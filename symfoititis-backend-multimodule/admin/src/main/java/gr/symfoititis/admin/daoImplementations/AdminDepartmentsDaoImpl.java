package gr.symfoititis.admin.daoImplementations;

import gr.symfoititis.admin.dao.DepartmentsDao;
import gr.symfoititis.common.records.Department;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AdminDepartmentsDaoImpl implements DepartmentsDao {
    private final JdbcTemplate jdbcTemplate;
    public AdminDepartmentsDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    /**
     *
     * Departments
     */
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
