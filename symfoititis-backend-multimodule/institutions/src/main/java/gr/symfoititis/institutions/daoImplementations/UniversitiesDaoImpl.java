package gr.symfoititis.institutions.daoImplementations;

import gr.symfoititis.institutions.dao.UniversitiesDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import gr.symfoititis.institutions.records.University;
import gr.symfoititis.institutions.rowMappers.UniversitiesRowMapper;

import java.util.List;
import java.util.Optional;

@Repository
public class UniversitiesDaoImpl implements UniversitiesDao {
    protected final JdbcTemplate jdbcTemplate;

    public UniversitiesDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<University> getUniversities() {
        String sql = "SELECT * FROM universities";
        return jdbcTemplate.query(sql, new UniversitiesRowMapper());
    }
    @Override
    public Optional<University> getUniversity(Integer uni_id) {
        String sql = "SELECT * FROM universities u WHERE u.uni_id = ?";
        return jdbcTemplate.query (sql, new UniversitiesRowMapper (), uni_id).stream().findFirst();
    }

    @Override
    public int addUniversity(University university) {
        String sql = "INSERT INTO universities(uni_display_name, uni_alt_name) VALUES(?, ?)";
        return jdbcTemplate.update(sql, university.uni_display_name(), university.uni_alt_name());
    }

    @Override
    public int updateUniversity(University university) {
        String sql = "UPDATE universities SET uni_display_name = ?, uni_alt_name = ? WHERE uni_id = ?";
        return jdbcTemplate.update(sql, university.uni_display_name(), university.uni_alt_name(), university.uni_id());
    }
    @Override
    public int deleteUniversity(Integer uni_id) {
        String sql = "DELETE FROM universities WHERE uni_id = ?";
        return jdbcTemplate.update(sql, uni_id);
    }
}
