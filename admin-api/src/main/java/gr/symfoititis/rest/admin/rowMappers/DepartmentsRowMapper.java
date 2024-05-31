package gr.symfoititis.rest.admin.rowMappers;

import gr.symfoititis.rest.admin.records.Department;
import org.springframework.jdbc.core.RowMapper;

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
