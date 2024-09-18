package gr.symfoititis.common.dao;

import gr.symfoititis.common.records.Course;

import java.util.List;
import java.util.Optional;

public interface CoursesDao {

    /**
     *
     * Courses
     */
    List<Course> getCourses (Integer dep_id);
    Optional<Course> getCourse (Integer c_id);
}
