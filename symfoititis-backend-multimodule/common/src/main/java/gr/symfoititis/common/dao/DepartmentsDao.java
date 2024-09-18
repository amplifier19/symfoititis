package gr.symfoititis.common.dao;

import gr.symfoititis.common.records.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentsDao {

    /**
     *
     * Departments
     */
    List<Department> getDepartments ();
    List<Department> getDepartments (Integer uni_id);
    Optional<Department> getDepartment (Integer dep_id);
}
