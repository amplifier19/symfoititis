package gr.symfoititis.education.services;

import gr.symfoititis.common.exceptions.InternalServerErrorException;
import gr.symfoititis.common.exceptions.BadRequestException;
import gr.symfoititis.common.exceptions.NotFoundException;
import gr.symfoititis.education.dao.CoursesDao;
import gr.symfoititis.education.records.Course;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CoursesService {
    private final CoursesDao coursesDao;
    public CoursesService(CoursesDao coursesDao) {
        this.coursesDao = coursesDao;
    }

    public List<Course> getCourses (Integer dep_id) {
        if (Objects.isNull(dep_id) || dep_id.compareTo(0) <= 0) {
            throw new BadRequestException("Bad Request");
        }
        return coursesDao.getCourses(dep_id);
    }

    public Course getCourse (Integer c_id, Integer dep_id) {
        if (
                Objects.isNull(c_id) ||
                Objects.isNull(dep_id) ||
                c_id.compareTo(0) <= 0 ||
                dep_id.compareTo(0) <= 0
        ) {
            throw new BadRequestException("Bad Request");
        }
        return coursesDao.getCourse(c_id, dep_id).orElseThrow(() -> new NotFoundException("Course Not Found"));
    }

    public void addCourse (Course course) {
        if (
                Objects.isNull(course) ||
                        Objects.isNull(course.dep_id()) ||
                        course.dep_id().compareTo(0) <= 0 ||
                        Objects.isNull(course.semester()) ||
                        course.c_display_name().isBlank()
        ) {
            throw new BadRequestException("Bad Request");
        }
        try {
            coursesDao.addCourse(course);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Bad Request");
        } catch (InternalServerErrorException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    public void updateCourse (Course course) {
        if (
                Objects.isNull(course) ||
                        Objects.isNull(course.dep_id()) ||
                        course.dep_id().compareTo(0) <= 0 ||
                        Objects.isNull(course.semester()) ||
                        course.c_display_name().isBlank()
        ) {
            throw new BadRequestException("Bad Request");
        }
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
        if (Objects.isNull(c_id) || c_id.compareTo(0) <= 0) {
            throw new BadRequestException("Bad Request");
        }
        int status = coursesDao.deleteCourse(c_id);
        if (status == 0) {
            throw new NotFoundException("Course Not Found");
        }
    }
}
