package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.Ground;

public interface GroundRepo extends JpaRepository<Ground,Integer> {
    List<Ground> findByActiveTrue();
}
