package gr.symfoititis.rest.admin.dao;

import gr.symfoititis.rest.admin.records.Course;
import gr.symfoititis.rest.admin.records.Department;
import gr.symfoititis.rest.admin.records.Note;
import gr.symfoititis.rest.admin.records.University;
import gr.symfoititis.rest.admin.rowMappers.CoursesRowMapper;
import gr.symfoititis.rest.admin.rowMappers.DepartmentsRowMapper;
import gr.symfoititis.rest.admin.rowMappers.NotesRowMapper;
import gr.symfoititis.rest.admin.rowMappers.UniversitiesRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AdminDaoImpl implements AdminDao {
    private final JdbcTemplate jdbcTemplate;

    public AdminDaoImpl(JdbcTemplate jdbcTemplate) {
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
    @Override
    public int addUniversity(University university) {
        String sql = "INSERT INTO universities(uni_display_name, uni_alt_name) VALUES(?, ?)";
        return jdbcTemplate.update(sql, university.uni_display_name(), university.uni_alt_name());
    }
    @Override
    public int deleteUniversity(Integer uni_id) {
        String sql = "DELETE FROM universities WHERE uni_id = ?";
        return jdbcTemplate.update(sql, uni_id);
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
        return jdbcTemplate.query(sql, new DepartmentsRowMapper(), dep_id).stream().findFirst();
    }
    @Override
    public int updateUniversity(University university) {
        String sql = "UPDATE universities SET uni_display_name = ?, uni_alt_name = ? WHERE uni_id = ?";
        return jdbcTemplate.update(sql, university.uni_display_name(), university.uni_alt_name(), university.uni_id());
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
    @Override
    public int addCourse(Course course) {
        String sql = "INSERT INTO courses(dep_id, semester, c_display_name, description) VALUES(?, ?, ?, ?)";
        return jdbcTemplate.update(sql, course.dep_id(), course.semester(), course.c_display_name(), course.description());
    }
    @Override
    public int updateCourse(Course course) {
        String sql = "UPDATE courses SET dep_id = ?, semester = ?, c_display_name = ?, description = ? WHERE c_id = ?";
        return jdbcTemplate.update(sql, course.dep_id(), course.semester(), course.c_display_name(), course.description(), course.c_id());
    }
    @Override
    public int deleteCourse(Integer c_id) {
        String sql = "DELETE FROM courses WHERE c_id = ?";
        return jdbcTemplate.update(sql, c_id);
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

    @Override
    public int addNote(Note note) {
        String sql = "INSERT INTO notes (c_id, type, note_display_name, note_filename) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, note.c_id(), note.type(), note.note_display_name(), note.note_filename());
    }

    @Override
    public int updateNote(Note note) {
        String sql = "UPDATE notes SET c_id = ?, type = ?, note_display_name = ?, note_filename = ? WHERE note_id = ?";
        return jdbcTemplate.update(sql, note.c_id(), note.type(), note.note_display_name(), note.note_filename(), note.note_id());
    }

    @Override
    public int deleteNote(Integer note_id) {
        String sql = "DELETE FROM notes WHERE note_id = ?";
        return jdbcTemplate.update(sql, note_id);
    }
}