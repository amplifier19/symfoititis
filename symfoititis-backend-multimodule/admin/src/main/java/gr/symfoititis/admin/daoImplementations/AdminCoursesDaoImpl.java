package gr.symfoititis.admin.daoImplementations;

import gr.symfoititis.admin.dao.CoursesDao;
import gr.symfoititis.common.records.Course;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AdminCoursesDaoImpl implements CoursesDao {
    private final JdbcTemplate jdbcTemplate;
    public AdminCoursesDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     *
     * Courses
     */
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
}
