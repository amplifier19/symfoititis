package gr.symfoititis.student.controller;

import gr.symfoititis.common.records.*;
import gr.symfoititis.common.services.DepartmentsService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/student")
public class DepartmentsController {
    private final DepartmentsService departmentsService;
    public DepartmentsController(DepartmentsService departmentsService) {
        this.departmentsService = departmentsService;
    }

    /**
     *
     * Departments
     */
    @GetMapping("/departments")
    ResponseEntity<Response> departments () {
        List<Department> departments = departmentsService.getDepartments ();

        return ResponseEntity.ok(new Response(200, departments));
    }

    @GetMapping("/departments/{uni_id}")
    ResponseEntity<Response> departments (@PathVariable (value="uni_id", required=true) int uni_id) {
        List<Department> departments = departmentsService.getDepartments (uni_id);
        return ResponseEntity.ok(new Response (200, departments));
    }

    @GetMapping("/department")
    ResponseEntity<Response> department (@RequestHeader("X-Department-Id") String id) {
        int dep_id = Integer.parseInt(id);
        Department department = departmentsService.getDepartment(dep_id);
        String message = String.format("Successfully fetched university with id: %d", dep_id);
        return ResponseEntity.ok(new Response (200, department));
    }
}
