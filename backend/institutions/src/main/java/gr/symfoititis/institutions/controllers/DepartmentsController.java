package gr.symfoititis.institutions.controllers;

import gr.symfoititis.common.exceptions.BadRequestException;
import gr.symfoititis.common.records.Response;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import gr.symfoititis.institutions.records.Department;
import gr.symfoititis.institutions.services.DepartmentsService;

import java.util.List;

import static gr.symfoititis.common.utils.RoleValidation.isAdmin;
import static gr.symfoititis.common.utils.RoleValidation.isStudentOrTeacher;

@Validated
@RestController
public class DepartmentsController {
    private final DepartmentsService departmentsService;
    public DepartmentsController(DepartmentsService departmentsService) {
        this.departmentsService = departmentsService;
    }

    @GetMapping("/departments")
    ResponseEntity<Response> departments (
            @NotNull
            @NotBlank
            @RequestHeader("X-Role")
            String role
    ) {
        isAdmin(role);
        List<Department> departments = departmentsService.getDepartments ();
        return ResponseEntity.ok(new Response(200, departments));
    }

    @GetMapping("/departments/{uni_id}")
    ResponseEntity<Response> departments (
            @Positive
            @PathVariable (value="uni_id", required=true)
            int uni_id
    ) {
        List<Department> departments = departmentsService.getDepartments (uni_id);
        return ResponseEntity.ok(new Response (200, departments));
    }

    @GetMapping("/department")
    ResponseEntity<Response> department (
            @NotNull
            @NotBlank
            @RequestHeader("X-Department-Id")
            String id,
            @NotNull
            @NotBlank
            @RequestHeader("X-Role")
            String role
    ) {
        isStudentOrTeacher(role);
        try {
            int dep_id = Integer.parseInt(id);
            Department department = departmentsService.getDepartment(dep_id);
            return ResponseEntity.ok(new Response (200, department));
        } catch (NumberFormatException ex) {
            throw new BadRequestException("Department id could not be parsed to integer");
        }
    }

    @GetMapping("/department/{dep_id}")
    ResponseEntity<Response> department (
            @NotNull
            @NotBlank
            @RequestHeader("X-Role")
            String role,
            @Positive
            @PathVariable(value="dep_id", required=true)
            int dep_id
    ) {
        isAdmin(role);
        Department department = departmentsService.getDepartment(dep_id);
        return ResponseEntity.ok(new Response (200, department));
    }

    @PostMapping("/department")
    ResponseEntity<Response> addDepartment (
            @NotNull
            @NotBlank
            @RequestHeader("X-Role")
            String role,
            @RequestBody @Valid Department department
    ) {
        isAdmin(role);
        departmentsService.addDepartment(department);
        String message = "Successfully added department";
        return ResponseEntity.ok(new Response (200, message));
    }

    @PutMapping("/department")
    ResponseEntity<Response> updateDepartment (
            @NotNull
            @NotBlank
            @RequestHeader("X-Role")
            String role,
            @RequestBody @Valid Department department
    ) {
        isAdmin(role);
        departmentsService.updateDepartment (department);
        String message = String.format("Successfully updated department with id: %d", department.dep_id());
        return ResponseEntity.ok(new Response (200, message));
    }

    @DeleteMapping("/department/{dep_id}")
    ResponseEntity<Response> deleteDepartment (
            @NotNull
            @NotBlank
            @RequestHeader("X-Role")
            String role,
            @Positive
            @PathVariable(value="dep_id", required=true) int dep_id
    ){
        isAdmin(role);
        departmentsService.deleteDepartment(dep_id);
        String message = String.format("Successfully deleted departments with id: %d", dep_id);
        return ResponseEntity.ok(new Response( 200, message));
    }
}
