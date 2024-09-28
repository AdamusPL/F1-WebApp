package com.typerf1.typerf1.repository;

import com.typerf1.typerf1.model.Joker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JokerRepository extends JpaRepository<Joker, Integer> {
}
