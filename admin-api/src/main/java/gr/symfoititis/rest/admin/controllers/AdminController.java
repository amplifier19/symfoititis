package gr.symfoititis.rest.admin.controllers;

import gr.symfoititis.rest.admin.records.*;
import gr.symfoititis.rest.admin.services.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping ("/rest/admin")
public class AdminController {
    private final AdminService adminService;
    AdminController(AdminService adminService) {
        this.adminService = adminService;
    }


    /**
     *
     * Universities
     */
    @GetMapping("/universities")
    ResponseEntity<Response> universities () {
        List<University> universities = adminService.getUniversities();
        String message = String.format("Successfully fetched %d universities", universities.size());
        return ResponseEntity.ok(new Response(universities, message, 200));
    }
    @GetMapping("/university/{uni_id}")
    ResponseEntity<Response> university (@PathVariable(value="uni_id", required=false) Integer uni_id) {
        University university = adminService.getUniversity(uni_id);
        String message = String.format("Successfully fetched university with id: %d", uni_id);
        return ResponseEntity.ok(new Response(university,message, 200));
    }
    @PostMapping("/university")
    ResponseEntity<Response> addUniversity (@RequestBody University university) {
        adminService.addUniversity(university);
        String message = "Successfully added university";
        return ResponseEntity.ok(new Response(null, message, 200));
    }
    @PutMapping ("/university")
    ResponseEntity<Response> updateUniversity (@RequestBody University university) {
        adminService.updateUniversity(university);
        String message = String.format("Successfully updated university with id: %d", university.uni_id());
        return ResponseEntity.ok(new Response(null, "Success", 200));
    }
    @DeleteMapping("/university/{uni_id}")
    ResponseEntity<Response> deleteUniversity (@PathVariable(value="uni_id", required=true) Integer uni_id) {
        adminService.deleteUniversity(uni_id);
        String message = String.format("Successfully deleted university with id: %d", uni_id);
        return ResponseEntity.ok(new Response(null, "Success", 200));
    }


    /**
     *
     * Departments
     */
    @GetMapping("/departments")
    ResponseEntity<Response> departments () {
        List<Department> departments = adminService.getDepartments ();
        String message = String.format("Successfully fetched %d departments", departments.size());
        return ResponseEntity.ok(new Response(departments, message, 200));
    }
    @GetMapping("/departments/{uni_id}")
     ResponseEntity<Response> departments (@PathVariable (value="uni_id", required=true) Integer uni_id) {
        List<Department> departments = adminService.getDepartments (uni_id);
        String message = String.format("Successfully fetched %d of university with id: %d", departments.size(), uni_id);
        return ResponseEntity.ok(new Response (departments, message, 200));
    }
    @GetMapping("/department/{dep_id}")
    ResponseEntity<Response> department (@PathVariable(value="dep_id", required=true) Integer dep_id) {
        Department department = adminService.getDepartment(dep_id);
        String message = String.format("Successfully fetched university with id: %d", dep_id);
        return ResponseEntity.ok(new Response (department, message, 200));
    }
    @PostMapping("/department")
    ResponseEntity<Response> addDepartment (@RequestBody Department department) {
            adminService.addDepartment(department);
            String message = "Successfully added department";
            return ResponseEntity.ok(new Response (null, message, 200));
    }
    @PutMapping("/department")
    ResponseEntity<Response> updateDepartment (@RequestBody Department department) {
        adminService.updateDepartment (department);
        String message = String.format("Successfully updated department with id: %d", department.dep_id());
        return ResponseEntity.ok(new Response (null, message, 200));
    }
    @DeleteMapping("/department/{dep_id}")
    ResponseEntity<Response> deleteDepartment (@PathVariable(value="dep_id", required=true) Integer dep_id){
        adminService.deleteDepartment(dep_id);
        String message = String.format("Successfully deleted departments with id: %d", dep_id);
        return ResponseEntity.ok(new Response(null, message, 200));
    }


    /**
     *
     * Courses
     */
    @GetMapping("/courses/{dep_id}")
    ResponseEntity<Response> courses (@PathVariable(value="dep_id", required=true)Integer dep_id) {
        List<Course> courses = adminService.getCourses(dep_id);
        String message = String.format("Successfully fetched %d courses", courses.size());
        return ResponseEntity.ok(new Response(courses, message, 200));
    }
    @GetMapping("/course/{c_id}")
    ResponseEntity<Response> course (@PathVariable(value="c_id", required=true) int c_id) {
        Course course = adminService.getCourse(c_id);
        String message = String.format("Successfully fetched course with id: %d", c_id);
        return ResponseEntity.ok(new Response (course, message, 200));
    }
    @PostMapping("/course")
    ResponseEntity<Response> addCourse (@RequestBody Course course) {
        adminService.addCourse (course);
        String message = "Successfully added course";
        return ResponseEntity.ok(new Response (null, message, 200));    }
    @PutMapping("/course")
    ResponseEntity<Response> updateCourse (@RequestBody Course course) {
        adminService.updateCourse(course);
        String message = String.format("Successfully updated course with id: %d", course.c_id());
        return ResponseEntity.ok(new Response(null, message, 200));
    }
    @DeleteMapping("/course/{c_id}")
    ResponseEntity<Response> deleteCourse (@PathVariable(value="c_id", required=true) Integer c_id) {
        adminService.deleteCourse(c_id);
        String message = String.format("Successfully deleted course with id: %d", c_id);
        return ResponseEntity.ok(new Response (null, message, 200));
    }


    /**
     *
     * Notes
     */
    @GetMapping ("/notes/{c_id}")
    ResponseEntity<Response> getNotes (@PathVariable(value="c_id", required=true) Integer c_id) {
        List<Note> notes = adminService.getNotes(c_id);
        String message = String.format("Successfully fetched %d notes", notes.size());
        return ResponseEntity.ok(new Response(notes, message, 200));
    }
    @PostMapping("/note")
    ResponseEntity<Response> addNote (@RequestBody Note note) {
        adminService.addNote (note);
        String message = "Successfully added note";
        return ResponseEntity.ok(new Response (null, message, 200));    }
    @PutMapping("/note")
    ResponseEntity<Response> updateNote (@RequestBody Note note) {
        adminService.updateNote(note);
        String message = String.format("Successfully updated note with id: %d", note.note_id());
        return ResponseEntity.ok(new Response(null, message, 200));
    }
    @DeleteMapping("/note/{note_id}")
    ResponseEntity<Response> deleteNote (@PathVariable(value="note_id", required=true) Integer note_id) {
        adminService.deleteNote(note_id);
        String message = String.format("Successfully deleted note with id: %d", note_id);
        return ResponseEntity.ok(new Response (null, message, 200));
    }


    /**
     *
     * Files
     */
    @GetMapping("/files/{c_id}")
    ResponseEntity<Response> getFiles (@PathVariable (value="c_id", required=true) Integer c_id) {
        List<String> files = adminService.getFiles (c_id);
        String message = String.format("Successfully fetched %d notes", files.size());
        return ResponseEntity.ok(new Response(files, message, 200));
    }
    @PostMapping("/files/upload/{c_id}")
    ResponseEntity<Response> uploadFiles (@PathVariable(value="c_id", required=true) Integer c_id, @RequestParam("files") MultipartFile[] files) {
        List<String> filenames = adminService.uploadMany(c_id, files);
        String message = String.format ("Successfully added %d files", filenames.size());
        return ResponseEntity.ok(new Response (filenames, message, 200));
    }
    @DeleteMapping("/files/{c_id}/{filename}")
    ResponseEntity<Response> deleteFile (@PathVariable(value="c_id",required=true) Integer c_id, @PathVariable(value="filename", required=true) String filename) {
        adminService.deleteFile (c_id, filename);
        String message = String.format("Successfully deleted %s", filename);
        return ResponseEntity.ok(new Response(filename, message, 200));
    }
}