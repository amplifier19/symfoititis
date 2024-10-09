package gr.symfoititis.institutions.daoImplementations;

import gr.symfoititis.institutions.dao.DepartmentsDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import gr.symfoititis.institutions.records.Department;
import gr.symfoititis.institutions.rowMappers.DepartmentsRowMapper;

import java.util.List;
import java.util.Optional;

@Repository
public class DepartmentsDaoImpl implements DepartmentsDao {
    protected final JdbcTemplate jdbcTemplate;

    public DepartmentsDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

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
    @Override
    public int addDepartment(Department department) {
        String sql = "INSERT INTO departments(uni_id, dep_display_name, dep_alt_name) VALUES(?, ?, ?)";
        return jdbcTemplate.update(sql, department.uni_id(), department.dep_display_name(), department.dep_alt_name());
    }
    @Override
    public int updateDepartment(Department department) {
        String sql = "UPDATE departments SET uni_id = ?, dep_display_name = ?, dep_alt_name = ? WHERE dep_id = ?";
        return jdbcTemplate.update(sql, department.uni_id(), department.dep_display_name(), department.dep_alt_name(), department.dep_id());
    }
    @Override
    public int deleteDepartment(Integer dep_id) {
        String sql = "DELETE FROM departments WHERE dep_id = ?";
        return jdbcTemplate.update(sql, dep_id);
    }
}
