package gr.symfoititis.student.controller;

import gr.symfoititis.common.records.*;
import gr.symfoititis.common.services.UniversitiesService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/student")
public class UniversityController {
    private final UniversitiesService universitiesService;
    public UniversityController(UniversitiesService universitiesService) {
        this.universitiesService = universitiesService;
    }

    /**
     *
     * Universities
     */
    @GetMapping("/universities")
    ResponseEntity<Response> universities () {
        List<University> universities = universitiesService.getUniversities();
        return ResponseEntity.ok(new Response(200, universities));
    }

    @GetMapping("/university")
    public ResponseEntity<Response> university (@RequestHeader("X-University-Id") String id) {
        int uni_id = Integer.parseInt(id);
        University university = universitiesService.getUniversity(uni_id);
        return ResponseEntity.ok(new Response(200, university));
    }
}
