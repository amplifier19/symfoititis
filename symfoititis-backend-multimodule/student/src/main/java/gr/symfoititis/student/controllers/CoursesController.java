package gr.symfoititis.student.controllers;

import gr.symfoititis.common.records.Course;
import gr.symfoititis.common.records.Response;
import gr.symfoititis.common.services.CoursesService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/student")
public class CoursesController {
    public final CoursesService coursesService;
    public CoursesController (CoursesService coursesService) {
        this.coursesService = coursesService;
    }

    /**
     *
     * Courses
     */
    @GetMapping("/courses")
    public ResponseEntity<Response> courses (@RequestHeader("X-Department-Id") String id) {
        int dep_id = Integer.parseInt(id);
        List<Course> courses = coursesService.getCourses(dep_id);
        String message = String.format("Successfully fetched %d courses", courses.size());
        return ResponseEntity.ok(new Response(200, courses));
    }

    @GetMapping("/course/{c_id}")
    public ResponseEntity<Response> course (@PathVariable(value="c_id", required=true) int c_id) {
        Course course = coursesService.getCourse(c_id);
        return ResponseEntity.ok(new Response (200, course));
    }
}
