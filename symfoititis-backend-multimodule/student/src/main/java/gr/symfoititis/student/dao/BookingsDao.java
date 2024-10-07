package gr.symfoititis.student.dao;

import gr.symfoititis.common.records.Booking;
import org.springframework.jdbc.BadSqlGrammarException;

import java.sql.SQLException;
import java.util.List;

public interface BookingsDao {
    List<Booking> getBookings (String s_id);
    int addBooking (Booking booking) throws BadSqlGrammarException;
    int cancelBooking (Integer b_id);
}
