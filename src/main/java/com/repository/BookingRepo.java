package com.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.Booking;
import com.enums.BookingStatus;

public interface BookingRepo extends JpaRepository<Booking,Integer>{
	 // Check overlapping bookings
	List<Booking> findByGroundIdAndStatusAndStartTimeLessThanAndEndTimeGreaterThan(
		    Integer groundId,
		    BookingStatus status,
		    LocalDateTime endTime,
		    LocalDateTime startTime
		);
    
    // 🔹 All bookings for ground
   List<Booking> findByGroundId(Integer groundId);

	
	List<Booking> findByGroundIdAndStatus(Integer groundId, BookingStatus status);
    List<Booking> findByUserId(Integer userId);
    
    List<Booking> findByUserIdOrderByStartTimeDesc(Integer userId);
}
