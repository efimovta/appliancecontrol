package ru.eta.appliancecontrol.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique = true, nullable = false)
    private String name;

    private String description;
    private int temperature;
    private int cookingTimeSeconds;

    @ManyToOne
    private HeatingMode heatingMode;

}
