package gr.symfoititis.tutoring.services;

import gr.symfoititis.common.exceptions.*;
import gr.symfoititis.student.services.StudentService;
import gr.symfoititis.tutoring.dao.BookingsDao;
import gr.symfoititis.common.entities.Booking;
import gr.symfoititis.common.entities.Teacher;
import gr.symfoititis.common.entities.Student;
import gr.symfoititis.teacher.services.TeacherService;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class BookingsService {
    private final BookingsDao bookingsDao;
    private final TeacherService teacherService;
    private final StudentService studentService;
    public BookingsService (BookingsDao bookingsDao, TeacherService teacherService, StudentService studentService) {
        this.bookingsDao = bookingsDao;
        this.teacherService = teacherService;
        this.studentService = studentService;
    }

    public List<Booking> getBookings (String id, String role) {
        return switch (role) {
            case "student" -> {
                List<Booking> bookings = bookingsDao.getStudentBookings(id);
                Set<String> teacherIds = teacherService.getUniqueTeacherIds(bookings);
                for (String t_id : teacherIds) {
                    Teacher teacher = teacherService.getTeacher(t_id);
                    for (Booking booking : bookings) {
                        if (booking.getT_id().equals(t_id)) {
                            booking.setTeacher_firstname(teacher.getFirstname());
                            booking.setTeacher_lastname(teacher.getLastname());
                        }
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
                        if (booking.getS_id().equals(student.getS_id())) {
                            booking.setStudent_name(student.getUsername());
                        }
                    }
                }
                yield bookings;
            }
            default -> throw new ForbiddenException("Invalid role");
        };
    }

    public void addBooking (Booking booking) {
        try {
            bookingsDao.addBooking(booking);
        } catch (BadSqlGrammarException e) {
            if ("65001".equals(e.getSQLException().getSQLState())) {
                throw new NotFoundException("This slot is not available for booking");
            }
            throw new InternalServerErrorException("Sql grammar error");
        }
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

    public Teacher retrieveTeacher (String t_id) {
        return teacherService.getTeacher(t_id);
    }
}
