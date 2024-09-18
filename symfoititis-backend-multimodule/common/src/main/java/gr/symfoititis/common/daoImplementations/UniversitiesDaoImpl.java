package gr.symfoititis.common.daoImplementations;

import gr.symfoititis.common.dao.UniversitiesDao;
import gr.symfoititis.common.records.University;
import gr.symfoititis.common.rowMappers.UniversitiesRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UniversitiesDaoImpl implements UniversitiesDao {
    protected final JdbcTemplate jdbcTemplate;

    public UniversitiesDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    /**
     *
     * Universities
     */
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

}
