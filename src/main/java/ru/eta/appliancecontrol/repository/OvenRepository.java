package ru.eta.appliancecontrol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.eta.appliancecontrol.domain.Oven;


public interface OvenRepository extends JpaRepository<Oven, Long> {
}
