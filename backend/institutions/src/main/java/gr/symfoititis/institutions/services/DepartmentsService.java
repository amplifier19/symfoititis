package gr.symfoititis.institutions.services;

import gr.symfoititis.institutions.dao.DepartmentsDao;
import gr.symfoititis.common.exceptions.BadRequestException;
import gr.symfoititis.common.exceptions.InternalServerErrorException;
import gr.symfoititis.common.exceptions.NotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import gr.symfoititis.institutions.records.Department;

import java.util.List;

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
        return departmentsDao.getDepartments(uni_id);
    }

    public Department getDepartment (Integer dep_id) {
        return departmentsDao.getDepartment (dep_id).orElseThrow(() -> new NotFoundException("Department Not Found"));
    }

    public void addDepartment (Department department) {
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
        int status = departmentsDao.deleteDepartment(dep_id);
        if (status == 0) {
            throw new NotFoundException("Department Not Found");
        }
    }
}
