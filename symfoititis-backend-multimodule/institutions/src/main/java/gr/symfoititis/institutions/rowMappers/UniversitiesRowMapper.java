package gr.symfoititis.institutions.rowMappers;

import org.springframework.jdbc.core.RowMapper;
import gr.symfoititis.institutions.records.University;

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
