package gr.symfoititis.education.dao;

import gr.symfoititis.education.records.Course;

import java.util.List;
import java.util.Optional;

public interface CoursesDao {
    List<Course> getCourses (Integer dep_id);
    Optional<Course> getCourse (Integer c_id, Integer dep_id);
    void addCourse(Course course);
    int updateCourse(Course course);
    int deleteCourse(Integer c_id);
}
