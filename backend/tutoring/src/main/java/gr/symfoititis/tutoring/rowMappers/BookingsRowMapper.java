package gr.symfoititis.tutoring.rowMappers;

import gr.symfoititis.common.enums.BookingState;
import gr.symfoititis.common.entities.Booking;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class BookingsRowMapper implements RowMapper<Booking> {
    @Override
    public Booking mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Booking(
               rs.getInt("b_id"),
               rs.getInt("av_id"),
               rs.getInt("c_id"),
               rs.getString("s_id"),
               rs.getString("room"),
               BookingState.valueOf(rs.getString("state")),
               rs.getString("t_id"),
               LocalDate.parse(rs.getString("date")),
               rs.getInt("start_time")
        );
    }
}
