package gr.symfoititis.tutoring.controllers;

import gr.symfoititis.common.entities.Booking;
import gr.symfoititis.common.entities.Student;
import gr.symfoititis.common.exceptions.BadRequestException;
import gr.symfoititis.common.records.Response;
import gr.symfoititis.student.services.StudentService;
import gr.symfoititis.tutoring.services.BookingsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static gr.symfoititis.common.utils.RoleValidation.*;

@Validated
@RestController
public class BookingsController {
    private final StudentService studentService;
    private final BookingsService bookingsService;
    public BookingsController(BookingsService bookingsService, StudentService studentService) {
        this.bookingsService = bookingsService;
        this.studentService = studentService;
    }

    @GetMapping("/bookings")
    ResponseEntity<Response> getBookings (
            @NotNull
            @NotBlank
            @RequestHeader("X-User-Id")
            String id,
            @NotNull
            @NotBlank
            @RequestHeader("X-Role")
            String role
    ) {
        isStudentOrTeacher(role);
        List<@Valid Booking> bookings = bookingsService.getBookings(id, role);
        return ResponseEntity.ok(new Response(200, bookings));
    }

    // TODO: On booking update tokens for the user (RabitMQ - Financial Service)
    @PostMapping("/booking")
    ResponseEntity<Response> addBookings (
            @NotNull
            @NotBlank
            @RequestHeader("X-Department-Id")
            String dep_id,
            @NotNull
            @NotBlank
            @RequestHeader("X-User-Id")
            String s_id,
            @NotNull
            @NotBlank
            @RequestHeader("X-Role")
            String role,
            @NotEmpty
            @RequestBody
            List <@NotNull @Positive Integer> availabilityIds
    ) {
        isStudent(role);
        try {
            @Positive
            int departmentId = Integer.parseInt(dep_id);
            bookingsService.addBookings(availabilityIds, departmentId, s_id);
            String message = String.format("Successfully added %d bookings", availabilityIds.size());
            return ResponseEntity.ok(new Response(200, message));
        } catch (NumberFormatException ex) {
            throw new BadRequestException("Department id cannot be parsed to integer");
        }
    }

    // TODO: Cancel only three times lifetime and 3 hours before the booking - On cancel refund token (RabitMQ - Financial Service)
    @PutMapping("/cancel/booking/{b_id}")
    ResponseEntity<Response> cancelBooking (
            @Positive
            @PathVariable(value="b_id", required = true)
            int b_id,
            @NotNull
            @NotBlank
            @RequestHeader("X-User-Id")
            String id,
            @NotNull
            @NotBlank
            @RequestHeader("X-Role")
            String role
    ) {
        isStudentOrTeacher(role);
        bookingsService.cancelBooking(b_id, id, role);
        String message = String.format("Booking %d has been successfully cancelled.", b_id);
        return ResponseEntity.ok(new Response(200, message));
    }
}
