package gr.symfoititis.admin.controllers;

import gr.symfoititis.admin.services.AdminUniversitiesService;
import gr.symfoititis.common.records.Response;
import gr.symfoititis.common.records.University;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/admin")
public class UniversityController {
    private final AdminUniversitiesService adminUniversitiesService;
    private final gr.symfoititis.common.services.UniversitiesService commonUniversityService;

    public UniversityController(AdminUniversitiesService adminUniversitiesService, gr.symfoititis.common.services.UniversitiesService commonUniversityService) {
        this.adminUniversitiesService = adminUniversitiesService;
        this.commonUniversityService = commonUniversityService;
    }

    /**
     *
     * Universities
     */
    @GetMapping("/universities")
    ResponseEntity<Response> universities () {
        List<University> universities = commonUniversityService.getUniversities();
        return ResponseEntity.ok(new Response(200, adminUniversitiesService));
    }
    @GetMapping("/university/{uni_id}")
    ResponseEntity<Response> university (@PathVariable(value="uni_id", required=false) Integer uni_id) {
        University university = commonUniversityService.getUniversity(uni_id);
        return ResponseEntity.ok(new Response(200, university));
    }
    @PostMapping("/university")
    ResponseEntity<Response> addUniversity (@RequestBody University university) {
        adminUniversitiesService.addUniversity(university);
        String message = "Successfully added university";
        return ResponseEntity.ok(new Response(200, message));
    }
    @PutMapping ("/university")
    ResponseEntity<Response> updateUniversity (@RequestBody University university) {
        adminUniversitiesService.updateUniversity(university);
        String message = String.format("Successfully updated university with id: %d", university.uni_id());
        return ResponseEntity.ok(new Response(200, message));
    }
    @DeleteMapping("/university/{uni_id}")
    ResponseEntity<Response> deleteUniversity (@PathVariable(value="uni_id", required=true) Integer uni_id) {
        adminUniversitiesService.deleteUniversity(uni_id);
        String message = String.format("Successfully deleted university with id: %d", uni_id);
        return ResponseEntity.ok(new Response(200, message));
    }

}
