package gr.symfoititis.institutions.rowMappers;

import org.springframework.jdbc.core.RowMapper;
import gr.symfoititis.institutions.records.Department;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DepartmentsRowMapper implements RowMapper<Department> {
    @Override
    public Department mapRow (ResultSet rs, int rowNum) throws SQLException {
        return new Department (
                rs.getInt ("dep_id"),
                rs.getInt ("uni_id"),
                rs.getString ("dep_display_name"),
                rs.getString ("dep_alt_name")
        );
    }
}
