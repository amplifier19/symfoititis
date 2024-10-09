package gr.symfoititis.institutions.dao;


import gr.symfoititis.institutions.records.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentsDao {
    List<Department> getDepartments ();
    List<Department> getDepartments (Integer uni_id);
    Optional<Department> getDepartment (Integer dep_id);
    int addDepartment (Department department);
    int updateDepartment (Department department);
    int deleteDepartment (Integer dep_id);
}
