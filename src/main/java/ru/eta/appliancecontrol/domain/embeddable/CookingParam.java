package ru.eta.appliancecontrol.domain.embeddable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.eta.appliancecontrol.domain.HeatingMode;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;


@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class CookingParam {
    private int temperature;
    private int cookingTimeSeconds;

    @ManyToOne
    private HeatingMode heatingMode;

    public CookingParam copy() {
        CookingParam copy = new CookingParam();
        copy.setHeatingMode(getHeatingMode());
        copy.setCookingTimeSeconds(getCookingTimeSeconds());
        copy.setTemperature(getTemperature());
        return copy;
    }
}
