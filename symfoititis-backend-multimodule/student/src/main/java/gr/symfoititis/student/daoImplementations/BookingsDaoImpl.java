package gr.symfoititis.student.daoImplementations;

import gr.symfoititis.common.rowMappers.BookingsRowMapper;
import gr.symfoititis.student.dao.BookingsDao;
import gr.symfoititis.common.records.Booking;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class BookingsDaoImpl implements BookingsDao {
    private final JdbcTemplate jdbcTemplate;

    public BookingsDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<Booking> getBookings(String s_id) {
        String sql = "SELECT * FROM bookings WHERE s_id = ? AND state = 'ACTIVE'";
        return jdbcTemplate.query(sql, new BookingsRowMapper(), s_id);
    }

    @Override
    public int addBooking(Booking booking) throws BadSqlGrammarException {
        String sql = "INSERT INTO bookings(av_id, s_id, room, state) VALUES(?, ?, ?, 'ACTIVE')";
        UUID room = UUID.randomUUID();
        return jdbcTemplate.update(sql, booking.av_id(), booking.s_id(), room.toString());
    }

    @Override
    public int cancelBooking(Integer b_id) {
        String sql = "UPDATE bookings SET state = 'CANCELED' WHERE b_id = ? AND state = 'ACTIVE'";
        return jdbcTemplate.update(sql, b_id);
    }
}
