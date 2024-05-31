package gr.symfoititis.rest.student.controllers;

import gr.symfoititis.rest.student.records.*;
import gr.symfoititis.rest.student.services.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    /**
     *
     * Universities
     */
    @GetMapping("/universities")
    ResponseEntity<Response> universities () {
        List<University> universities = studentService.getUniversities();
        String message = String.format("Successfully fetched %d universities", universities.size());
        return ResponseEntity.ok(new Response(universities, message, 200));
    }
    @GetMapping("/university")
    public ResponseEntity<Response> university (@AuthenticationPrincipal Jwt jwt) {
        int uni_id = Integer.parseInt(jwt.getClaimAsString("uni_id"));
        University university = studentService.getUniversity(uni_id);
        String message = String.format("Successfully fetched university with id: %d", uni_id);
        return ResponseEntity.ok(new Response(university,message, 200));
    }


    /**
     *
     * Departments
     */
    @GetMapping("/departments")
    ResponseEntity<Response> departments () {
        List<Department> departments = studentService.getDepartments ();
        String message = String.format("Successfully fetched %d departments", departments.size());
        return ResponseEntity.ok(new Response(departments, message, 200));
    }

    @GetMapping("/departments/{uni_id}")
    ResponseEntity<Response> departments (@PathVariable (value="uni_id", required=true) int uni_id) {
        List<Department> departments = studentService.getDepartments (uni_id);
        String message = String.format("Successfully fetched %d of university with id: %d", departments.size(), uni_id);
        return ResponseEntity.ok(new Response (departments, message, 200));
    }
    @GetMapping("/department")
    ResponseEntity<Response> department (@AuthenticationPrincipal Jwt jwt) {
        int dep_id = Integer.parseInt(jwt.getClaimAsString("dep_id"));
        Department department = studentService.getDepartment(dep_id);
        String message = String.format("Successfully fetched university with id: %d", dep_id);
        return ResponseEntity.ok(new Response (department, message, 200));
    }


    /**
     *
     * Courses
     */
    @GetMapping("/courses")
    public ResponseEntity<Response> courses (@AuthenticationPrincipal Jwt jwt) {
        int dep_id = Integer.parseInt(jwt.getClaimAsString("dep_id"));
        List<Course> courses = studentService.getCourses(dep_id);
        String message = String.format("Successfully fetched %d courses", courses.size());
        return ResponseEntity.ok(new Response(courses, message, 200));
    }
    // Might remove
    @GetMapping("/course/{c_id}")
    public ResponseEntity<Response> course (@PathVariable(value="c_id", required=true) int c_id) {
        Course course = studentService.getCourse(c_id);
        String message = String.format("Successfully fetched course with id: %d", c_id);
        return ResponseEntity.ok(new Response (course, message, 200));
    }


    /**
     *
     * Notes
     */
    @GetMapping ("/notes/{c_id}")
    ResponseEntity<Response> getNotes (@PathVariable(value="c_id", required=true) Integer c_id) {
        List<Note> notes = studentService.getNotes(c_id);
        String message = String.format("Successfully fetched %d notes", notes.size());
        return ResponseEntity.ok(new Response(notes, message, 200));
    }
}
