package gr.symfoititis.education.controllers;

import gr.symfoititis.common.records.Response;
import gr.symfoititis.education.records.Course;
import gr.symfoititis.education.services.CoursesService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
            @NotNull
            @NotBlank
            @RequestHeader("X-Role")
            String role,
            @NotNull
            @NotBlank
            @RequestHeader("X-Department-Id")
            String d_id
    ) {
        isStudentOrTeacher(role);
        int dep_id = Integer.parseInt(d_id);
        List<Course> courses = coursesService.getCourses(dep_id);
        return ResponseEntity.ok(new Response(200, courses));
    }

    @GetMapping("/course/{c_id}")
    ResponseEntity<Response> course (
            @NotNull
            @NotBlank
            @RequestHeader("X-Role")
            String role,
            @NotNull
            @NotBlank
            @RequestHeader("X-Department-Id")
            String d_id,
            @Positive
            @PathVariable(value="c_id", required=true)
            int c_id
    ) {
        isStudentOrTeacher(role);
        int dep_id = Integer.parseInt(d_id);
        Course course = coursesService.getCourse(c_id, dep_id);
        return ResponseEntity.ok(new Response (200, course));
    }

    @GetMapping("/courses/{dep_id}")
    ResponseEntity<Response> courses (
            @NotNull
            @NotBlank
            @RequestHeader("X-Role")
            String role,
            @Positive
            @PathVariable(value="dep_id", required=true)
            int dep_id
    ) {
        isAdmin(role);
        List<Course> courses = coursesService.getCourses(dep_id);
        return ResponseEntity.ok(new Response(200, courses));
    }

    @PostMapping("/course")
    ResponseEntity<Response> addCourse (
            @NotNull
            @NotBlank
            @RequestHeader("X-Role")
            String role,
            @RequestBody @Valid Course course
    ) {
        isAdmin(role);
        coursesService.addCourse (course);
        String message = "Successfully added course";
        return ResponseEntity.ok(new Response (200, message));
    }

    @PutMapping("/course")
    ResponseEntity<Response> updateCourse (
            @NotNull
            @NotBlank
            @RequestHeader("X-Role")
            String role,
            @RequestBody @Valid Course course
    ) {
        isAdmin(role);
        coursesService.updateCourse(course);
        String message = String.format("Successfully updated course with id: %d", course.c_id());
        return ResponseEntity.ok(new Response(200, message));
    }

    @DeleteMapping("/course/{c_id}")
    ResponseEntity<Response> deleteCourse (
            @NotNull
            @NotBlank
            @RequestHeader("X-Role")
            String role,
            @Positive
            @PathVariable(value="c_id", required=true)
            int c_id
    ) {
        isAdmin(role);
        coursesService.deleteCourse(c_id);
        String message = String.format("Successfully deleted course with id: %d", c_id);
        return ResponseEntity.ok(new Response (200, message));
    }
}
