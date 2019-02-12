package ru.eta.appliancecontrol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.eta.appliancecontrol.domain.HeatingMode;


public interface HeatingModeRepository extends JpaRepository<HeatingMode, Long> {
}
