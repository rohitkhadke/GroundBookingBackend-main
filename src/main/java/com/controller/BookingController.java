package com.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dto.BookingRequestDTO;
import com.entity.Booking;
import com.service.BookingService;

@RestController
@RequestMapping("/booking")
@CrossOrigin
public class BookingController {

	@Autowired
	private BookingService bookingService;

	// ✅ Create Booking
	@PostMapping("/create")
	public Booking createBooking(@RequestBody BookingRequestDTO dto) {
		return bookingService.createBooking(dto);
	}

	// ✅ User booking history
	@GetMapping("/user/{userId}")
	public List<Booking> getUserBookings(@PathVariable Integer userId) {
		return bookingService.getUserBookings(userId);
	}
	@GetMapping("/ground/{groundId}")
	public List<Booking> getBookingsByGround(@PathVariable Integer groundId) {
		return bookingService.getBookingsByGround(groundId);
	}
	// ✅ Admin - all bookings
	@GetMapping("/all")
	public List<Booking> getAllBookings() {
		return bookingService.getAllBookings();
	}

	// ✅ Ground availability check
	@GetMapping("/check")
	public String checkAvailability(
			@RequestParam Integer groundId,
			@RequestParam String startTime,
			@RequestParam String endTime
			) {

		LocalDateTime start = LocalDateTime.parse(startTime);
		LocalDateTime end = LocalDateTime.parse(endTime);

		boolean available = bookingService.isGroundAvailable(groundId, start, end);

		return available ? "AVAILABLE" : "NOT AVAILABLE";
	}

	@PutMapping("/cancel/{bookingId}")
	public Booking cancelBooking(@PathVariable Integer bookingId) {
		return bookingService.cancelBooking(bookingId);
	}
	
	@GetMapping("/ground/active/{groundId}")
	public List<Booking> getActiveBookings(@PathVariable Integer groundId) {
	    return bookingService.getActiveBookingsByGround(groundId);
	}
	
	@PutMapping("/refund/{bookingId}")
	public Booking refundBooking(
	        @PathVariable Integer bookingId) {

	    return bookingService.refundBooking(bookingId);
	}
	
}