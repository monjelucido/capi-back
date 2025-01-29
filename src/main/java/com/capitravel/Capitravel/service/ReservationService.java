package com.capitravel.Capitravel.service;

import com.capitravel.Capitravel.dto.ReservationDTO;
import com.capitravel.Capitravel.dto.ReservationDatesDTO;
import com.capitravel.Capitravel.model.Reservation;

import java.util.List;

public interface ReservationService {
    List<Reservation> getAllReservations();

    Reservation createReservation(ReservationDTO reservationDTO);

    Reservation getReservationById(Long id);

    void deleteReservation(Long id);

    boolean hasCheckoutPassed(Long id);

    List<ReservationDatesDTO> getReservationsByExperience(Long experienceId);

    List<Reservation> getReservationsByUser(String email);
}
