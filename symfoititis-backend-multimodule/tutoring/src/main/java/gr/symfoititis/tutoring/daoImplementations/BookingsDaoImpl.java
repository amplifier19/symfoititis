package gr.symfoititis.tutoring.daoImplementations;

import gr.symfoititis.tutoring.rowMappers.BookingsRowMapper;
import gr.symfoititis.tutoring.dao.BookingsDao;
import gr.symfoititis.common.entities.Booking;
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
    public List<Booking> getStudentBookings(String s_id) {
        String sql = """
            SELECT b.*, a.t_id, a.date, a.start_time
            FROM bookings b
            JOIN availability_slots a ON b.av_id = a.av_id
            WHERE b.s_id = ?
            AND b.state = 'ACTIVE'
        """;
        return jdbcTemplate.query(sql, new BookingsRowMapper(), s_id);
    }

    @Override
    public List<Booking> getTeacherBookings(String t_id) {
        String sql = """
            SELECT b.*, a.t_id, a.date, a.start_time
            FROM bookings b
            JOIN availability_slots a ON b.av_id = a.av_id
            WHERE a.t_id = ?
            AND b.state = 'ACTIVE'
        """;
        return jdbcTemplate.query(sql, new BookingsRowMapper(), t_id);
    }

    @Override
    public void addBooking(Booking booking) throws BadSqlGrammarException {
        String sql = "INSERT INTO bookings(av_id, s_id, room, state) VALUES(?, ?, ?, 'ACTIVE')";
        UUID room = UUID.randomUUID();
        jdbcTemplate.update(sql, booking.getAvailabilityId(), booking.getStudentId(), room.toString());
    }

    @Override
    public int studentCancelBooking(Integer b_id, String s_id) {
        String sql = "UPDATE bookings SET state = 'CANCELED' WHERE b_id = ? AND s_id = ? AND state = 'ACTIVE'";
        return jdbcTemplate.update(sql, b_id, s_id);
    }

    @Override
    public int teacherCancelBooking(Integer b_id, String t_id) {
        String sql = """
            UPDATE bookings b
            SET b.state = 'CANCELED'
            WHERE b.b_id = ?
            AND EXISTS (
                SELECT 1 FROM availability_slots a
                WHERE a.av_id = b.av_id
                AND a.t_id = ?
            )
            AND b.state = 'ACTIVE'
        """;
        return jdbcTemplate.update(sql, b_id, t_id);
    }
    @Override
    public int adminCancelBooking(Integer b_id) {
        String sql = "UPDATE bookings SET state = 'CANCELED' WHERE b_id = ? AND state = 'ACTIVE'";
        return jdbcTemplate.update(sql, b_id);
    }
}
