package com.typerf1.typerf1.repository;

import com.typerf1.typerf1.dto.GrandPrixScore;
import com.typerf1.typerf1.dto.JokersUsage;
import com.typerf1.typerf1.dto.Score;
import com.typerf1.typerf1.dto.UsedJokersGP;
import com.typerf1.typerf1.model.Points;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StandingsRepository extends JpaRepository<Points, Integer> {

    @Query("SELECT new com.typerf1.typerf1.dto.Score(s.year, gp.name, ses.name, p.name, p.surname, pt.number) " +
            "FROM Points pt " +
            "JOIN pt.participant p " +
            "JOIN pt.session ses " +
            "JOIN ses.grandPrix gp " +
            "JOIN gp.season s " +
            "WHERE s.year = :year " +
            "ORDER BY ses.id, pt.number DESC")
    List<Score> findSeasonScores(@Param("year") Integer year);

    @Query("SELECT new com.typerf1.typerf1.dto.JokersUsage(gp.name, p.name, p.surname) " +
            "FROM Joker j " +
            "JOIN j.grandPrix gp " +
            "JOIN j.participant p " +
            "JOIN gp.season s " +
            "WHERE s.year = :year " +
            "GROUP BY gp.name, p.name, p.surname")
    List<JokersUsage> findJokerUsageInSeasonScores(@Param("year") Integer year);

    @Query("SELECT new com.typerf1.typerf1.dto.GrandPrixScore(gp.name, p.name, p.surname, SUM(pt.number)) " +
            "FROM Points pt " +
            "JOIN pt.participant p " +
            "JOIN pt.session ses " +
            "JOIN ses.grandPrix gp " +
            "JOIN gp.season s " +
            "WHERE s.year = :year AND gp.name LIKE :grandPrixName " +
            "GROUP BY gp.name, p.name, p.surname " +
            "ORDER BY gp.name, SUM(pt.number) DESC")
    List<GrandPrixScore> findGrandPrixSummaryScores(@Param("year") Integer year, @Param("grandPrixName") String grandPrixName);

    @Query("SELECT new com.typerf1.typerf1.dto.UsedJokersGP(gp.name, p.name, p.surname, SUM(j.id)) " +
            "FROM Joker j " +
            "JOIN j.participant p " +
            "JOIN j.grandPrix gp " +
            "JOIN gp.season s " +
            "WHERE s.year = :year AND gp.name LIKE :grandPrixName " +
            "GROUP BY gp.name, p.name, p.surname " +
            "ORDER BY gp.name, SUM(j.id) DESC")
    List<UsedJokersGP> findJokersUsedOnGPs(@Param("year") Integer year, @Param("grandPrixName") String grandPrixName);

}
