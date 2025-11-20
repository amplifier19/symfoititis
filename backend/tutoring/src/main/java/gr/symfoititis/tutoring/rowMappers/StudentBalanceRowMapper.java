package gr.symfoititis.tutoring.rowMappers;

import gr.symfoititis.tutoring.entities.StudentBalance;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentBalanceRowMapper implements RowMapper<StudentBalance> {
    @Override
    public StudentBalance mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new StudentBalance(
                rs.getInt("id"),
                rs.getString("student_id"),
                rs.getInt("hours"),
                rs.getInt("weight")
        );
    }
}
