package gr.symfoititis.tutoring.rowMappers;

import gr.symfoititis.tutoring.enums.SlotState;
import gr.symfoititis.tutoring.records.AvailabilitySlot;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class AvailabilitySlotRowMapper implements RowMapper<AvailabilitySlot> {
    @Override
    public AvailabilitySlot mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new AvailabilitySlot(
                rs.getInt("av_id"),
                rs.getInt("dep_id"),
                rs.getInt("c_id"),
                rs.getString("t_id"),
                LocalDate.parse(rs.getString("date")),
                rs.getInt("week_day"),
                rs.getInt("start_time"),
                SlotState.valueOf(rs.getString("state"))
        );
    }
}
