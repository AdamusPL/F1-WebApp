package com.typerf1.typerf1.repository;

import com.typerf1.typerf1.dto.Record;
import com.typerf1.typerf1.dto.RecordLong;
import com.typerf1.typerf1.model.Points;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorldRecordRepository extends JpaRepository<Points, Integer> {
    @Query("SELECT new com.typerf1.typerf1.dto.Record(p.name, p.surname, gp.name, s.year, pt.number) " +
            "FROM Points pt " +
            "JOIN pt.participant p " +
            "JOIN pt.session ses " +
            "JOIN ses.grandPrix gp " +
            "JOIN gp.season s " +
            "WHERE pt.number = (SELECT MAX(pt.number) FROM Points pt " +
            "JOIN pt.participant p " +
            "JOIN pt.session ses " +
            "JOIN ses.grandPrix gp " +
            "JOIN gp.season s " +
            "WHERE ses.name LIKE :session " +
            "AND p.id NOT IN ( " +
            "SELECT j.participant.id p " +
            "FROM Joker j " +
            "JOIN j.grandPrix gp2 " +
            "WHERE gp2.id = gp.id)) ")
    List<Record> findHighest(@Param("session") String session, Pageable pageable);

    @Query("SELECT new com.typerf1.typerf1.dto.Record(p.name, p.surname, gp.name, s.year, pt.number) " +
            "FROM Points pt " +
            "JOIN pt.participant p " +
            "JOIN pt.session ses " +
            "JOIN ses.grandPrix gp " +
            "JOIN gp.season s " +
            "WHERE pt.number = (SELECT MIN(pt.number) FROM Points pt " +
            "JOIN pt.participant p " +
            "JOIN pt.session ses " +
            "JOIN ses.grandPrix gp " +
            "JOIN gp.season s " +
            "WHERE ses.name LIKE :session " +
            "AND p.id NOT IN ( " +
            "SELECT j.participant.id p " +
            "FROM Joker j " +
            "JOIN j.grandPrix gp2 " +
            "WHERE gp2.id = gp.id)) ")
    List<Record> findLowest(@Param("session") String session, Pageable pageable);

    @Query("SELECT new com.typerf1.typerf1.dto.Record(p.name, p.surname, gp.name, s.year, pt.number) " +
            "FROM Points pt " +
            "JOIN pt.participant p " +
            "JOIN pt.session ses " +
            "JOIN ses.grandPrix gp " +
            "JOIN gp.season s " +
            "JOIN p.jokers j " +
            "WHERE ses.name = :session " +
            "AND pt.number = (SELECT MAX(pt2.number) FROM Points pt2 " +
            "JOIN pt2.session ses2 " +
            "JOIN ses2.grandPrix gp2 " +
            "JOIN gp2.joker j " +
            "WHERE ses2.name LIKE :session) ")
    List<Record> findHighestJoker(@Param("session") String session, Pageable pageable);

    @Query("SELECT new com.typerf1.typerf1.dto.Record(p.name, p.surname, gp.name, s.year, pt.number) " +
            "FROM Points pt " +
            "JOIN pt.participant p " +
            "JOIN pt.session ses " +
            "JOIN ses.grandPrix gp " +
            "JOIN gp.season s " +
            "JOIN p.jokers j " +
            "WHERE ses.name = :session " +
            "AND pt.number = (SELECT MIN(pt2.number) FROM Points pt2 " +
            "JOIN pt2.session ses2 " +
            "JOIN ses2.grandPrix gp2 " +
            "JOIN gp2.joker j " +
            "WHERE ses2.name LIKE :session) ")
    List<Record> findLowestJoker(@Param("session") String session, Pageable pageable);

    @Query("SELECT new com.typerf1.typerf1.dto.RecordLong(p.name, p.surname, gp.name, s.year, SUM(pt.number)) " +
            "FROM Points pt " +
            "JOIN pt.participant p " +
            "JOIN pt.session ses " +
            "JOIN ses.grandPrix gp " +
            "JOIN gp.season s " +
            "JOIN p.jokers j " +
            "GROUP BY gp.name, p.name, p.surname, s.year " +
            "HAVING SUM(pt.number) = (SELECT MAX(pointsSum) FROM ( " +
            "SELECT SUM(pt2.number) AS pointsSum " +
            "FROM Points pt2 " +
            "JOIN pt2.participant p2 " +
            "JOIN pt2.session ses2 " +
            "JOIN ses2.grandPrix gp2 " +
            "JOIN gp2.season s2 " +
            "GROUP BY gp2.id, p2.id " +
            "))")
    List<RecordLong> findHighestWeekendJoker(Pageable pageable);

    @Query("SELECT new com.typerf1.typerf1.dto.RecordLong(p.name, p.surname, gp.name, s.year, SUM(pt.number)) " +
            "FROM Points pt " +
            "JOIN pt.participant p " +
            "JOIN pt.session ses " +
            "JOIN ses.grandPrix gp " +
            "JOIN gp.season s " +
            "JOIN p.jokers j " +
            "GROUP BY gp.name, p.name, p.surname, s.year " +
            "HAVING SUM(pt.number) = (SELECT MIN(pointsSum) FROM ( " +
            "SELECT SUM(pt2.number) AS pointsSum " +
            "FROM Points pt2 " +
            "JOIN pt2.participant p2 " +
            "JOIN pt2.session ses2 " +
            "JOIN ses2.grandPrix gp2 " +
            "JOIN gp2.season s2 " +
            "GROUP BY gp2.id, p2.id " +
            "))")
    List<RecordLong> findLowestWeekendJoker(Pageable pageable);
}
