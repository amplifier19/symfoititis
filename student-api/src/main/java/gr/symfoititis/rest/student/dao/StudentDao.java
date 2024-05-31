package gr.symfoititis.rest.student.dao;

import gr.symfoititis.rest.student.records.Course;
import gr.symfoititis.rest.student.records.Note;
import gr.symfoititis.rest.student.records.University;
import gr.symfoititis.rest.student.records.Department;

import java.util.List;
import java.util.Optional;

public interface StudentDao {


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
