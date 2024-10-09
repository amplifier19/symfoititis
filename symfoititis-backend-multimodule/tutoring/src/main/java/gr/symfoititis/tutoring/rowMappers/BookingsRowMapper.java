package gr.symfoititis.student.rowMappers;

import gr.symfoititis.student.enums.BookingState;
import gr.symfoititis.student.records.Booking;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookingsRowMapper implements RowMapper<Booking> {
    @Override
    public Booking mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Booking(
               rs.getInt("b_id"),
               rs.getInt("av_id"),
               rs.getString("s_id"),
               rs.getString("room"),
               BookingState.valueOf(rs.getString("state"))
        );
    }
}
