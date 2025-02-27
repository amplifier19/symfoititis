package gr.symfoititis.common.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import gr.symfoititis.common.enums.BookingState;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Booking {
    @Positive
    private Integer b_id;
    @NotNull
    @Positive
    private Integer av_id;
    @Positive
    private Integer c_id;
    @NotNull
    @NotBlank
    private String s_id;
    private String room;
    private BookingState state;
    private String t_id;
    private LocalDate date;
    @PositiveOrZero
    private Integer start_time;
    private String student_name;
    private String student_email;
    private String teacher_firstname;
    private String teacher_lastname;
    private String teacher_email;

    public Booking () {}

    public Booking(
            Integer b_id,
            Integer av_id,
            Integer c_id,
            @NotNull
            @NotBlank
            String s_id,
            @NotNull
            @NotBlank
            String room,
            @NotNull
            BookingState state,
            @NotNull
            @NotBlank
            String t_id,
            @NotNull
            LocalDate date,
            Integer start_time
    ) {
        this.b_id = b_id;
        this.av_id = av_id;
        this.c_id = c_id;
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

    public Integer getC_id() {
        return c_id;
    }

    public void setC_id(Integer c_id) {
        this.c_id = c_id;
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

    public String getStudent_email() {
        return student_email;
    }

    public void setStudent_email(String student_email) {
        this.student_email = student_email;
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

    public String getTeacher_email() {
        return teacher_email;
    }

    public void setTeacher_email(String teacher_email) {
        this.teacher_email = teacher_email;
    }
}
