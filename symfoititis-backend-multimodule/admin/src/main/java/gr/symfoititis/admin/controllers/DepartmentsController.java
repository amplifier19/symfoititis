package gr.symfoititis.admin.controllers;

import gr.symfoititis.admin.services.AdminDepartmentsService;
import gr.symfoititis.common.records.Department;
import gr.symfoititis.common.records.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/admin")
public class DepartmentsController {
    private final AdminDepartmentsService adminDepartmentsService;
    private final gr.symfoititis.common.services.DepartmentsService commonDepartmentsService;


    public DepartmentsController(AdminDepartmentsService adminDepartmentsService, gr.symfoititis.common.services.DepartmentsService commonDepartmentsService) {
        this.adminDepartmentsService = adminDepartmentsService;
        this.commonDepartmentsService = commonDepartmentsService;
    }

    /**
     *
     * Departments
     */
    @GetMapping("/departments")
    ResponseEntity<Response> departments () {
        List<Department> departments = commonDepartmentsService.getDepartments ();
        return ResponseEntity.ok(new Response(200, departments));
    }
    @GetMapping("/departments/{uni_id}")
    ResponseEntity<Response> departments (@PathVariable(value="uni_id", required=true) Integer uni_id) {
        List<Department> departments = commonDepartmentsService.getDepartments (uni_id);
        return ResponseEntity.ok(new Response (200, departments));
    }
    @GetMapping("/department/{dep_id}")
    ResponseEntity<Response> department (@PathVariable(value="dep_id", required=true) Integer dep_id) {
        Department department = commonDepartmentsService.getDepartment(dep_id);
        return ResponseEntity.ok(new Response (200, department));
    }
    @PostMapping("/department")
    ResponseEntity<Response> addDepartment (@RequestBody Department department) {
        adminDepartmentsService.addDepartment(department);
        String message = "Successfully added department";
        return ResponseEntity.ok(new Response (200, message));
    }
    @PutMapping("/department")
    ResponseEntity<Response> updateDepartment (@RequestBody Department department) {
        adminDepartmentsService.updateDepartment (department);
        String message = String.format("Successfully updated department with id: %d", department.dep_id());
        return ResponseEntity.ok(new Response (200, message));
    }
    @DeleteMapping("/department/{dep_id}")
    ResponseEntity<Response> deleteDepartment (@PathVariable(value="dep_id", required=true) Integer dep_id){
        adminDepartmentsService.deleteDepartment(dep_id);
        String message = String.format("Successfully deleted departments with id: %d", dep_id);
        return ResponseEntity.ok(new Response( 200, message));
    }
}
