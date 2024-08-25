package com.typerf1.typerf1.repository;

import com.typerf1.typerf1.model.GrandPrix;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GrandPrixRepository extends JpaRepository<GrandPrix, Integer> {

    @Query("SELECT new com.typerf1.typerf1.model.GrandPrix(gp.id, gp.name) " +
            "FROM GrandPrix gp " +
            "JOIN gp.season s " +
            "WHERE s.year = :year")
    List<GrandPrix> getThisYearGrandPrixWeekends(@Param("year") int year);

}
