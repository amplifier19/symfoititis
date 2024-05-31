package gr.symfoititis.rest.student.services;

import gr.symfoititis.rest.student.exceptions.BadRequestException;
import gr.symfoititis.rest.student.exceptions.NotFoundException;
import gr.symfoititis.rest.student.dao.StudentDao;
import gr.symfoititis.rest.student.records.Course;
import gr.symfoititis.rest.student.records.Note;
import gr.symfoititis.rest.student.records.University;
import gr.symfoititis.rest.student.records.Department;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class StudentService {
    private final StudentDao studentDao;
    public StudentService(StudentDao studentDao) {
        this.studentDao = studentDao;
    }


    /**
     *
     * Universities
     */
    public List<University> getUniversities () {
        return studentDao.getUniversities();
    }
    public University getUniversity (Integer uni_id) {
        if (Objects.isNull(uni_id) || uni_id <=0) {
            throw new BadRequestException("Bad Request");
        }
        return studentDao.getUniversity(uni_id).orElseThrow(() -> new NotFoundException("University Not Found"));
    }


    /**
     *
     * Departments
     */
    public List<Department> getDepartments () {
        return studentDao.getDepartments();
    }
    public List<Department> getDepartments (Integer uni_id) {
        if (Objects.isNull(uni_id) || uni_id.compareTo(0) <= 0) {
            throw new BadRequestException("Bad Request");
        }
        return studentDao.getDepartments(uni_id);
    }
    public Department getDepartment (Integer dep_id) {
        if (Objects.isNull(dep_id) || dep_id.compareTo(0) <= 0) {
            throw new BadRequestException("Bad Request");
        }
        return studentDao.getDepartment (dep_id).orElseThrow(() -> new NotFoundException("Department Not Found"));
    }


    /**
     *
     * Courses
     */
    public List<Course> getCourses (Integer dep_id) {
        if (Objects.isNull(dep_id) || dep_id.compareTo(0) <= 0) {
            throw new BadRequestException("Bad Request");
        }
        return studentDao.getCourses(dep_id);
    }
    public Course getCourse (Integer c_id) {
        if (Objects.isNull(c_id) || c_id.compareTo(0) <= 0) {
            throw new BadRequestException("Bad Request");
        }
        return studentDao.getCourse(c_id).orElseThrow(() -> new NotFoundException("Course Not Found"));
    }


    /**
     *
     * Notes
     */
    public List<Note> getNotes (Integer c_id) {
        if (Objects.isNull(c_id) || c_id.compareTo(0) <= 0) {
            throw new BadRequestException("Bad Request");
        }
        return studentDao.getNotes(c_id);
    }
}
