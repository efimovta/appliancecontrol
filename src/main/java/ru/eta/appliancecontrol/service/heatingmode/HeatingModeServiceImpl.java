package ru.eta.appliancecontrol.service.heatingmode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.eta.appliancecontrol.domain.HeatingMode;
import ru.eta.appliancecontrol.repository.HeatingModeRepository;

import java.util.List;

@Service
public class HeatingModeServiceImpl implements HeatingModeService {

    private final HeatingModeRepository heatingModeRepository;

    @Autowired
    public HeatingModeServiceImpl(HeatingModeRepository heatingModeRepository) {
        this.heatingModeRepository = heatingModeRepository;
    }

    @Override
    public List<HeatingMode> getAll() {
        return heatingModeRepository.findAll();
    }

}
