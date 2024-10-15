package gr.symfoititis.tutoring.daoImplementations;

import gr.symfoititis.tutoring.dao.AvailabilityDao;
import gr.symfoititis.tutoring.entities.AvailabilitySlot;
import gr.symfoititis.tutoring.rowMappers.AvailabilitySlotRowMapper;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class AvailabilityDaoImpl implements AvailabilityDao {
    private final JdbcTemplate jdbcTemplate;
    public AvailabilityDaoImpl (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<AvailabilitySlot> getAvailabilitySlots(Integer c_id, String t_id) {
        String sql = "SELECT * FROM availability_slots WHERE c_id = ? AND t_id = ? AND state = 'AVAILABLE'";
        return jdbcTemplate.query(sql, new AvailabilitySlotRowMapper(), c_id, t_id);
    }

    @Override
    public List<String> getAvailableTeacherIds(Integer c_id, Integer dep_id) {
        String sql = "SELECT DISTINCT t_id FROM availability_slots WHERE c_id = ? AND dep_id = ?";
        return jdbcTemplate.queryForList(sql, String.class, c_id, dep_id);
    }

    @Override
    public void addAvailabilitySlots(List<AvailabilitySlot> availabilitySlots) {
        String sql = """
                INSERT INTO
                availability_slots (dep_id, c_id, t_id, date, week_day, start_time)
                VALUES (?, ?, ?, ?, ?, ?)
        """;
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                AvailabilitySlot slot = availabilitySlots.get(i);
                ps.setInt(1, slot.dep_id());
                ps.setInt(2, slot.c_id());
                ps.setString(3, slot.t_id());
                ps.setString(4, slot.date().toString());
                ps.setInt(5, slot.week_day());
                ps.setInt(6, slot.start_time());
            }
            @Override
            public int getBatchSize() {
                return availabilitySlots.size();
            }
        });
    }

    @Override
    public int[] updateAvailabilitySlots(List<AvailabilitySlot> availabilitySlots) {
        String sql = """
            UPDATE availability_slots SET
                date = ?,
                week_day = ?,
                start_time = ?
            WHERE av_id = ?
            AND dep_id = ?
            AND c_id = ?
            AND t_id = ?
            AND state = 'AVAILABLE'
        """;
        return jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        AvailabilitySlot slot = availabilitySlots.get(i);
                        ps.setString(1, slot.date().toString());
                        ps.setInt(2, slot.week_day());
                        ps.setInt(3, slot.start_time());
                        ps.setInt(4, slot.av_id());
                        ps.setInt(5, slot.dep_id());
                        ps.setInt(6, slot.c_id());
                        ps.setString(7, slot.t_id());
                    }
                    @Override
                    public int getBatchSize() {
                        return availabilitySlots.size();
                    }
                }
        );
    }

    @Override
    public int[] deleteAvailabilitySlots(List<Integer> availabilitySlotIds, String t_id) {
        String sql = "UPDATE availability_slots SET state = 'UNAVAILABLE' WHERE av_id = ? AND t_id = ?";
        return jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Integer id = availabilitySlotIds.get(i);
                ps.setInt(1, id);
                ps.setString(2, t_id);
            }
            @Override
            public int getBatchSize() {
                return availabilitySlotIds.size();
            }
        });
    }
}
