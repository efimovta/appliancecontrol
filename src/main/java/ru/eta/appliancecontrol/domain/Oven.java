package ru.eta.appliancecontrol.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class Oven {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String name;

    private boolean door;
    private boolean lightBulb;
    private boolean cook;

    private int temperature;
    private int remainTimeSeconds;

    @ManyToOne
    private HeatingMode heatingMode;

    @ManyToOne
    private Recipe lastAppliedRecipe;

}
