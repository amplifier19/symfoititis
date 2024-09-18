package gr.symfoititis.admin.controllers;

import gr.symfoititis.admin.services.CoursesService;
import gr.symfoititis.common.records.Course;
import gr.symfoititis.common.records.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/admin")
public class CoursesController {
    private final CoursesService coursesService;
    private final gr.symfoititis.common.services.CoursesService commonCoursesService;

    public CoursesController(CoursesService coursesService, gr.symfoititis.common.services.CoursesService commonCoursesService) {
        this.coursesService = coursesService;
        this.commonCoursesService = commonCoursesService;
    }

    /**
     *
     * Courses
     */
    @GetMapping("/courses/{dep_id}")
    ResponseEntity<Response> courses (@PathVariable(value="dep_id", required=true)Integer dep_id) {
        List<Course> courses = commonCoursesService.getCourses(dep_id);
        return ResponseEntity.ok(new Response(200, courses));
    }
    @GetMapping("/course/{c_id}")
    ResponseEntity<Response> course (@PathVariable(value="c_id", required=true) int c_id) {
        Course course = commonCoursesService.getCourse(c_id);
        String message = String.format("Successfully fetched course with id: %d", c_id);
        return ResponseEntity.ok(new Response (200, course));
    }
    @PostMapping("/course")
    ResponseEntity<Response> addCourse (@RequestBody Course course) {
        coursesService.addCourse (course);
        String message = "Successfully added course";
        return ResponseEntity.ok(new Response (200, message));    }
    @PutMapping("/course")
    ResponseEntity<Response> updateCourse (@RequestBody Course course) {
        coursesService.updateCourse(course);
        String message = String.format("Successfully updated course with id: %d", course.c_id());
        return ResponseEntity.ok(new Response(200, message));
    }
    @DeleteMapping("/course/{c_id}")
    ResponseEntity<Response> deleteCourse (@PathVariable(value="c_id", required=true) Integer c_id) {
        coursesService.deleteCourse(c_id);
        String message = String.format("Successfully deleted course with id: %d", c_id);
        return ResponseEntity.ok(new Response (200, message));
    }
}
