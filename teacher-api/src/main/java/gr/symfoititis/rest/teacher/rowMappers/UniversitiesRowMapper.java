package gr.symfoititis.rest.teacher.rowMappers;

import gr.symfoititis.rest.teacher.records.University;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UniversitiesRowMapper implements RowMapper<University> {

    @Override
    public University mapRow (ResultSet rs, int rowNum) throws SQLException {
        return new University (
                rs.getInt("uni_id"),
                rs.getString ("uni_display_name"),
                rs.getString ("uni_alt_name")
        );
    }
}
