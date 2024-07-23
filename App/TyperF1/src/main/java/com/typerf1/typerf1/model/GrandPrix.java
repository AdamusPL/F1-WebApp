package com.typerf1.typerf1.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class GrandPrix {
    @jakarta.persistence.Id
    Integer id;
    String name;

    @ManyToOne
    @JoinColumn(name = "SeasonId")
    Season season;

    @OneToMany(mappedBy = "grandPrix")
    private List<Session> session;

    @OneToOne(mappedBy = "grandPrix")
    Joker joker;

}
