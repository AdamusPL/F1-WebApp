package com.typerf1.typerf1.repository;

import com.typerf1.typerf1.model.Points;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointsRepository extends JpaRepository<Points, Integer> {
}
