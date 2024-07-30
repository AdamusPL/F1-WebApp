package com.typerf1.typerf1.repository;

import com.typerf1.typerf1.dto.Record;
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
            "SELECT j.participant.id " +
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
            "SELECT j.participant.id " +
            "FROM Joker j " +
            "JOIN j.grandPrix gp2 " +
            "WHERE gp2.id = gp.id)) ")
    List<Record> findLowest(@Param("session") String session, Pageable pageable);

    @Query("SELECT new com.typerf1.typerf1.dto.Record(p.name, p.surname, gp.name, s.year, MAX(pt.number)) " +
            "FROM Points pt " +
            "JOIN pt.participant p " +
            "JOIN pt.session ses " +
            "JOIN ses.grandPrix gp " +
            "JOIN gp.season s " +
            "JOIN p.jokers j " +
            "WHERE ses.name = :session " +
            "AND gp.id = (SELECT gp2.id FROM Joker j2 " +
            "JOIN j2.grandPrix gp2 ) " +
            "GROUP BY p.name, p.surname, gp.name, s.year ")
    List<Record> findHighestJoker(@Param("session") String session, Pageable pageable);

    @Query("SELECT new com.typerf1.typerf1.dto.Record(p.name, p.surname, gp.name, s.year, MIN(pt.number)) " +
            "FROM Points pt " +
            "JOIN pt.participant p " +
            "JOIN pt.session ses " +
            "JOIN ses.grandPrix gp " +
            "JOIN gp.season s " +
            "JOIN p.jokers j " +
            "WHERE ses.name = :session " +
            "AND gp.id = (SELECT gp2.id FROM Joker j2 " +
            "JOIN j2.grandPrix gp2 ) " +
            "GROUP BY p.name, p.surname, gp.name, s.year ")
    List<Record> findLowestJoker(@Param("session") String session, Pageable pageable);

    @Query(
            value = "SELECT p.Name, p.Surname, gp.Name AS GrandPrixName, s.Year, SUM(pt.Number) AS PointsSum " +
                    "FROM Participant p " +
                    "INNER JOIN Points pt ON p.Id = pt.ParticipantId " +
                    "INNER JOIN Session ses ON pt.SessionId = ses.Id " +
                    "INNER JOIN GrandPrix gp ON ses.GrandPrixId = gp.Id " +
                    "INNER JOIN Season s ON gp.SeasonId = s.Id " +
                    "LEFT JOIN Joker j ON p.Id = j.ParticipantId AND j.GrandPrixId = gp.Id " +
                    "WHERE ses.Name != 'Sprint' " +
                    "AND gp.Id NOT IN ( " +
                    "    SELECT DISTINCT gp2.Id " +
                    "    FROM GrandPrix gp2 " +
                    "    INNER JOIN Session ses2 ON gp2.Id = ses2.GrandPrixId " +
                    "    INNER JOIN Joker j2 ON gp2.Id = j2.GrandPrixId " +
                    "    WHERE ses2.Name = 'Sprint' " +
                    ") " +
                    "GROUP BY p.Name, p.Surname, gp.Name, s.Year " +
                    "HAVING SUM(pt.Number) = ( " +
                    "    SELECT MAX(PointsSum) " +
                    "    FROM ( " +
                    "        SELECT gp3.Id, p3.Id AS ParticipantId, SUM(pt3.Number) AS PointsSum " +
                    "        FROM Participant p3 " +
                    "        INNER JOIN Points pt3 ON p3.Id = pt3.ParticipantId " +
                    "        INNER JOIN Session ses3 ON pt3.SessionId = ses3.Id " +
                    "        INNER JOIN GrandPrix gp3 ON ses3.GrandPrixId = gp3.Id " +
                    "        INNER JOIN Season s3 ON gp3.SeasonId = s3.Id " +
                    "        LEFT JOIN Joker j3 ON p3.Id = j3.ParticipantId AND j3.GrandPrixId = gp3.Id " +
                    "        WHERE ses3.Name != 'Sprint' " +
                    "        AND gp3.Id NOT IN ( " +
                    "            SELECT DISTINCT gp4.Id " +
                    "            FROM GrandPrix gp4 " +
                    "            INNER JOIN Session ses4 ON gp4.Id = ses4.GrandPrixId " +
                    "            INNER JOIN Joker j4 ON gp4.Id = j4.GrandPrixId " +
                    "            WHERE ses4.Name = 'Sprint' " +
                    "        ) " +
                    "        GROUP BY gp3.Id, p3.Id " +
                    "    ) AS SubQuery " +
                    ")",
            nativeQuery = true
    )
    List<Object[]> findHighestWeekend(Pageable pageable);

    @Query(
            value = "SELECT p.Name, p.Surname, gp.Name AS GrandPrixName, s.Year, SUM(pt.Number) AS PointsSum " +
                    "FROM Participant p " +
                    "INNER JOIN Points pt ON p.Id = pt.ParticipantId " +
                    "INNER JOIN Session ses ON pt.SessionId = ses.Id " +
                    "INNER JOIN GrandPrix gp ON ses.GrandPrixId = gp.Id " +
                    "INNER JOIN Season s ON gp.SeasonId = s.Id " +
                    "LEFT JOIN Joker j ON p.Id = j.ParticipantId AND j.GrandPrixId = gp.Id " +
                    "WHERE ses.Name != 'Sprint' " +
                    "AND gp.Id NOT IN ( " +
                    "    SELECT DISTINCT gp2.Id " +
                    "    FROM GrandPrix gp2 " +
                    "    INNER JOIN Session ses2 ON gp2.Id = ses2.GrandPrixId " +
                    "    INNER JOIN Joker j2 ON gp2.Id = j2.GrandPrixId " +
                    "    WHERE ses2.Name = 'Sprint' " +
                    ") " +
                    "GROUP BY p.Name, p.Surname, gp.Name, s.Year " +
                    "HAVING SUM(pt.Number) = ( " +
                    "    SELECT MIN(PointsSum) " +
                    "    FROM ( " +
                    "        SELECT gp3.Id, p3.Id AS ParticipantId, SUM(pt3.Number) AS PointsSum " +
                    "        FROM Participant p3 " +
                    "        INNER JOIN Points pt3 ON p3.Id = pt3.ParticipantId " +
                    "        INNER JOIN Session ses3 ON pt3.SessionId = ses3.Id " +
                    "        INNER JOIN GrandPrix gp3 ON ses3.GrandPrixId = gp3.Id " +
                    "        INNER JOIN Season s3 ON gp3.SeasonId = s3.Id " +
                    "        LEFT JOIN Joker j3 ON p3.Id = j3.ParticipantId AND j3.GrandPrixId = gp3.Id " +
                    "        WHERE ses3.Name != 'Sprint' " +
                    "        AND gp3.Id NOT IN ( " +
                    "            SELECT DISTINCT gp4.Id " +
                    "            FROM GrandPrix gp4 " +
                    "            INNER JOIN Session ses4 ON gp4.Id = ses4.GrandPrixId " +
                    "            INNER JOIN Joker j4 ON gp4.Id = j4.GrandPrixId " +
                    "            WHERE ses4.Name = 'Sprint' " +
                    "        ) " +
                    "        GROUP BY gp3.Id, p3.Id " +
                    "    ) AS SubQuery " +
                    ")",
            nativeQuery = true
    )
    List<Object[]> findLowestWeekend(Pageable pageable);

    @Query(
            value = "SELECT p.Name, p.Surname, gp.Name AS GrandPrixName, s.Year, SUM(pt.Number) AS PointsSum " +
                    "FROM Participant p " +
                    "INNER JOIN Points pt ON p.Id = pt.ParticipantId " +
                    "INNER JOIN Session ses ON pt.SessionId = ses.Id " +
                    "INNER JOIN GrandPrix gp ON ses.GrandPrixId = gp.Id " +
                    "INNER JOIN Season s ON gp.SeasonId = s.Id " +
                    "INNER JOIN Joker j ON p.Id = j.ParticipantId " +
                    "WHERE gp.Id = ( " +
                    "    SELECT jgp.Id FROM Joker j " +
                    "    INNER JOIN GrandPrix jgp ON jgp.Id = j.GrandPrixId " +
                    ") " +
                    "GROUP BY gp.Name, p.Name, p.Surname, s.Year " +
                    "HAVING SUM(pt.Number) = ( " +
                    "    SELECT MAX(PointsSum) FROM ( " +
                    "        SELECT jgp.Id, p3.Id AS ParticipantId, SUM(pt3.Number) AS PointsSum " +
                    "        FROM Participant p3 " +
                    "        INNER JOIN Points pt3 ON p3.Id = pt3.ParticipantId " +
                    "        INNER JOIN Session ses3 ON pt3.SessionId = ses3.Id " +
                    "        INNER JOIN GrandPrix jgp ON ses3.GrandPrixId = jgp.Id " +
                    "        INNER JOIN Season s3 ON jgp.SeasonId = s3.Id " +
                    "        INNER JOIN Joker j3 ON p3.Id = j3.ParticipantId " +
                    "        WHERE jgp.Id = ( " +
                    "            SELECT jgp2.Id FROM Joker j2 " +
                    "            INNER JOIN GrandPrix jgp2 ON jgp2.Id = j2.GrandPrixId " +
                    "        ) " +
                    "        GROUP BY jgp.Id, p3.Id " +
                    "    ) AS SubQuery " +
                    ")",
            nativeQuery = true
    )
    List<Object[]> findHighestSprintWeekendJoker(Pageable pageable);

    @Query(
            value = "SELECT p.Name, p.Surname, gp.Name AS GrandPrixName, s.Year, SUM(pt.Number) AS PointsSum " +
                    "FROM Participant p " +
                    "INNER JOIN Points pt ON p.Id = pt.ParticipantId " +
                    "INNER JOIN Session ses ON pt.SessionId = ses.Id " +
                    "INNER JOIN GrandPrix gp ON ses.GrandPrixId = gp.Id " +
                    "INNER JOIN Season s ON gp.SeasonId = s.Id " +
                    "INNER JOIN Joker j ON p.Id = j.ParticipantId " +
                    "WHERE gp.Id = ( " +
                    "    SELECT jgp.Id FROM Joker j " +
                    "    INNER JOIN GrandPrix jgp ON jgp.Id = j.GrandPrixId " +
                    ") " +
                    "GROUP BY gp.Name, p.Name, p.Surname, s.Year " +
                    "HAVING SUM(pt.Number) = ( " +
                    "    SELECT MIN(PointsSum) FROM ( " +
                    "        SELECT jgp.Id, p3.Id AS ParticipantId, SUM(pt3.Number) AS PointsSum " +
                    "        FROM Participant p3 " +
                    "        INNER JOIN Points pt3 ON p3.Id = pt3.ParticipantId " +
                    "        INNER JOIN Session ses3 ON pt3.SessionId = ses3.Id " +
                    "        INNER JOIN GrandPrix jgp ON ses3.GrandPrixId = jgp.Id " +
                    "        INNER JOIN Season s3 ON jgp.SeasonId = s3.Id " +
                    "        INNER JOIN Joker j3 ON p3.Id = j3.ParticipantId " +
                    "        WHERE jgp.Id = ( " +
                    "            SELECT jgp2.Id FROM Joker j2 " +
                    "            INNER JOIN GrandPrix jgp2 ON jgp2.Id = j2.GrandPrixId " +
                    "        ) " +
                    "        GROUP BY jgp.Id, p3.Id " +
                    "    ) AS SubQuery " +
                    ")",
            nativeQuery = true
    )
    List<Object[]> findLowestSprintWeekendJoker(Pageable pageable);


//    List<Object[]> findHighestWeekendJoker(Pageable pageable);
//
//
//    List<Object[]> findLowestWeekendJoker(Pageable pageable);
}
