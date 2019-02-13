package ru.eta.appliancecontrol.controller.oven;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.eta.appliancecontrol.domain.Oven;
import ru.eta.appliancecontrol.domain.embeddable.CookingParam;
import ru.eta.appliancecontrol.service.oven.OvenService;

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

    @PutMapping("/{id}/cooking")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cookingControl(@RequestBody boolean isCookingMustGoOn, @PathVariable("id") long id) {
        ovenService.setIsCookingMustGoOn(id, isCookingMustGoOn);
    }

    @PutMapping("/{id}/doorIsOpen")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void controlDoor(@RequestBody boolean isDoorMustBeOpen, @PathVariable("id") long id) {
        ovenService.setIsDoorOpen(id, isDoorMustBeOpen);
    }

    @PutMapping("/{id}/lightBulbIsOn")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void controlLightBulb(@RequestBody boolean isLightBulbMustShine, @PathVariable("id") long id) {
        ovenService.setIsLightBulbShine(id, isLightBulbMustShine);
    }

    @PutMapping("/{id}/recipe/id")
    public Oven setRecipe(@RequestBody long recipeId, @PathVariable("id") long id) {
        return ovenService.setRecipeAndItsCookingParam(id, recipeId);
    }

    @PutMapping("/{id}/cookingParam")
    public Oven setCookingParam(@RequestBody CookingParam cookingParam, @PathVariable("id") long id) {
        return ovenService.setCookingParam(id, cookingParam);
    }

}
