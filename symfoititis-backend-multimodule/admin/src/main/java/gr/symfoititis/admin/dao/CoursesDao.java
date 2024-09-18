package gr.symfoititis.admin.dao;

import gr.symfoititis.common.records.Course;

public interface CoursesDao {

    /**
     *
     * Courses
     */
    int addCourse(Course course);
    int updateCourse(Course course);
    int deleteCourse(Integer c_id);
}
