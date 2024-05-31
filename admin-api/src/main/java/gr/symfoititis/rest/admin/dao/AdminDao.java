package gr.symfoititis.rest.admin.dao;

import gr.symfoititis.rest.admin.records.Course;
import gr.symfoititis.rest.admin.records.Department;
import gr.symfoititis.rest.admin.records.Note;
import gr.symfoititis.rest.admin.records.University;

import java.util.List;
import java.util.Optional;

public interface AdminDao {


    /**
     *
     * Universities
     */
    List<University> getUniversities ();
    Optional<University> getUniversity (Integer uni_id);
    int addUniversity (University university);
    int updateUniversity (University university);
    int deleteUniversity (Integer uni_id);


    /**
     *
     * Departments
     */
    List<Department> getDepartments ();
    List<Department> getDepartments (Integer uni_id);
    Optional<Department> getDepartment (Integer dep_id);
    int addDepartment (Department department);
    int updateDepartment (Department department);
    int deleteDepartment (Integer dep_id);


    /**
     *
     * Courses
     */
    List<Course> getCourses (Integer dep_id);
    Optional<Course> getCourse (Integer c_id);
    int addCourse(Course course);
    int updateCourse(Course course);
    int deleteCourse(Integer c_id);


    /**
     *
     * Notes
     */
    List<Note> getNotes (Integer c_id);
    int addNote (Note note);
    int updateNote(Note note);
    int deleteNote (Integer note_id);
}

