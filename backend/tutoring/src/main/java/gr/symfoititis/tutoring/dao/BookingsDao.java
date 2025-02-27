package gr.symfoititis.tutoring.dao;

import gr.symfoititis.common.entities.Booking;
import gr.symfoititis.common.entities.ChatRoom;
import gr.symfoititis.tutoring.records.AvailabilitySlot;

import java.util.List;
import java.util.Optional;

public interface BookingsDao {
    Optional<Booking> getBooking(Integer av_id);
    List<Booking> getStudentBookings (String s_id);
    List<Booking> getTeacherBookings (String t_id);
    List<ChatRoom> addBookings (List<AvailabilitySlot> availabilitySlots, String s_id);
    int studentCancelBooking (Integer b_id, String s_id);
    int teacherCancelBooking (Integer b_id, String t_id);
    int adminCancelBooking (Integer b_id);
}
