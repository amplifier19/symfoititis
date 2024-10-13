package gr.symfoititis.education.rowMappers;

import gr.symfoititis.education.records.Course;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CoursesRowMapper implements RowMapper<Course> {
    @Override
    public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Course(
                rs.getInt("c_id"),
                rs.getInt("dep_id"),
                rs.getInt ("semester"),
                rs.getString ("c_display_name"),
                rs.getString ("description")
        );
    }
}
