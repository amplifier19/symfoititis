package gr.symfoititis.admin.daoImplementations;

import gr.symfoititis.admin.dao.UniversitiesDao;
import gr.symfoititis.common.records.University;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AdminUniversitiesDaoImpl implements UniversitiesDao {
    private final JdbcTemplate jdbcTemplate;
    public AdminUniversitiesDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     *
     * Universities
     */
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
