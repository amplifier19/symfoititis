package gr.symfoititis.tutoring.services;

import gr.symfoititis.common.entities.ChatRoom;
import gr.symfoititis.common.exceptions.*;
import gr.symfoititis.email.services.EmailService;
import gr.symfoititis.student.services.StudentService;
import gr.symfoititis.tutoring.config.RabbitmqConfig;
import gr.symfoititis.tutoring.dao.BookingsDao;
import gr.symfoititis.common.entities.Booking;
import gr.symfoititis.common.entities.Teacher;
import gr.symfoititis.common.entities.Student;
import gr.symfoititis.teacher.services.TeacherService;
import gr.symfoititis.tutoring.records.AvailabilitySlot;
import jakarta.validation.Valid;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class BookingsService {
    private final BookingsDao bookingsDao;
    private final TeacherService teacherService;
    private final StudentService studentService;
    private final AvailabilityService availabilityService;
    private final EmailService emailService;
    private final RabbitTemplate rabbitTemplate;
    private final RabbitmqConfig rabbitmqConfig;
    public BookingsService (
            BookingsDao bookingsDao,
            TeacherService teacherService,
            StudentService studentService,
            AvailabilityService availabilityService,
            EmailService emailService,
            RabbitTemplate rabbitTemplate,
            RabbitmqConfig rabbitmqConfig
    ) {
        this.bookingsDao = bookingsDao;
        this.teacherService = teacherService;
        this.studentService = studentService;
        this.availabilityService = availabilityService;
        this.emailService = emailService;
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitmqConfig = rabbitmqConfig;
    }
    private Booking getBooking(Integer av_id) {
        Booking booking = bookingsDao.getBooking(av_id).orElseThrow(() -> new NotFoundException("Booking not found"));
        Teacher teacher = teacherService.getTeacher(booking.getT_id());
        Student student = studentService.getStudent(booking.getS_id());
        attachStudentInfo(booking, student);
        attachTeacherInfo(booking, teacher);
        return booking;
    }
    public List<Booking> getBookings (String id, String role) {
        return switch (role) {
            case "student" -> {
                List<Booking> bookings = bookingsDao.getStudentBookings(id);
                Set<String> teacherIds = teacherService.getUniqueTeacherIds(bookings);
                for (String t_id : teacherIds) {
                    Teacher teacher = teacherService.getTeacher(t_id);
                    for (Booking booking : bookings) {
                        attachTeacherInfo(booking, teacher);
                    }
                }
                yield bookings;
            }
            case "teacher" -> {
                List<Booking> bookings = bookingsDao.getTeacherBookings(id);
                Set<String> studentIds = studentService.getUniqueStudentIds(bookings);
                for (String s_id : studentIds) {
                    Student student = studentService.getStudent(s_id);
                    for (Booking booking : bookings) {
                        attachStudentInfo(booking, student);
                    }
                }
                yield bookings;
            }
            default -> throw new ForbiddenException("Invalid role");
        };
    }

    public void addBookings (List<Integer> availabilityIds, Integer dep_id, String s_id) {
        List<AvailabilitySlot> slots = availabilityService.getAvailabilitySlotsByIds(availabilityIds, dep_id);
        List<@Valid ChatRoom> chatRooms = bookingsDao.addBookings(slots, s_id);
        rabbitTemplate.convertAndSend(rabbitmqConfig.getDirectExchangeName(), rabbitmqConfig.getRoutingKey(), chatRooms);
        Booking booking = getBooking(slots.get(0).av_id());
        emailService.sendBookingEmailNotificationToStudent(booking);
//        emailService.sendBookingEmailNotificationToTeacher();
    }

    public void cancelBooking (Integer b_id, String id, String role) {
        int ret = switch (role) {
            case "student" -> bookingsDao.studentCancelBooking(b_id, id);
            case "teacher" -> bookingsDao.teacherCancelBooking(b_id, id);
            case "admin" -> bookingsDao.adminCancelBooking(b_id);
            default -> throw new ForbiddenException("Invalid role");
        };
        if (ret == 0) {
            throw new NotFoundException(String.format("Booking %d not found", b_id));
        }
    }

    private void attachStudentInfo (Booking booking, Student student) {
        booking.setStudent_name(student.getUsername());
        booking.setStudent_email(student.getEmail());
    }

    private void attachTeacherInfo(Booking booking, Teacher teacher) {
        booking.setTeacher_firstname(teacher.getFirstname());
        booking.setTeacher_lastname(teacher.getLastname());
        booking.setTeacher_email(teacher.getEmail());
    }
}
