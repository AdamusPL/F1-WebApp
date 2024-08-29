package com.typerf1.typerf1.repository;

import com.typerf1.typerf1.model.Predictions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PredictionsRepository extends JpaRepository<Predictions, Integer> {

    @Query("SELECT p FROM Predictions p " +
            "WHERE p.grandPrix.id = :grandPrixId " +
            "AND p.session.id = :sessionId " +
            "AND p.participant.participantLoginData.username = :username")
    List<Predictions> checkPredictionExistence(@Param("grandPrixId") int grandPrixId, @Param("sessionId") int sessionId,
                                               @Param("username") String username);
}
