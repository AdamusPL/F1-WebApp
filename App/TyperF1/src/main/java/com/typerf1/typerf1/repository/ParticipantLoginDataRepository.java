package com.typerf1.typerf1.repository;

import com.typerf1.typerf1.model.ParticipantLoginData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantLoginDataRepository extends JpaRepository<ParticipantLoginData, Integer> {
}
