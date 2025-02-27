package gr.symfoititis.tutoring.controllers;

import gr.symfoititis.common.entities.Teacher;
import gr.symfoititis.common.exceptions.BadRequestException;
import gr.symfoititis.common.records.Response;
import gr.symfoititis.tutoring.records.AvailabilitySlot;
import gr.symfoititis.tutoring.services.AvailabilityService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static gr.symfoititis.common.utils.RoleValidation.*;

@Validated
@RestController
public class AvailabilityController {
    private final AvailabilityService availabilityService;
    public AvailabilityController(AvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    @GetMapping("/availability/{c_id}/teacher/{t_id}")
    ResponseEntity<Response> getTeacherAvailabilitySlots(
            @NotNull
            @NotBlank
            @RequestHeader("X-Role")
            String role,
            @NotNull
            @NotBlank
            @RequestHeader("X-Department-Id")
            String dep_id,
            @Positive
            @PathVariable(value = "c_id", required = true)
            int c_id,
            @NotNull
            @NotBlank
            @PathVariable(value = "t_id", required = true)
            String t_id
    ) {
        isStudentOrTeacher(role);
        try {
            @Positive
            int departmentId = Integer.parseInt(dep_id);
            List<@Valid AvailabilitySlot> availabilitySlots = availabilityService.getAvailabilitySlots(role, departmentId, c_id, t_id);
            return ResponseEntity.ok(new Response(200, availabilitySlots));
        } catch (NumberFormatException ex) {
            throw new BadRequestException("Department id cannot be parsed to integer");
        }
    }

    @GetMapping("/availability/{c_id}/teachers")
    ResponseEntity<Response> getAvailableTeachers (
            @NotNull
            @NotBlank
            @RequestHeader("X-Role")
            String role,
            @NotNull
            @NotBlank
            @RequestHeader("X-Department-Id")
            String dep_id,
            @Positive
            @PathVariable(value = "c_id", required = true)
            int c_id
    ) {
        isStudent(role);
        try {
            @Positive
            int departmentId = Integer.parseInt(dep_id);
            List<@Valid Teacher> teachers= availabilityService.getAvailableTeachers(departmentId, c_id);
            return ResponseEntity.ok(new Response(200, teachers));
        } catch (NumberFormatException ex) {
            throw new BadRequestException("Department id cannot be parsed to integer");
        }
    }

    @GetMapping("availability/{dep_id}/courseIds")
    ResponseEntity<Response> getAvailableTutoringCourseIds (
            @NotNull
            @NotBlank
            @RequestHeader("X-Role")
            String role,
            @NotNull
            @NotBlank
            @RequestHeader("X-Department-Id")
            String departmentId,
            @Positive
            @PathVariable(value = "dep_id", required = true)
            int dep_id
    ) {
        isStudent(role);
        try {
            if (dep_id != Integer.parseInt(departmentId)) {
                throw new BadRequestException("Invalid Department id");
            }
        } catch (NumberFormatException ex) {
            throw new BadRequestException("Department id could not be parsed to integer");
        }
        List<@NotNull @Positive Integer> courseIds = availabilityService.getAvailableTutoringCourseIds(dep_id);
        return ResponseEntity.ok(new Response(200, courseIds));
    }

    @PostMapping("/availability")
    ResponseEntity<Response> addAvailabilitySlots (
            @NotNull
            @NotBlank
            @RequestHeader("X-Role")
            String role,
            @NotNull
            @NotBlank
            @RequestHeader("X-User-Id")
            String t_id,
            @NotNull
            @NotBlank
            @RequestHeader("X-Department-Id")
            String dep_id,
            @NotNull
            @Size(min=1, max=15)
            @RequestBody
            List<@Valid AvailabilitySlot> availabilitySlots
    ) {
        isTeacher(role);
        try {
            @Positive
            int departmentId = Integer.parseInt(dep_id);
            availabilityService.addAvailabilitySlots(availabilitySlots, departmentId, t_id);
            String message = String.format("Successfully added %d availability slots", availabilitySlots.size());
            return ResponseEntity.ok(new Response(200, message));
        } catch (NumberFormatException ex) {
            throw new BadRequestException("Department id cannot be parsed to integer");
        }
    }

    @PutMapping("/availability")
    ResponseEntity<Response> updateAvailabilitySlots (
            @NotNull
            @NotBlank
            @RequestHeader("X-Role")
            String role,
            @NotNull
            @NotBlank
            @RequestHeader("X-User-Id")
            String t_id,
            @NotNull
            @NotBlank
            @RequestHeader("X-Department-Id")
            String dep_id,
            @NotNull
            @Size(min=1, max=15)
            @RequestBody
            List<@Valid AvailabilitySlot> availabilitySlots
    ) {
        isTeacher(role);
        try {
            @Positive
            int departmentId = Integer.parseInt(dep_id);
            availabilityService.updateAvailabilitySlots(availabilitySlots, departmentId, t_id);
            String message = String.format("Successfully updated %d availability slots", availabilitySlots.size());
            return ResponseEntity.ok(new Response(200, message));
        } catch (NumberFormatException ex) {
            throw new BadRequestException("Department id cannot be parsed to integer");
        }
    }

    @DeleteMapping("/availability")
    ResponseEntity<Response> deleteAvailabilitySlots (
            @NotNull
            @NotBlank
            @RequestHeader("X-Role")
            String role,
            @NotNull
            @NotBlank
            @RequestHeader("X-User-Id")
            String t_id,
            @NotNull
            @NotBlank
            @RequestHeader("X-Department-Id")
            String dep_id,
            @NotNull
            @Size(min=1, max=15)
            @RequestBody
            List<@NotNull @Positive Integer> availabilitySlotIds
    ) {
        isTeacher(role);
        try {
            @Positive
            int departmentId = Integer.parseInt(dep_id);
            availabilityService.deleteAvailabilitySlots(availabilitySlotIds, departmentId, t_id);
            String message = String.format("Successfully deleted %d availability slots", availabilitySlotIds.size());
            return ResponseEntity.ok(new Response(200, message));
        } catch (NumberFormatException ex) {
            throw new BadRequestException("Department id cannot be parsed to integer");
        }
    }
}
