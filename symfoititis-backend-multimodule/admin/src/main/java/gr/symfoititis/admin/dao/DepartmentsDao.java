package gr.symfoititis.admin.dao;

import gr.symfoititis.common.records.Department;

public interface DepartmentsDao {

    /**
     *
     * Departments
     */
    int addDepartment (Department department);
    int updateDepartment (Department department);
    int deleteDepartment (Integer dep_id);
}
