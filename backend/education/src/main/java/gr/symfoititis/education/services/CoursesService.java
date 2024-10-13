package gr.symfoititis.education.services;

import gr.symfoititis.common.exceptions.InternalServerErrorException;
import gr.symfoititis.common.exceptions.BadRequestException;
import gr.symfoititis.common.exceptions.NotFoundException;
import gr.symfoititis.education.dao.CoursesDao;
import gr.symfoititis.education.records.Course;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoursesService {
    private final CoursesDao coursesDao;
    public CoursesService(CoursesDao coursesDao) {
        this.coursesDao = coursesDao;
    }

    public List<Course> getCourses (Integer dep_id) {
        return coursesDao.getCourses(dep_id);
    }

    public Course getCourse (Integer c_id, Integer dep_id) {
        return coursesDao.getCourse(c_id, dep_id).orElseThrow(() -> new NotFoundException("Course Not Found"));
    }

    public void addCourse (Course course) {
        try {
            coursesDao.addCourse(course);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Bad Request");
        } catch (InternalServerErrorException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    public void updateCourse (Course course) {
        try {
            int status = coursesDao.updateCourse(course);
            if (status == 0) {
                throw new NotFoundException("Course Not Found");
            }
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Bad Request");
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
    }

    public void deleteCourse (Integer c_id) {
        int status = coursesDao.deleteCourse(c_id);
        if (status == 0) {
            throw new NotFoundException("Course Not Found");
        }
    }
}
