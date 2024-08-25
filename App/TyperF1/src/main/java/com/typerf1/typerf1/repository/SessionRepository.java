package com.typerf1.typerf1.repository;

import com.typerf1.typerf1.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Integer> {

    @Query("SELECT new com.typerf1.typerf1.model.Session(s.id, s.name) " +
            "FROM Session s " +
            "JOIN s.grandPrix gp " +
            "WHERE gp.id = :grandPrixId")
    List<Session> getSessionsFromThatGrandPrix(@Param("grandPrixId") int grandPrixId);
}
