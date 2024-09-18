package gr.symfoititis.common.services;

import gr.symfoititis.common.dao.DepartmentsDao;
import gr.symfoititis.common.exceptions.BadRequestException;
import gr.symfoititis.common.exceptions.NotFoundException;
import gr.symfoititis.common.records.Department;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class DepartmentsService {
    private final DepartmentsDao departmentsDao;

    public DepartmentsService (DepartmentsDao departmentsDao) {
        this.departmentsDao = departmentsDao;
    }

    /**
     *
     * Departments
     */
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
}
