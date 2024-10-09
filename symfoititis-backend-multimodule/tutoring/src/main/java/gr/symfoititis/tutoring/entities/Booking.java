package gr.symfoititis.tutoring.records;

import gr.symfoititis.common.exceptions.BadRequestException;
import gr.symfoititis.tutoring.enums.BookingState;

import java.time.LocalDate;
import java.util.Objects;


public class Booking {
    private Integer b_id;
    private Integer av_id;
    private String s_id;
    private String room;
    private BookingState state;
    private String t_id;
    private LocalDate date;
    private Integer start_time;
    private String student_name;
    private String teacher_first_name;
    private String teacher_last_name;

    public Booking () {}


    public Booking (Integer b_id, Integer av_id, String s_id, String room, BookingState state, String t_id, LocalDate date, Integer start_time) {
        this.b_id = b_id;
        this.av_id = av_id;
        this.s_id = s_id;
        this.room = room;
        this.state = state;
        this.t_id = t_id;
        this.date = date;
        this.start_time = start_time;
    }

   /* public Booking (Integer av_id, String s_id) {
        this.av_id = av_id;
        this.s_id = s_id;
    }

    // Constructor for teacher bookings
    public Booking (Integer b_id, Integer av_id, String s_id, String room, BookingState state, LocalDate date, Integer start_time, String student_name) {
        this.b_id = b_id;
        this.av_id = av_id;
        this.s_id = s_id;
        this.room = room;
        this.state = state;
        this.date = date;
        this.start_time = start_time;
        this.student_name = student_name;
    }

    // Constructor for student bookings
    public Booking (Integer b_id, Integer av_id, String s_id, String room, BookingState state, LocalDate date, Integer start_time, String teacher_first_name, String teacher_last_name) {
        this.b_id = b_id;
        this.av_id = av_id;
        this.s_id = s_id;
        this.room = room;
        this.state = state;
        this.date = date;
        this.start_time = start_time;
        this.teacher_first_name = teacher_first_name;
        this.teacher_last_name = teacher_last_name;
    } */

    public Integer getAvailabilityId () {
       return av_id;
    }

    public String getStudentId() {
        return s_id;
    }

    public String getTeacherId() {
        return t_id;
    }
    public void setBookingId (Integer b_id) {
        this.b_id = b_id;
    }

    public void setAvailabilityId (Integer av_id) {
        this.av_id = av_id;
    }

    public void setStudentId (String s_id) {
        this.s_id = s_id;
    }

    public void setRoom (String room) {
        this.room = room;
    }

    public void setState (BookingState state) {
        this.state = state;
    }

    public void setTeacherId (String teacherId) {
       this.t_id = t_id;
    }
    public void setDate (LocalDate date) {
        this.date = date;
    }

    public void setStartTime (Integer start_time) {
        this.start_time = start_time;
    }

    public void setStudentName(String student_name) {
        this.student_name = student_name;
    }

    public void setTeacherFirstName(String teacher_first_name) {
        this.teacher_first_name = teacher_first_name;
    }

    public void setTeacherLastName(String teacher_last_name) {
        this.teacher_last_name = teacher_last_name;
    }

    public void validateAvailabilityId () {
        if (Objects.isNull(av_id) || av_id.compareTo(0) <= 0)
            throw new BadRequestException("Invalid availability slot id");
    }

    public void validateStudentId () {
        if (Objects.isNull(s_id) || s_id.isBlank())
            throw new BadRequestException("Invalid student id");
    }
}
