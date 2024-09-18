package gr.symfoititis.admin.services;

import gr.symfoititis.admin.exceptions.InternalServerErrorException;
import gr.symfoititis.common.exceptions.BadRequestException;
import gr.symfoititis.common.exceptions.NotFoundException;
import gr.symfoititis.common.records.Course;
import gr.symfoititis.admin.dao.CoursesDao;
import gr.symfoititis.common.services.DepartmentsService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CoursesService {
    private final CoursesDao coursesDao;
    private final DepartmentsService commonDepartmentsService;
    public CoursesService (CoursesDao coursesDao, DepartmentsService commonDepartmentsService) {
        this.coursesDao = coursesDao;
        this.commonDepartmentsService = commonDepartmentsService;
    }

    /**
     *
     * Courses
     */
    public void addCourse (Course course) {
        if (
                Objects.isNull(course) ||
                        Objects.isNull(course.dep_id()) ||
                        course.dep_id().compareTo(0) <=0 ||
                        Objects.isNull(course.semester()) ||
                        course.c_display_name().isBlank()
        ) {
            throw new BadRequestException("Bad Request");
        }
        try {
            int status = coursesDao.addCourse(course);
            if (status == 0) {
                throw new InternalServerErrorException("Internal Server Error");
            }
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
                        course.dep_id().compareTo(0) <=0 ||
                        Objects.isNull(course.semester()) ||
                        course.c_display_name().isBlank()
        ) {
            throw new BadRequestException("Bad Request");
        }
        commonDepartmentsService.getDepartment(course.dep_id());
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
        if (Objects.isNull(c_id) || c_id.compareTo(0) <=0) {
            throw new BadRequestException("Bad Request");
        }
        int status = coursesDao.deleteCourse(c_id);
        if (status == 0) {
            throw new NotFoundException("Course Not Found");
        }
    }
}
