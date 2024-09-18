package gr.symfoititis.common.services;

import gr.symfoititis.common.dao.CoursesDao;
import gr.symfoititis.common.records.Course;
import gr.symfoititis.common.exceptions.BadRequestException;
import gr.symfoititis.common.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service

public class CoursesService {
    private final CoursesDao coursesDao;

    public CoursesService(CoursesDao coursesDao) {
        this.coursesDao = coursesDao;
    }

    /**
     *
     * Courses
     */
    public List<Course> getCourses (Integer dep_id) {
        if (Objects.isNull(dep_id) || dep_id.compareTo(0) <= 0) {
            throw new BadRequestException("Bad Request");
        }
        return coursesDao.getCourses(dep_id);
    }
    public Course getCourse (Integer c_id) {
        if (Objects.isNull(c_id) || c_id.compareTo(0) <= 0) {
            throw new BadRequestException("Bad Request");
        }
        return coursesDao.getCourse(c_id).orElseThrow(() -> new NotFoundException("Course Not Found"));
    }
}
