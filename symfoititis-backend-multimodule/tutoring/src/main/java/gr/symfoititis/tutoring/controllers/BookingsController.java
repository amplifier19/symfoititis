package gr.symfoititis.tutoring.controllers;

import gr.symfoititis.common.exceptions.BadRequestException;
import gr.symfoititis.common.entities.Booking;
import gr.symfoititis.common.records.Response;
import gr.symfoititis.tutoring.services.BookingsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static gr.symfoititis.common.utils.RoleValidation.*;

@RestController
public class BookingsController {
    private final BookingsService bookingsService;
    public BookingsController(BookingsService bookingsService) {
        this.bookingsService = bookingsService;
    }

   @GetMapping("/bookings")
   ResponseEntity<Response> getBookings (
           @RequestHeader("X-User-Id") String id,
           @RequestHeader("X-Role") String role
   ) {
        isStudentOrTeacher(role);
        List<Booking> bookings = bookingsService.getBookings(id, role);
        return ResponseEntity.ok(new Response(200, bookings));
   }

   @PostMapping("/booking")
   ResponseEntity<Response> addBooking (
           @RequestBody Booking booking,
           @RequestHeader("X-User-Id") String s_id,
           @RequestHeader("X-Role") String role
   ) {
       if (!s_id.equals(booking.getStudentId())) {
           throw new BadRequestException("Invalid student id");
       }
       isStudent(role);
       bookingsService.addBooking(booking);
       String message = "Booking has been added successfully.";
       return ResponseEntity.ok(new Response(200, message));
   }

   @PutMapping("/cancel/booking/{b_id}")
   ResponseEntity<Response> cancelBooking (
           @PathVariable(value="b_id", required = true) int b_id,
           @RequestHeader("X-Role") String role,
           @RequestHeader("X-User-Id") String id
   ) {
       bookingsService.cancelBooking(b_id, id, role);
       String message = String.format("Booking %d has been successfully cancelled.", b_id);
       return ResponseEntity.ok(new Response(200, message));
   }
}