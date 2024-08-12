package com.typerf1.typerf1.repository;

import com.typerf1.typerf1.dto.RegisterData;
import com.typerf1.typerf1.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegisterRepository extends JpaRepository<Participant, Integer> {
    @Query("SELECT new com.typerf1.typerf1.dto.RegisterData(p.name, p.surname, pld.username, e.email) " +
            "FROM Participant p " +
            "JOIN p.participantLoginData pld " +
            "JOIN p.email e")
    List<RegisterData> getAllUserData();
}
