package gr.symfoititis.student.controllers;

import gr.symfoititis.student.records.Booking;
import gr.symfoititis.common.records.Response;
import gr.symfoititis.student.services.BookingsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookingsController {
    private final BookingsService bookingsService;
    public BookingsController(BookingsService bookingsService) {
        this.bookingsService = bookingsService;
    }
   @GetMapping("/bookings")
   ResponseEntity<Response> getBookings (@RequestHeader("X-Student-Id") String s_id) {
        List<Booking> bookings = bookingsService.getBookings(s_id);
        return ResponseEntity.ok(new Response(200, bookings));
   }
   @PostMapping("/booking")
   ResponseEntity<Response> addBooking (@RequestBody Booking booking, @RequestHeader("X-Student-Id") String s_id) {
       bookingsService.addBooking(new Booking(null, booking.av_id(), s_id, null, null));
       String message = "Booking has been added successfully.";
       return ResponseEntity.ok(new Response(200, message));
   }
   @PutMapping("/booking/cancel/{b_id}")
   ResponseEntity<Response> cancelBooking (@PathVariable(value="b_id", required = true) int b_id) {
       bookingsService.cancelBooking(b_id);
       String message = String.format("Booking %d has been successfully cancelled.", b_id);
       return ResponseEntity.ok(new Response(200, message));
   }
}
