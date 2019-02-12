package ru.eta.appliancecontrol.service.oven;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.eta.appliancecontrol.domain.Oven;
import ru.eta.appliancecontrol.exception.OvenNotFoundException;
import ru.eta.appliancecontrol.repository.OvenRepository;

@Service
public class OvenServiceImpl implements OvenService {

    private final OvenRepository ovenRepository;

    @Autowired
    public OvenServiceImpl(OvenRepository ovenRepository) {
        this.ovenRepository = ovenRepository;
    }

    public void controlDoor(long id, boolean isDoorMustBeOpen) {
        Oven oven = getOven(id);
        oven.setDoor(isDoorMustBeOpen);
        save(oven);
    }

    public void controlLightBulb(long id, boolean isLightBulbMustShine) {
        Oven oven = getOven(id);
        oven.setLightBulb(isLightBulbMustShine);
        save(oven);
    }

    public Oven getOven(long id) {
        return ovenRepository.findById(id).orElseThrow(OvenNotFoundException::new);
    }

    private void save(Oven oven) {
        ovenRepository.save(oven);
    }
}