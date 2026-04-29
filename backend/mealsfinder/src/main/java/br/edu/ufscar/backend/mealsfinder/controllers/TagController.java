package br.edu.ufscar.backend.mealsfinder.controllers;

import br.edu.ufscar.backend.mealsfinder.models.enums.EnvironmentTag;
import br.edu.ufscar.backend.mealsfinder.models.enums.FoodTag;
import br.edu.ufscar.backend.mealsfinder.models.enums.ServiceTag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Exposes the canonical tag values (enums) for the API.
 */
@RestController
@RequestMapping("/api/tags")
public class TagController {

    @GetMapping("/food")
    public List<FoodTag> getAllFoodTags() {
        return List.of(FoodTag.values());
    }

    @GetMapping("/service")
    public List<ServiceTag> getAllServiceTags() {
        return List.of(ServiceTag.values());
    }

    @GetMapping("/environment")
    public List<EnvironmentTag> getAllEnvironmentTags() {
        return List.of(EnvironmentTag.values());
    }
}
