package com.capitravel.Capitravel.repository;

import com.capitravel.Capitravel.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUserId(Long userId);

    List<Reservation> findByExperienceId(Long experienceId);
}
