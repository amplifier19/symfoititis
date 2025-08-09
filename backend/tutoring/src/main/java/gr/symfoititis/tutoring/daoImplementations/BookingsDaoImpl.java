package gr.symfoititis.tutoring.daoImplementations;

import gr.symfoititis.common.entities.ChatRoom;
import gr.symfoititis.tutoring.records.AvailabilitySlot;
import gr.symfoititis.tutoring.rowMappers.BookingsRowMapper;
import gr.symfoititis.tutoring.dao.BookingsDao;
import gr.symfoititis.common.entities.Booking;
import jakarta.validation.Valid;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Repository
public class BookingsDaoImpl implements BookingsDao {
    private final JdbcTemplate jdbcTemplate;

    public BookingsDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Booking> getBooking(Integer av_id) {
        String sql = """
            SELECT b.*, a.c_id, a.t_id, a.date, a.start_time
            FROM bookings b
            JOIN availability_slots a ON b.av_id = a.av_id
            WHERE b.av_id = ?
        """;
        return jdbcTemplate.query(sql, new BookingsRowMapper(), av_id).stream().findFirst();
    }

    @Override
    public List<Booking> getStudentBookings(String s_id) {
        String sql = """
        (SELECT b.*, a.c_id, a.t_id, a.date, a.start_time
        FROM bookings b
        JOIN availability_slots a ON b.av_id = a.av_id
        WHERE b.s_id = ?
        AND ((a.date = ? AND a.start_time >= ?) OR a.date > ?)
        GROUP BY b.b_id, a.date, a.start_time, a.c_id, a.t_id
        ORDER BY a.date, a.start_time
        LIMIT 50)
        
        UNION ALL
        
        (SELECT DISTINCT ON (b.room) b.*, a.c_id, a.t_id, a.date, a.start_time
        FROM bookings b
        JOIN availability_slots a ON b.av_id = a.av_id
        WHERE b.s_id = ?
        AND ((a.date = ? AND a.start_time < ?) OR a.date < ?)
        GROUP BY b.b_id, a.date, a.start_time, a.c_id, a.t_id
        ORDER BY b.room, a.date, a.start_time
        LIMIT 7)
        """;
        Date dateToday = Date.valueOf(LocalDate.now());
        Integer hour = LocalTime.now().minusHours(2).getHour();
        return jdbcTemplate.query(sql, new BookingsRowMapper(), s_id, dateToday, hour, dateToday, s_id, dateToday, hour, dateToday);
    }

    @Override
    public List<Booking> getTeacherBookings(String t_id) {
        String sql = """
            (SELECT b.*, a.c_id, a.t_id, a.date, a.start_time
            FROM bookings b
            JOIN availability_slots a ON b.av_id = a.av_id
            WHERE a.t_id = ?
            AND ((a.date = ? AND a.start_time >= ?) OR a.date > ?)
            GROUP BY b.b_id, a.date, a.start_time, a.c_id, a.t_id
            ORDER BY a.date, a.start_time
            LIMIT 50)
        
            UNION ALL
        
            (SELECT DISTINCT ON (b.room) b.*, a.c_id, a.t_id, a.date, a.start_time
            FROM bookings b
            JOIN availability_slots a ON b.av_id = a.av_id
            WHERE a.t_id = ?
            AND ((a.date = ? AND a.start_time < ?) OR a.date < ?)
            GROUP BY b.b_id, a.date, a.start_time, a.c_id, a.t_id
            ORDER BY b.room, a.date, a.start_time
            LIMIT 7)
        """;
        Date dateToday = Date.valueOf(LocalDate.now());
        Integer hour = LocalTime.now().minusHours(2).getHour();
        return jdbcTemplate.query(sql, new BookingsRowMapper(), t_id, dateToday, hour, dateToday, t_id, dateToday, hour, dateToday);
    }

    @Override
    public List<@Valid ChatRoom> addBookings(List<AvailabilitySlot> availabilitySlots, String s_id) throws BadSqlGrammarException {
        String sql = "INSERT INTO bookings(av_id, s_id, room, state) VALUES(?, ?, ?, 'ACTIVE')";
        List<ChatRoom> chatRooms = new ArrayList<>();
        Set<String> uniqueRoomIds = new HashSet<>();
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                AvailabilitySlot slot = availabilitySlots.get(i);
                String input = slot.dep_id() + slot.c_id() + slot.t_id() + s_id;
                UUID roomId = UUID.nameUUIDFromBytes(input.getBytes());
                String roomIdString = roomId.toString();
                ps.setInt(1, slot.av_id());
                ps.setString(2, s_id);
                ps.setString(3, roomIdString);
                if (uniqueRoomIds.add(roomIdString)) {
                    chatRooms.add(new ChatRoom(roomIdString, slot.dep_id(), slot.c_id(), slot.t_id(), s_id));
                }
            }

            @Override
            public int getBatchSize() {
                return availabilitySlots.size();
            }
        });
        return chatRooms;
    }

    @Override
    public int studentCancelBooking(Integer b_id, String s_id) {
        String sql = """
            UPDATE bookings
            SET state = 'CANCELED'
            WHERE b_id = ?
            AND s_id = ?
            AND EXISTS (
                SELECT 1 FROM availability_slots a
                WHERE a.av_id = bookings.av_id
                AND a.date > ?
                AND a.state = 'BOOKED'
            )
            AND state = 'ACTIVE'
        """;
        Date dateToday = Date.valueOf(LocalDate.now());
        return jdbcTemplate.update(sql, b_id, s_id, dateToday);
    }

    @Override
    public int teacherCancelBooking(Integer b_id, String t_id) {
        String sql = """
            UPDATE bookings
            SET state = 'CANCELED'
            WHERE b_id = ?
            AND EXISTS (
                SELECT 1 FROM availability_slots a
                WHERE a.av_id = bookings.av_id
                AND a.t_id = ?
                AND a.date > ?
                AND a.state = 'BOOKED'
            )
            AND state = 'ACTIVE'
        """;
        Date dateToday = Date.valueOf(LocalDate.now());
        return jdbcTemplate.update(sql, b_id, t_id, dateToday);
    }
    @Override
    public int adminCancelBooking(Integer b_id) {
        String sql = """
            UPDATE bookings
            SET state = 'CANCELED'
            WHERE b_id = ?
            AND EXISTS (
                SELECT 1 FROM availability_slots a
                WHERE a.av_id = booking.b_id
                AND a.date > ?
                AND a.state = 'BOOKED'
            )
            AND state = 'ACTIVE'
        """;
        Date dateToday = Date.valueOf(LocalDate.now());
        return jdbcTemplate.update(sql, b_id, dateToday);
    }
}
