package com.typerf1.typerf1.repository;

import com.typerf1.typerf1.dto.Score;
import com.typerf1.typerf1.model.Points;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatisticsRepository extends JpaRepository<Points, Integer> {

    @Query("SELECT new com.typerf1.typerf1.dto.Score(s.year, gp.name, ses.name, p.name, p.surname, pt.number) " +
            "FROM Points pt " +
            "JOIN pt.participant p " +
            "JOIN pt.session ses " +
            "JOIN ses.grandPrix gp " +
            "JOIN gp.season s " +
            "WHERE s.year = :year " +
            "ORDER BY ses.id, pt.number DESC")
    List<Score> findSeasonScores(@Param("year") Integer year);

}
