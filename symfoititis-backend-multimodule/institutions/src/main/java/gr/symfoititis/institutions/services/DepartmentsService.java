package gr.symfoititis.institutions.services;

import gr.symfoititis.institutions.dao.DepartmentsDao;
import gr.symfoititis.common.exceptions.BadRequestException;
import gr.symfoititis.common.exceptions.InternalServerErrorException;
import gr.symfoititis.common.exceptions.NotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import gr.symfoititis.institutions.records.Department;

import java.util.List;
import java.util.Objects;

@Service
public class DepartmentsService {
    private final DepartmentsDao departmentsDao;
    private final UniversitiesService universitiesService;

    public DepartmentsService(DepartmentsDao departmentsDao, UniversitiesService universitiesService) {
        this.departmentsDao = departmentsDao;
        this.universitiesService = universitiesService;
    }

    public List<Department> getDepartments () {
        return departmentsDao.getDepartments();
    }
    public List<Department> getDepartments (Integer uni_id) {
        if (Objects.isNull(uni_id) || uni_id.compareTo(0) <= 0) {
            throw new BadRequestException("Bad Request");
        }
        return departmentsDao.getDepartments(uni_id);
    }
    public Department getDepartment (Integer dep_id) {
        if (Objects.isNull(dep_id) || dep_id.compareTo(0) <= 0) {
            throw new BadRequestException("Bad Request");
        }
        return departmentsDao.getDepartment (dep_id).orElseThrow(() -> new NotFoundException("Department Not Found"));
    }
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
        universitiesService.getUniversity(department.uni_id());
        try {
            departmentsDao.addDepartment(department);
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
        universitiesService.getUniversity(department.uni_id());
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
