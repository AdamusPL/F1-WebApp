package com.typerf1.typerf1.repository;

import com.typerf1.typerf1.dto.joker.JokersUsed;
import com.typerf1.typerf1.dto.season.SeasonScore;
import com.typerf1.typerf1.model.Points;
import com.typerf1.typerf1.dto.plot.ScoresDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultsRepository extends JpaRepository<Points, Integer> {

    @Query("SELECT new com.typerf1.typerf1.dto.season.SeasonScore(s.id, p.name, p.surname, SUM(pt.number)) " +
            "FROM Points pt " +
            "JOIN pt.participant p " +
            "JOIN pt.session ses " +
            "JOIN ses.grandPrix gp " +
            "JOIN gp.season s " +
            "WHERE s.year = :year " +
            "GROUP BY s.id, p.name, p.surname " +
            "ORDER BY s.id, SUM(pt.number) DESC")
    List<SeasonScore> getParticipantStandings(@Param("year") Integer year);

    @Query("SELECT new com.typerf1.typerf1.dto.joker.JokersUsed(p.name, p.surname, COUNT(j.id)) " +
            "FROM Joker j " +
            "JOIN j.participant p " +
            "JOIN j.grandPrix gp " +
            "JOIN gp.season s " +
            "WHERE s.year = 2024 " +
            "GROUP BY p.name, p.surname")
    List<JokersUsed> getParticipantJokers(@Param("year") Integer year);

    @Query("SELECT new com.typerf1.typerf1.dto.plot.ScoresDto(p.id, pa.name, pa.surname, s.name, gp.name, p.number) " +
            "FROM Points p " +
            "JOIN p.participant pa " +
            "JOIN p.session s " +
            "JOIN s.grandPrix gp")
    List<ScoresDto> getPlotData();
}
