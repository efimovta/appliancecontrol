package ru.eta.appliancecontrol.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.eta.appliancecontrol.domain.Oven;
import ru.eta.appliancecontrol.service.OvenService;

@RestController
@RequestMapping(value = "/api/v1/oven")
public class OvenRestController {

    private final OvenService ovenService;

    @Autowired
    public OvenRestController(OvenService ovenService) {
        this.ovenService = ovenService;
    }

    @GetMapping("/{id}")
    public Oven getOvenById(@PathVariable("id") long id) {
        return ovenService.getOven(id);
    }

    @PutMapping("/{id}/door")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void controlDoor(@RequestBody boolean isDoorMustBeOpen, @PathVariable("id") long id) {
        ovenService.controlDoor(id, isDoorMustBeOpen);
    }

    @PutMapping("/{id}/lightBulb")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void controlLightBulb(@RequestBody boolean isLightBulbMustShine, @PathVariable("id") long id) {
        ovenService.controlLightBulb(id, isLightBulbMustShine);
    }

}
