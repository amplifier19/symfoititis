package gr.symfoititis.tutoring.dao;

import gr.symfoititis.common.entities.Booking;

import java.util.List;

public interface BookingsDao {
    List<Booking> getStudentBookings (String s_id);
    List<Booking> getTeacherBookings (String t_id);
    void addBooking (Booking booking);
    int studentCancelBooking (Integer b_id, String s_id);
    int teacherCancelBooking (Integer b_id, String t_id);
    int adminCancelBooking (Integer b_id);
}
