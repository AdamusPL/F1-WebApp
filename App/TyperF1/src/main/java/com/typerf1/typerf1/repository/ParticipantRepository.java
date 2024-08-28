package com.typerf1.typerf1.repository;

import com.typerf1.typerf1.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Integer> {

    @Query("SELECT new com.typerf1.typerf1.model.Participant(p.id, p.name, p.surname, p.description, p.profilePicture) " +
            "FROM Participant p " +
            "JOIN p.participantLoginData pld " +
            "WHERE pld.username = :username")
    List<Participant> getParticipantByParticipantLoginDataUsername(@Param("username") String username);
}
