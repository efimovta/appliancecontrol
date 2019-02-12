package ru.eta.appliancecontrol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.eta.appliancecontrol.domain.Recipe;


public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
