package ru.eta.appliancecontrol.service.oven;

import ru.eta.appliancecontrol.domain.Oven;

public interface OvenService {

    void controlDoor(long id, boolean isDoorMustBeOpen);

    void controlLightBulb(long id, boolean isLightBulbMustShine);

    Oven getOven(long id);
}
