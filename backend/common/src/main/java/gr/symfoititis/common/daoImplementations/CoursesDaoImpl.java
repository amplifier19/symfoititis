package gr.symfoititis.common.repositories;

import gr.symfoititis.common.dao.CoursesDao;
import gr.symfoititis.common.records.Course;
import gr.symfoititis.common.rowMappers.CoursesRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CoursesDaoImpl implements CoursesDao {
    private final JdbcTemplate jdbcTemplate;

    public CoursesDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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

}
