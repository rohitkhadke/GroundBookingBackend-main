package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dto.GroundDTO;
import com.entity.Ground;
import com.service.GroundService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class GroundController {

    @Autowired
    GroundService gserv;

    // Admin - Add ground
    @PostMapping("/addGround")
    public ResponseEntity<?> addGround(
            @RequestPart("ground") GroundDTO dto,
            @RequestPart("images") List<MultipartFile> images) {
        try {
            Ground g = gserv.addGround(dto, images);
            return ResponseEntity.ok(g);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error adding ground");
        }
    }

    // User - View all grounds
    @GetMapping("/Allgrounds")
    public List<Ground> getAllGrounds() {
        return gserv.getAllGrounds();
    }

    // User - View details
    @GetMapping("/ground/{id}")
    public ResponseEntity<?> getGroundById(@PathVariable Integer id) {

        Ground g = gserv.getGroundById(id);

        if (g == null) {
            return ResponseEntity
                    .status(404)
                    .body("No such ground exists");
        }

        return ResponseEntity.ok(g);
    }

    // Admin - Update
    @PutMapping("/updateground/{id}")
    public ResponseEntity<?> updateGround(
            @PathVariable Integer id,
            @RequestPart("ground") GroundDTO dto,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) {

        try {
            Ground updated = gserv.updateGroundWithImages(id, dto, images);

            if (updated == null) {
                return ResponseEntity.status(404).body("Ground not found");
            }

            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Update failed");
        }
    }
    
    
    // Admin - Delete
    @DeleteMapping("/deleteground/{id}")
    public ResponseEntity<?> deleteGround(@PathVariable Integer id) {

        boolean deleted = gserv.deleteGround(id);

        if (!deleted) {
            return ResponseEntity
                    .status(404)
                    .body("Ground not found");
        }

        return ResponseEntity.ok("Ground deleted successfully");
    }
    
    @GetMapping("/admin/grounds")
    public List<Ground> getAllGroundsAdmin() {
        return gserv.getAllGroundsAdmin();
    }
    
    @PutMapping("/ground/active/{id}")
    public ResponseEntity<?> makeGroundActive(
            @PathVariable Integer id) {

        Ground ground = gserv.makeGroundActive(id);

        if (ground == null) {
            return ResponseEntity
                    .status(404)
                    .body("Ground not found");
        }

        return ResponseEntity.ok(ground);
    }
    
    @PutMapping("/ground/inactive/{id}")
    public ResponseEntity<?> makeGroundInactive(
            @PathVariable Integer id) {

        boolean updated = gserv.makeInactive(id);

        if (!updated) {
            return ResponseEntity
                    .status(404)
                    .body("Ground not found");
        }

        return ResponseEntity.ok("Ground marked inactive");
    }
}