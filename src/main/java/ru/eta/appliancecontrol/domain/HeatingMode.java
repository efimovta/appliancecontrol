package ru.eta.appliancecontrol.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class HeatingMode {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique = true, nullable = false)
    private String name;

    public HeatingMode copy() {
        HeatingMode copy = new HeatingMode();
        copy.setName(getName());
        copy.setId(getId());
        return copy;
    }

}
