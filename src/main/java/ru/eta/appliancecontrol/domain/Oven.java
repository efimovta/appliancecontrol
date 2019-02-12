package ru.eta.appliancecontrol.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.eta.appliancecontrol.domain.embeddable.CookingParam;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class Oven {

    @Embedded
    CookingParam cookingParam;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false)
    private String name;
    private boolean door;
    private boolean lightBulb;
    private boolean cook;
    @ManyToOne
    private Recipe recipe;

}
