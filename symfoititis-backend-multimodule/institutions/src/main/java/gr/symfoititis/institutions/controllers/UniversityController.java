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
import gr.symfoititis.institutions.records.University;
import gr.symfoititis.institutions.services.UniversitiesService;

import java.util.List;

import static gr.symfoititis.common.utils.RoleValidation.isAdmin;
import static gr.symfoititis.common.utils.RoleValidation.isStudentOrTeacher;

@Validated
@RestController
public class UniversityController {
    private final UniversitiesService universitiesService;

    public UniversityController(UniversitiesService universitiesService) {
        this.universitiesService = universitiesService;
    }

    @GetMapping("/universities")
    ResponseEntity<Response> universities () {
        List<University> universities = universitiesService.getUniversities();
        return ResponseEntity.ok(new Response(200, universities));
    }

    @GetMapping("/university")
    public ResponseEntity<Response> university (
            @RequestHeader("X-Role")
            @NotNull(message = "Role cannot be null")
            @NotBlank(message = "Role cannot be blank")
            String role,
            @RequestHeader("X-University-Id")
            @NotNull(message = "University id cannot be null")
            @NotBlank(message = "University id cannot be blank")
            String id
    ) {
        isStudentOrTeacher(role);
        try {
            int uni_id = Integer.parseInt(id);
            University university = universitiesService.getUniversity(uni_id);
            return ResponseEntity.ok(new Response(200, university));
        } catch (NumberFormatException ex) {
            throw new BadRequestException("University id could not be parsed to integer");
        }
    }

    @GetMapping("/university/{uni_id}")
    ResponseEntity<Response> university (
            @RequestHeader("X-Role")
            @NotNull(message = "Role cannot be null")
            @NotBlank(message = "Role cannot be blank")
            String role,
            @PathVariable(value="uni_id", required=false)
            @Positive(message = "University id must be positive")
            int uni_id
    ) {
        isAdmin(role);
        University university = universitiesService.getUniversity(uni_id);
        return ResponseEntity.ok(new Response(200, university));
    }

    @PostMapping("/university")
    ResponseEntity<Response> addUniversity (
            @RequestHeader("X-Role")
            @NotNull(message = "Role cannot be null")
            @NotBlank(message = "Role cannot be blank")
            String role,
            @RequestBody @Valid University university
    ) {
        isAdmin(role);
        universitiesService.addUniversity(university);
        String message = String.format("Successfully added university %s", university.uni_alt_name());
        return ResponseEntity.ok(new Response(200, message));
    }

    @PutMapping ("/university")
    ResponseEntity<Response> updateUniversity (
            @RequestHeader("X-Role")
            @NotNull(message = "Role cannot be null")
            @NotBlank(message = "Role cannot be blank")
            String role,
            @RequestBody @Valid University university
    ) {
        isAdmin(role);
        universitiesService.updateUniversity(university);
        String message = String.format("Successfully updated university with id: %d", university.uni_id());
        return ResponseEntity.ok(new Response(200, message));
    }

    @DeleteMapping("/university/{uni_id}")
    ResponseEntity<Response> deleteUniversity (
            @RequestHeader("X-Role")
            @NotNull(message = "Role cannot be null")
            @NotBlank(message = "Role cannot be blank")
            String role,
            @PathVariable(value="uni_id", required=true)
            @Positive(message = "University id must be positive")
            int uni_id
    ) {
        isAdmin(role);
        universitiesService.deleteUniversity(uni_id);
        String message = String.format("Successfully deleted university with id: %d", uni_id);
        return ResponseEntity.ok(new Response(200, message));
    }
}
