package ru.eta.appliancecontrol.domain.embeddable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.eta.appliancecontrol.domain.HeatingMode;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;


@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class CookingParam {
    private int temperature;
    private int cookingTimeSeconds;

    @ManyToOne
    private HeatingMode heatingMode;
}
