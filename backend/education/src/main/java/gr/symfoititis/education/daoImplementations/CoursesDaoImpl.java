package gr.symfoititis.education.daoImplementations;

import gr.symfoititis.education.dao.CoursesDao;
import gr.symfoititis.education.records.Course;
import gr.symfoititis.education.rowMappers.CoursesRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CoursesDaoImpl implements CoursesDao {
    protected final JdbcTemplate jdbcTemplate;

    public CoursesDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Course> getCourses(Integer dep_id) {
        String sql = "SELECT * FROM courses c WHERE c.dep_id = ? ORDER BY c.semester";
        return jdbcTemplate.query(sql, new CoursesRowMapper(), dep_id);
    }

    @Override
    public Optional<Course> getCourse(Integer c_id, Integer dep_id) {
        String sql = "SELECT * FROM courses c WHERE c.c_id = ? AND c.dep_id = ?";
        return jdbcTemplate.query (sql, new CoursesRowMapper(), c_id, dep_id).stream().findFirst();
    }

    @Override
    public Optional<Course> getCourse(Integer c_id) {
        String sql = "SELECT * FROM courses WHERE c_id = ?";
        return jdbcTemplate.query (sql, new CoursesRowMapper(), c_id).stream().findFirst();
    }

    @Override
    public void addCourse(Course course) {
        String sql = "INSERT INTO courses(dep_id, semester, c_display_name) VALUES(?, ?, ?)";
        jdbcTemplate.update(sql, course.dep_id(), course.semester(), course.c_display_name());
    }

    @Override
    public int updateCourse(Course course) {
        String sql = "UPDATE courses SET dep_id = ?, semester = ?, c_display_name = ? WHERE c_id = ?";
        return jdbcTemplate.update(sql, course.dep_id(), course.semester(), course.c_display_name(), course.c_id());
    }

    @Override
    public int deleteCourse(Integer c_id) {
        String sql = "DELETE FROM courses WHERE c_id = ?";
        return jdbcTemplate.update(sql, c_id);
    }
}