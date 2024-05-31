package gr.symfoititis.rest.admin.rowMappers;

import gr.symfoititis.rest.admin.records.Course;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CoursesRowMapper implements org.springframework.jdbc.core.RowMapper<Course> {
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