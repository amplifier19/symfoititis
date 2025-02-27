package gr.symfoititis.tutoring.daoImplementations;

import gr.symfoititis.tutoring.dao.AvailabilityDao;
import gr.symfoititis.tutoring.records.AvailabilitySlot;
import gr.symfoititis.tutoring.rowMappers.AvailabilitySlotRowMapper;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class AvailabilityDaoImpl implements AvailabilityDao {
    private final JdbcTemplate jdbcTemplate;
    public AvailabilityDaoImpl (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<AvailabilitySlot> getAvailabilitySlots(Integer dep_id, Integer c_id, String t_id) {
        String sql = """
            SELECT * FROM availability_slots
            WHERE dep_id = ?
            AND c_id = ?
            AND t_id = ?
            AND date >= ?
            AND ( state = 'AVAILABLE' OR state = 'BOOKED' )
            GROUP BY av_id, date
            ORDER BY start_time
        """;
        return jdbcTemplate.query(sql, new AvailabilitySlotRowMapper(), dep_id, c_id, t_id, Date.valueOf(LocalDate.now().minusDays(15)));
    }

    @Override
    public List<AvailabilitySlot> getTeacherAvailabilitySlots(Integer dep_id, Integer c_id, String t_id) {
        String sql = """
            SELECT * FROM availability_slots
            WHERE dep_id = ?
            AND c_id = ?
            AND t_id = ?
            AND date > ?
            AND state = 'AVAILABLE'
            GROUP BY av_id, date
            ORDER BY start_time
        """;
        return jdbcTemplate.query(sql, new AvailabilitySlotRowMapper(), dep_id, c_id, t_id, Date.valueOf(LocalDate.now()));
    }

    public List<AvailabilitySlot> getAvailabilitySlotsByIds(List<Integer> availabilityIds, Integer dep_id) {
        String sql = "SELECT * FROM availability_slots WHERE av_id IN (" +
                availabilityIds.stream().map(id -> "?").collect(Collectors.joining(", ")) +
                ") AND dep_id = ? AND state = 'AVAILABLE'";
        Object[] params = Stream.concat(availabilityIds.stream(), Stream.of(dep_id)).toArray();
        return jdbcTemplate.query(sql, new AvailabilitySlotRowMapper(), params);
    }

    @Override
    public List<String> getAvailableTeacherIds(Integer dep_id, Integer c_id) {
        String sql = "SELECT DISTINCT t_id FROM availability_slots WHERE dep_id = ? AND c_id = ?";
        return jdbcTemplate.queryForList(sql, String.class, dep_id, c_id);
    }

    @Override
    public List<Integer> getAvailableTutoringCourseIds(Integer dep_id) {
        String sql = "SELECT DISTINCT c_id FROM availability_slots WHERE dep_id = ? AND state = 'AVAILABLE'";
        return jdbcTemplate.queryForList(sql, Integer.class, dep_id);
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
                ps.setDate(4, Date.valueOf(slot.date()));
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
            UPDATE availability_slots
            SET start_time = ?
            WHERE av_id = ?
            AND dep_id = ?
            AND c_id = ?
            AND t_id = ?
            AND date = ?
            AND week_day = ?
        """;
        return jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        AvailabilitySlot slot = availabilitySlots.get(i);
                        ps.setInt(1, slot.start_time());
                        ps.setInt(2, slot.av_id());
                        ps.setInt(3, slot.dep_id());
                        ps.setInt(4, slot.c_id());
                        ps.setString(5, slot.t_id());
                        ps.setDate(6, Date.valueOf(slot.date()));
                        ps.setInt(7, slot.week_day());
                    }
                    @Override
                    public int getBatchSize() {
                        return availabilitySlots.size();
                    }
                }
        );
    }

    @Override
    public int[] deleteAvailabilitySlots(List<Integer> availabilitySlotIds, Integer dep_id, String t_id) {
        String sql = "UPDATE availability_slots SET state = 'UNAVAILABLE' WHERE av_id = ? AND dep_id = ? AND t_id = ?";
        return jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Integer id = availabilitySlotIds.get(i);
                ps.setInt(1, id);
                ps.setInt(2, dep_id);
                ps.setString(3, t_id);
            }
            @Override
            public int getBatchSize() {
                return availabilitySlotIds.size();
            }
        });
    }
}
