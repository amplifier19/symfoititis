package gr.symfoititis.tutoring.controllers;

import gr.symfoititis.common.entities.Booking;
import gr.symfoititis.common.exceptions.BadRequestException;
import gr.symfoititis.common.records.Response;
import gr.symfoititis.tutoring.services.AvailabilityService;
import gr.symfoititis.tutoring.services.BookingsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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
    private final BookingsService bookingsService;
    private final AvailabilityService availabilityService;
    public BookingsController(BookingsService bookingsService, AvailabilityService availabilityService) {
        this.bookingsService = bookingsService;
        this.availabilityService = availabilityService;
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

    @PostMapping("/booking")
    ResponseEntity<Response> addBooking (
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
            @Valid
            @RequestBody
            Booking booking
    ) {
        isStudent(role);
        try {
            @Positive
            int departmentId = Integer.parseInt(dep_id);
            if (!s_id.equals(booking.getS_id())) {
                throw new BadRequestException("Invalid student id");
            }
            availabilityService.getAvailabilitySlot(booking.getAv_id(), departmentId);
            bookingsService.addBooking(booking);
            String message = "Booking has been added successfully.";
            return ResponseEntity.ok(new Response(200, message));
        } catch (NumberFormatException ex) {
            throw new BadRequestException("Department id cannot be parsed to integer");
        }
    }

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
        bookingsService.cancelBooking(b_id, id, role);
        String message = String.format("Booking %d has been successfully cancelled.", b_id);
        return ResponseEntity.ok(new Response(200, message));
    }
}
