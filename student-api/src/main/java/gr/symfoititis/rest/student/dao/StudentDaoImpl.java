package gr.symfoititis.rest.student.dao;

import gr.symfoititis.rest.student.records.Course;
import gr.symfoititis.rest.student.records.Note;
import gr.symfoititis.rest.student.records.University;
import gr.symfoititis.rest.student.records.Department;
import gr.symfoititis.rest.student.rowMappers.CoursesRowMapper;
import gr.symfoititis.rest.student.rowMappers.DepartmentsRowMapper;
import gr.symfoititis.rest.student.rowMappers.NotesRowMapper;
import gr.symfoititis.rest.student.rowMappers.UniversitiesRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class StudentDaoImpl implements StudentDao {
    private final JdbcTemplate jdbcTemplate;

    public StudentDaoImpl(JdbcTemplate jdbcTemplate) {
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


    /**
     *
     * Courses
     */
    @Override
    public List<Course> getCourses(Integer dep_id) {
        String sql = "SELECT * FROM courses c WHERE c.dep_id = ? ORDER BY c.semester";
        return jdbcTemplate.query(sql, new CoursesRowMapper(), dep_id);
    }
    @Override
    public Optional<Course> getCourse(Integer c_id) {
        String sql = "SELECT * FROM courses c WHERE c.c_id = ?";
        return jdbcTemplate.query (sql, new CoursesRowMapper(), c_id).stream().findFirst();
    }


    /**
     *
     * Notes
     */
    @Override
    public List<Note> getNotes(Integer c_id) {
        String sql = "SELECT * FROM notes n WHERE n.c_id = ?";
        return jdbcTemplate.query(sql, new NotesRowMapper(), c_id);
    }
}
