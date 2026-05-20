package com.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dto.BookingRequestDTO;
import com.entity.Booking;
import com.entity.Ground;
import com.entity.User;
import com.enums.BookingStatus;
import com.repository.BookingRepo;
import com.repository.GroundRepo;
import com.repository.UserRepo;

@Service
public class BookingService {


	@Autowired
	private BookingRepo bookingRepo;

	@Autowired
	private GroundRepo groundRepo;

	@Autowired
	private UserRepo userRepo;


	public Booking createBooking(BookingRequestDTO dto) {

	    if (dto.getEndTime().isBefore(dto.getStartTime()) ||
	        dto.getEndTime().isEqual(dto.getStartTime())) {

	        throw new RuntimeException("Invalid time selection");
	    }

	    Ground ground = groundRepo.findById(dto.getGroundId())
	            .orElseThrow(() -> new RuntimeException("Ground not found"));

	    User user = userRepo.findById(dto.getUserId())
	            .orElseThrow(() -> new RuntimeException("User not found"));

	    boolean available = isGroundAvailable(
	            dto.getGroundId(),
	            dto.getStartTime(),
	            dto.getEndTime()
	    );

	    if (!available) {

	        throw new RuntimeException("Ground not available");
	    }

	    long minutes = Duration.between(
	            dto.getStartTime(),
	            dto.getEndTime()
	    ).toMinutes();

	    double hours = minutes / 60.0;

	    double totalAmount =
	            hours * ground.getPricePerHour();

	    Booking booking = new Booking();

	    booking.setGround(ground);

	    booking.setUser(user);

	    booking.setStartTime(dto.getStartTime());

	    booking.setEndTime(dto.getEndTime());

	    booking.setTotalAmount(totalAmount);

	    booking.setStatus(BookingStatus.BOOKED);

	    // PAYMENT DETAILS
	    booking.setPaid(true);

	    booking.setPaymentId(dto.getPaymentId());

	    booking.setPaymentStatus("PAID");

	    return bookingRepo.save(booking);
	}


	// ✅ Get user bookings
	public List<Booking> getUserBookings(Integer userId) {

	    List<Booking> bookings =
	        bookingRepo.findByUserIdOrderByStartTimeDesc(userId);

	    for (Booking b : bookings) {
	        if (b.getEndTime().isBefore(LocalDateTime.now())
	                && b.getStatus() == BookingStatus.BOOKED) {

	            b.setStatus(BookingStatus.COMPLETED);
	        }
	    }

	    return bookings;
	}
	// ✅ Get all bookings (Admin)	
	public List<Booking> getAllBookings() {
		return bookingRepo.findAll();
	}

	// ✅ Check availability
	public boolean isGroundAvailable(Integer groundId, LocalDateTime start, LocalDateTime end) {

	    List<Booking> conflicts = bookingRepo
	        .findByGroundIdAndStatusAndStartTimeLessThanAndEndTimeGreaterThan(
	            groundId,
	            BookingStatus.BOOKED,   // ✅ ONLY ACTIVE BOOKINGS
	            end,
	            start
	        );

	    return conflicts.isEmpty();
	}
	
	public List<Booking> getBookingsByGround(Integer groundId) {
	    return bookingRepo.findByGroundId(groundId);
	}
	
	public List<Booking> getActiveBookingsByGround(Integer groundId) {
	    return bookingRepo.findByGroundIdAndStatus(groundId, BookingStatus.BOOKED);
	}
	
	public Booking cancelBooking(Integer bookingId) {

	    Booking booking = bookingRepo.findById(bookingId)
	            .orElseThrow(() -> new RuntimeException("Booking not found"));

	    // ❌ already cancelled
	    if (booking.getStatus() == BookingStatus.CANCELLED) {

	        throw new RuntimeException("Booking already cancelled");
	    }

	    // ❌ completed booking
	    if (booking.getEndTime().isBefore(LocalDateTime.now())) {

	        throw new RuntimeException(
	            "Completed bookings cannot be cancelled"
	        );
	    }

	    booking.setStatus(BookingStatus.CANCELLED);

	    // refund request created
	    booking.setPaymentStatus("REFUND_PENDING");

	    return bookingRepo.save(booking);
	}
	
	public Booking refundBooking(Integer bookingId) {

	    Booking booking = bookingRepo.findById(bookingId)
	            .orElseThrow(() -> new RuntimeException("Booking not found"));

	    if (!"REFUND_PENDING".equals(booking.getPaymentStatus())) {

	        throw new RuntimeException("No refund pending");
	    }

	    booking.setPaymentStatus("REFUNDED");

	    booking.setRefundedAt(LocalDateTime.now());

	    return bookingRepo.save(booking);
	}

}
