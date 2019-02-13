package ru.eta.appliancecontrol.controller.heatingmode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.eta.appliancecontrol.domain.HeatingMode;
import ru.eta.appliancecontrol.service.heatingmode.HeatingModeService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/heatingmode")
public class HeatingModeRestController {

    private final HeatingModeService heatingModeService;

    @Autowired
    public HeatingModeRestController(HeatingModeService heatingModeService) {
        this.heatingModeService = heatingModeService;
    }

    @GetMapping
    public List<HeatingMode> getAll() {
        return heatingModeService.getAll();
    }
}

