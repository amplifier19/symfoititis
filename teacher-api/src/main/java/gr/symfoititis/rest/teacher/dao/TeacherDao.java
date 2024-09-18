package gr.symfoititis.rest.teacher.dao;

import gr.symfoititis.rest.teacher.records.Course;
import gr.symfoititis.rest.teacher.records.Department;
import gr.symfoititis.rest.teacher.records.Note;
import gr.symfoititis.rest.teacher.records.University;

import java.util.List;
import java.util.Optional;

public interface TeacherDao {


    /**
     *
     * Universities
     */
    List<University> getUniversities ();
    Optional<University> getUniversity (Integer uni_id);


    /**
     *
     * Departments
     */
    List<Department> getDepartments ();
    List<Department> getDepartments (Integer uni_id);
    Optional<Department> getDepartment (Integer dep_id);


    /**
     *
     * Courses
     */
    List<Course> getCourses (Integer dep_id);
    Optional<Course> getCourse (Integer c_id);

    /**
     *
     * Notes
     */
    List<Note> getNotes (Integer c_id);
}
