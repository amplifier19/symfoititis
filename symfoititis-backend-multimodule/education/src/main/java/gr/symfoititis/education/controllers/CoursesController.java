package gr.symfoititis.education.controllers;

import gr.symfoititis.common.records.Response;
import gr.symfoititis.education.records.Course;
import gr.symfoititis.education.services.CoursesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static gr.symfoititis.common.utils.RoleValidation.*;

@RestController
public class CoursesController {
    private final CoursesService coursesService;

    public CoursesController(CoursesService coursesService) {
        this.coursesService = coursesService;
    }

    @GetMapping("/courses")
    public ResponseEntity<Response> courses (
            @RequestHeader("X-Department-Id") String d_id,
            @RequestHeader("X-Role-Id") String role
    ) {
        isStudentOrTeacher(role);
        int dep_id = Integer.parseInt(d_id);
        List<Course> courses = coursesService.getCourses(dep_id);
        return ResponseEntity.ok(new Response(200, courses));
    }

    @GetMapping("/course/{c_id}")
    ResponseEntity<Response> course (
            @PathVariable(value="c_id", required=true) int c_id,
            @RequestHeader("X-Department-Id") String d_id,
            @RequestHeader("X-Role") String role
    ) {
        isStudentOrTeacher(role);
        int dep_id = Integer.parseInt(d_id);
        Course course = coursesService.getCourse(c_id, dep_id);
        return ResponseEntity.ok(new Response (200, course));
    }

    @GetMapping("/courses/{dep_id}")
    ResponseEntity<Response> courses (
            @PathVariable(value="dep_id", required=true) int dep_id,
            @RequestHeader("X-Role") String role
    ) {
        isAdmin(role);
        List<Course> courses = coursesService.getCourses(dep_id);
        return ResponseEntity.ok(new Response(200, courses));
    }

    @PostMapping("/course")
    ResponseEntity<Response> addCourse (
            @RequestBody Course course,
            @RequestHeader("X-Role") String role
    ) {
        isAdmin(role);
        coursesService.addCourse (course);
        String message = "Successfully added course";
        return ResponseEntity.ok(new Response (200, message));
    }

    @PutMapping("/course")
    ResponseEntity<Response> updateCourse (
            @RequestBody Course course,
            @RequestHeader("X-Role") String role
    ) {
        isAdmin(role);
        coursesService.updateCourse(course);
        String message = String.format("Successfully updated course with id: %d", course.c_id());
        return ResponseEntity.ok(new Response(200, message));
    }

    @DeleteMapping("/course/{c_id}")
    ResponseEntity<Response> deleteCourse (
            @PathVariable(value="c_id", required=true) Integer c_id,
            @RequestHeader("X-Role") String role
    ) {
        isAdmin(role);
        coursesService.deleteCourse(c_id);
        String message = String.format("Successfully deleted course with id: %d", c_id);
        return ResponseEntity.ok(new Response (200, message));
    }
}
