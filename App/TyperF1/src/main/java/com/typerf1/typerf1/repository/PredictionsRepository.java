package com.typerf1.typerf1.repository;

import com.typerf1.typerf1.model.Predictions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PredictionsRepository extends JpaRepository<Predictions, Integer> {
}
