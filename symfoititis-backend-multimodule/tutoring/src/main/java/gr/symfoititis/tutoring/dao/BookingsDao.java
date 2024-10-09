package gr.symfoititis.student.dao;

import org.springframework.jdbc.BadSqlGrammarException;

import java.util.List;

public interface BookingsDao {
    List<Booking> getBookings (String s_id);
    int addBooking (Booking booking) throws BadSqlGrammarException;
    int cancelBooking (Integer b_id);
}
