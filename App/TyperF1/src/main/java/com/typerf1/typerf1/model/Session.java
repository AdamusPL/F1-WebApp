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
public class Session {
    @jakarta.persistence.Id
    Integer id;
    String name;

    public Session(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    @ManyToOne
    @JoinColumn(name = "GrandPrixId")
    GrandPrix grandPrix;

    @OneToOne(mappedBy = "session")
    private Points points;

    @OneToMany(mappedBy = "session")
    private List<Predictions> predictions;

}
