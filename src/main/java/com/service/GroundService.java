package com.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dto.GroundDTO;
import com.entity.Booking;
import com.entity.Ground;
import com.repository.BookingRepo;
import com.repository.GroundRepo;

@Service
public class GroundService {

    @Autowired
    GroundRepo grepo;
    
    @Autowired
    ImageService imageService;
    
    @Autowired
    private BookingRepo bookingRepo;

    // Add ground (Admin)
    public Ground addGround(GroundDTO dto, List<MultipartFile> images) throws IOException {

        List<String> imageNames = imageService.saveImages(images);

        Ground g = new Ground();
        g.setName(dto.getName());
        g.setLocation(dto.getLocation());
        g.setType(dto.getType());
        g.setPricePerHour(dto.getPricePerHour());
        g.setPricePerDay(dto.getPricePerDay());
        g.setDescription(dto.getDescription());
        g.setImages(imageNames);

        g.setActive(true);
        
        return grepo.save(g);
    }

    // View all grounds (User)
    public List<Ground> getAllGrounds() {
    	  return grepo.findByActiveTrue();
    }

    // Get ground by id
    public Ground getGroundById(Integer id) {
        return grepo.findById(id).orElse(null); 
        
    }

    // Update ground
    public Ground updateGroundWithImages(Integer id, GroundDTO dto, List<MultipartFile> images) throws IOException {

        Ground existing = grepo.findById(id).orElse(null);

        if (existing == null) return null;

        // update text fields
        if (dto.getName() != null) existing.setName(dto.getName());
        if (dto.getLocation() != null) existing.setLocation(dto.getLocation());
        if (dto.getType() != null) existing.setType(dto.getType());
        if (dto.getDescription() != null) existing.setDescription(dto.getDescription());

        if (dto.getPricePerHour() > 0) existing.setPricePerHour(dto.getPricePerHour());
        if (dto.getPricePerDay() > 0) existing.setPricePerDay(dto.getPricePerDay());

        // update images (if new ones provided)
        if (images != null && !images.isEmpty()) {
            List<String> imageNames = imageService.saveImages(images);
            existing.setImages(imageNames); // overwrite old images
        }

        return grepo.save(existing);
    }

    // Delete ground
    public boolean deleteGround(Integer id) {

        Ground existing = grepo.findById(id).orElse(null);

        if (existing == null) {
            return false;
        }

        // DELETE BOOKINGS FIRST
        List<Booking> bookings =
                bookingRepo.findByGroundId(id);

        bookingRepo.deleteAll(bookings);

        // DELETE GROUND
        grepo.delete(existing);

        return true;
    }
    
    public List<Ground> getAllGroundsAdmin() {
        return grepo.findAll();
    }
    
    public Ground makeGroundActive(Integer id) {

        Ground ground = grepo.findById(id).orElse(null);

        if (ground == null) {
            return null;
        }

        ground.setActive(true);

        return grepo.save(ground);
    }
    
    public boolean makeInactive(Integer id) {

        Ground ground = grepo.findById(id).orElse(null);

        if (ground == null) {
            return false;
        }

        ground.setActive(false);

        grepo.save(ground);

        return true;
    }
}