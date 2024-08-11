package com.typerf1.typerf1.repository;

import com.typerf1.typerf1.dto.FullName;
import com.typerf1.typerf1.model.ParticipantLoginData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantLoginDataRepository extends JpaRepository<ParticipantLoginData, Integer> {

    @Query(
            "SELECT new com.typerf1.typerf1.dto.FullName(p.name, p.surname) " +
                    "FROM ParticipantLoginData pld " +
                    "JOIN pld.participant p " +
                    "WHERE pld.username = :username "
    )
    List<FullName> getFullName(@Param("username") String username);
}
