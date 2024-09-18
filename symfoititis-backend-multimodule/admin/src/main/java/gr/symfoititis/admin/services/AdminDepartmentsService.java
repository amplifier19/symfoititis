package gr.symfoititis.admin.services;

import gr.symfoititis.admin.dao.DepartmentsDao;
import gr.symfoititis.admin.exceptions.InternalServerErrorException;
import gr.symfoititis.common.exceptions.BadRequestException;
import gr.symfoititis.common.exceptions.NotFoundException;
import gr.symfoititis.common.records.Department;
import gr.symfoititis.common.services.UniversitiesService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AdminDepartmentsService {
    private final DepartmentsDao departmentsDao;
    private final UniversitiesService commonUniversitiesService;
    public AdminDepartmentsService(DepartmentsDao departmentsDao, UniversitiesService universitiesService) {
        this.departmentsDao = departmentsDao;
        this.commonUniversitiesService = universitiesService;
    }

    /**
     *
     * Departments
     */
    public void addDepartment (Department department) {
        if (
                Objects.isNull(department) ||
                        Objects.isNull(department.uni_id()) ||
                        department.uni_id().compareTo(0) <= 0 ||
                        department.dep_display_name().isBlank() ||
                        department.dep_alt_name().isBlank()
        ) {
            throw new BadRequestException("Bad Request");
        }
        commonUniversitiesService.getUniversity(department.uni_id());
        try {
            int status = departmentsDao.addDepartment(department);
            if (status == 0) {
                throw new InternalServerErrorException("Internal Server Error");
            }
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Bad Request");
        } catch (InternalServerErrorException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }
    public void updateDepartment (Department department) {
        if (
                Objects.isNull(department) ||
                        Objects.isNull(department.dep_id()) ||
                        department.dep_id().compareTo(0) <= 0||
                        Objects.isNull(department.uni_id()) ||
                        department.uni_id().compareTo(0) <= 0 ||
                        department.dep_display_name().isBlank() ||
                        department.dep_alt_name().isBlank()
        ) {
            throw new BadRequestException("Bad Request");
        }
        commonUniversitiesService.getUniversity(department.uni_id());
        try {
            int status = departmentsDao.updateDepartment(department);
            if (status == 0) {
                throw new NotFoundException("Department Not Found");
            }
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Bad Request");
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
    }
    public void deleteDepartment (Integer dep_id) {
        if (Objects.isNull(dep_id) || dep_id.compareTo(0) <= 0) {
            throw new BadRequestException ("Bad Request");
        }
        int status = departmentsDao.deleteDepartment(dep_id);
        if (status == 0) {
            throw new NotFoundException("Department Not Found");
        }
    }
}
