package gr.symfoititis.common.entities;

import gr.symfoititis.common.enums.BookingState;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public class Booking {
    @Positive(message = "Booking id must be a positive value")
    private Integer b_id;
    @NotNull(message = "Availability slot id cannot be null")
    @Positive(message = "Availability slot id must be a positive value")
    private Integer av_id;
    @NotNull(message = "Student id cannot be null")
    @NotBlank(message = "Student id cannot be blank")
    private String s_id;
    @NotBlank(message = "Room cannot be blank")
    private String room;
    private BookingState state;
    @NotBlank(message = "Teacher id cannot be blank")
    private String t_id;
    private LocalDate date;
    @Positive(message = "Start time must be a positive value")
    private Integer start_time;
    @NotBlank(message = "Student name cannot be blank")
    private String student_name;
    @NotBlank(message = "Teacher name cannot be blank")
    private String teacher_firstname;
    @NotBlank(message = "Teacher name cannot be blank")
    private String teacher_lastname;

    public Booking () {}

    public Booking(Integer b_id, Integer av_id, String s_id, String room, BookingState state, String t_id, LocalDate date, Integer start_time) {
        this.b_id = b_id;
        this.av_id = av_id;
        this.s_id = s_id;
        this.room = room;
        this.state = state;
        this.t_id = t_id;
        this.date = date;
        this.start_time = start_time;
    }

    public Integer getB_id() {
        return b_id;
    }

    public void setB_id(Integer b_id) {
        this.b_id = b_id;
    }

    public Integer getAv_id() {
        return av_id;
    }

    public void setAv_id(Integer av_id) {
        this.av_id = av_id;
    }

    public String getS_id() {
        return s_id;
    }

    public void setS_id(String s_id) {
        this.s_id = s_id;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public BookingState getState() {
        return state;
    }

    public void setState(BookingState state) {
        this.state = state;
    }

    public String getT_id() {
        return t_id;
    }

    public void setT_id(String t_id) {
        this.t_id = t_id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getStart_time() {
        return start_time;
    }

    public void setStart_time(Integer start_time) {
        this.start_time = start_time;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getTeacher_firstname() {
        return teacher_firstname;
    }

    public void setTeacher_firstname(String teacher_firstname) {
        this.teacher_firstname = teacher_firstname;
    }

    public String getTeacher_lastname() {
        return teacher_lastname;
    }

    public void setTeacher_lastname(String teacher_lastname) {
        this.teacher_lastname = teacher_lastname;
    }
}
