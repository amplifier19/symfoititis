package gr.symfoititis.institutions.controllers;

import gr.symfoititis.common.records.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import gr.symfoititis.institutions.records.Department;
import gr.symfoititis.institutions.services.DepartmentsService;

import java.util.List;

import static gr.symfoititis.common.utils.RoleValidation.isAdmin;
import static gr.symfoititis.common.utils.RoleValidation.isStudentOrTeacher;

@RestController
public class DepartmentsController {
    private final DepartmentsService departmentsService;
    public DepartmentsController(DepartmentsService departmentsService) {
        this.departmentsService = departmentsService;
    }

    @GetMapping("/departments")
    ResponseEntity<Response> departments (@RequestHeader("X-Role") String role) {
        isAdmin(role);
        List<Department> departments = departmentsService.getDepartments ();
        return ResponseEntity.ok(new Response(200, departments));
    }

    @GetMapping("/departments/{uni_id}")
    ResponseEntity<Response> departments (@PathVariable (value="uni_id", required=true) int uni_id) {
        List<Department> departments = departmentsService.getDepartments (uni_id);
        return ResponseEntity.ok(new Response (200, departments));
    }

    @GetMapping("/department")
    ResponseEntity<Response> department (
            @RequestHeader("X-Department-Id") String id,
            @RequestHeader("X-Role") String role
    ) {
        isStudentOrTeacher(role);
        int dep_id = Integer.parseInt(id);
        Department department = departmentsService.getDepartment(dep_id);
        return ResponseEntity.ok(new Response (200, department));
    }

    @GetMapping("/department/{dep_id}")
    ResponseEntity<Response> department (
            @RequestHeader("X-Role") String role,
            @PathVariable(value="dep_id", required=true) Integer dep_id
    ) {
        isAdmin(role);
        Department department = departmentsService.getDepartment(dep_id);
        return ResponseEntity.ok(new Response (200, department));
    }

    @PostMapping("/department")
    ResponseEntity<Response> addDepartment (
            @RequestHeader("X-Role") String role,
            @RequestBody Department department
    ) {
        isAdmin(role);
        departmentsService.addDepartment(department);
        String message = "Successfully added department";
        return ResponseEntity.ok(new Response (200, message));
    }

    @PutMapping("/department")
    ResponseEntity<Response> updateDepartment (
            @RequestHeader("X-Role") String role,
            @RequestBody Department department
    ) {
        isAdmin(role);
        departmentsService.updateDepartment (department);
        String message = String.format("Successfully updated department with id: %d", department.dep_id());
        return ResponseEntity.ok(new Response (200, message));
    }

    @DeleteMapping("/department/{dep_id}")
    ResponseEntity<Response> deleteDepartment (
            @RequestHeader("X-Role") String role,
            @PathVariable(value="dep_id", required=true) Integer dep_id
    ){
        isAdmin(role);
        departmentsService.deleteDepartment(dep_id);
        String message = String.format("Successfully deleted departments with id: %d", dep_id);
        return ResponseEntity.ok(new Response( 200, message));
    }
}
