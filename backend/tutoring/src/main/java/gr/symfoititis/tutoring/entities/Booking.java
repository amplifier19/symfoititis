package gr.symfoititis.tutoring.entities;

import gr.symfoititis.tutoring.enums.BookingState;

import java.time.LocalDate;

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

    public String getTeacher_first_name() {
        return teacher_first_name;
    }

    public void setTeacher_first_name(String teacher_first_name) {
        this.teacher_first_name = teacher_first_name;
    }

    public String getTeacher_last_name() {
        return teacher_last_name;
    }

    public void setTeacher_last_name(String teacher_last_name) {
        this.teacher_last_name = teacher_last_name;
    }
}
