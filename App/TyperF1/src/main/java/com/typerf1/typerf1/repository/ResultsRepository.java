package com.typerf1.typerf1.repository;

import com.typerf1.typerf1.dto.JokersUsed;
import com.typerf1.typerf1.dto.SeasonScore;
import com.typerf1.typerf1.model.Points;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultsRepository extends JpaRepository<Points, Integer> {

    @Query("SELECT new com.typerf1.typerf1.dto.SeasonScore(s.id, p.name, p.surname, SUM(pt.number)) " +
            "FROM Points pt " +
            "JOIN pt.participant p " +
            "JOIN pt.session ses " +
            "JOIN ses.grandPrix gp " +
            "JOIN gp.season s " +
            "WHERE s.year = 2024 " +
            "GROUP BY s.id, p.name, p.surname " +
            "ORDER BY s.id, SUM(pt.number) DESC")
    List<SeasonScore> getParticipantStandings(@Param("year") Integer year);

    @Query("SELECT new com.typerf1.typerf1.dto.JokersUsed(p.name, p.surname, COUNT(j.id)) " +
            "FROM Joker j " +
            "JOIN j.participant p " +
            "JOIN j.grandPrix gp " +
            "JOIN gp.season s " +
            "WHERE s.year = 2024 " +
            "GROUP BY p.name, p.surname")
    List<JokersUsed> getParticipantJokers(@Param("year") Integer year);
}
