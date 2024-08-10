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
public interface PersonalBestRepository extends JpaRepository<Points, Integer> {

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
            "WHERE ses.name = :session " +
            "AND p.name = :firstName " +
            "AND p.surname = :surname " +
            "AND p.id NOT IN ( " +
            "SELECT j.participant.id " +
            "FROM Joker j " +
            "JOIN j.grandPrix gp2 " +
            "WHERE gp2.id = gp.id)) " +
            "AND p.name = :firstName " +
            "AND p.surname = :surname ")
    List<Record> findHighest(@Param("session") String session, @Param("firstName") String firstName, @Param("surname") String surname, Pageable pageable);

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
            "WHERE ses.name = :session " +
            "AND p.name = :firstName " +
            "AND p.surname = :surname " +
            "AND p.id NOT IN ( " +
            "SELECT j.participant.id " +
            "FROM Joker j " +
            "JOIN j.grandPrix gp2 " +
            "WHERE gp2.id = gp.id)) " +
            "AND p.name = :firstName " +
            "AND p.surname = :surname ")
    List<Record> findLowest(@Param("session") String session, @Param("firstName") String firstName, @Param("surname") String surname, Pageable pageable);
}
