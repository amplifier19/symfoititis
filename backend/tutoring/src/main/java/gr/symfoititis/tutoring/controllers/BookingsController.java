package gr.symfoititis.tutoring.controllers;

import gr.symfoititis.common.entities.Booking;
import gr.symfoititis.common.records.Response;
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
    public BookingsController(BookingsService bookingsService) {
        this.bookingsService = bookingsService;
    }

   @GetMapping("/bookings")
   ResponseEntity<Response> getBookings (
           @RequestHeader("X-User-Id")
           @NotBlank(message = "User id cannot be blank")
           String id,
           @RequestHeader("X-Role")
           @NotBlank(message = "User id cannot be blank")
           String role
   ) {
        isStudentOrTeacher(role);
        List<Booking> bookings = bookingsService.getBookings(id, role);
        return ResponseEntity.ok(new Response(200, bookings));
   }

   @PostMapping("/booking")
   ResponseEntity<Response> addBooking (
           @RequestBody @Valid Booking booking,
           @RequestHeader("X-User-Id")
           @NotNull(message = "User id cannot be null")
           @NotBlank(message = "User id cannot be blank")
           String s_id,
           @RequestHeader("X-Role")
           @NotNull(message = "Role cannot be null")
           @NotBlank(message = "Role cannot be blank")
           String role
   ) {
       isStudent(role);
       bookingsService.addBooking(booking);
       String message = "Booking has been added successfully.";
       return ResponseEntity.ok(new Response(200, message));
   }

   @PutMapping("/cancel/booking/{b_id}")
   ResponseEntity<Response> cancelBooking (
           @PathVariable(value="b_id", required = true)
           @Positive(message = "Booking id must be positive")
           int b_id,
           @RequestHeader("X-User-Id")
           @NotNull(message = "User id cannot be null")
           @NotBlank(message = "User id cannot be blank")
           String id,
           @RequestHeader("X-Role")
           @NotNull(message = "Role cannot be null")
           @NotBlank(message = "Role cannot be blank")
           String role
   ) {
       bookingsService.cancelBooking(b_id, id, role);
       String message = String.format("Booking %d has been successfully cancelled.", b_id);
       return ResponseEntity.ok(new Response(200, message));
   }

   @GetMapping("/user/{s_id}")
    ResponseEntity<Response> retrieveStudent(
            @PathVariable(value="s_id")
            @NotNull(message = "Student id cannot be null")
            @NotBlank(message = "Student id cannot be blank")
            String s_id) {
        return ResponseEntity.ok(new Response(200, bookingsService.retrieveStudent(s_id)));
   }
}