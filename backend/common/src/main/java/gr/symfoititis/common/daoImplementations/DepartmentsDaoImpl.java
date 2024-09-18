package gr.symfoititis.common.repositories;

import gr.symfoititis.common.dao.DepartmentsDao;
import gr.symfoititis.common.records.Department;
import gr.symfoititis.common.rowMappers.DepartmentsRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DepartmentsDaoImpl implements DepartmentsDao {
    private final JdbcTemplate jdbcTemplate;

    public DepartmentsDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    /**
     *
     * Departments
     */
    @Override
    public List<Department> getDepartments() {
        String sql = "SELECT * FROM departments";
        return jdbcTemplate.query(sql, new DepartmentsRowMapper());
    }
    @Override
    public List<Department> getDepartments(Integer uni_id) {
        String sql = "SELECT * FROM departments d WHERE d.uni_id = ?";
        return jdbcTemplate.query(sql, new DepartmentsRowMapper (), uni_id);
    }
    @Override
    public Optional<Department> getDepartment(Integer dep_id) {
        String sql = "SELECT * FROM departments d WHERE d.dep_id = ?";
        return jdbcTemplate.query (sql, new DepartmentsRowMapper (), dep_id).stream().findFirst();
    }
}
