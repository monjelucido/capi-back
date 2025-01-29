package com.capitravel.Capitravel.service.impl;

import com.capitravel.Capitravel.dto.ReservationDTO;
import com.capitravel.Capitravel.dto.ReservationDatesDTO;
import com.capitravel.Capitravel.exception.ResourceInUseException;
import com.capitravel.Capitravel.exception.ResourceNotFoundException;
import com.capitravel.Capitravel.model.Experience;
import com.capitravel.Capitravel.model.Reservation;
import com.capitravel.Capitravel.model.User;
import com.capitravel.Capitravel.repository.ExperienceRepository;
import com.capitravel.Capitravel.repository.ReservationRepository;
import com.capitravel.Capitravel.repository.UserRepository;
import com.capitravel.Capitravel.service.EmailService;
import com.capitravel.Capitravel.service.ReservationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation createReservation(ReservationDTO reservationDTO) {
        User user = userRepository.findByEmail(reservationDTO.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + reservationDTO.getEmail()));

        Experience experience = experienceRepository.findById(reservationDTO.getExperienceId())
                .orElseThrow(() -> new ResourceNotFoundException("Experience not found with ID: " + reservationDTO.getExperienceId()));

        Reservation reservation = new Reservation();
        reservation.setCheckIn(reservationDTO.getCheckIn());
        reservation.setUser(user);
        reservation.setExperience(experience);
        reservation.calculateCheckOut();

        if (!isExperienceAvailable(reservation.getExperience().getId(), reservation.getCheckIn(), reservation.getCheckOut())) {
            throw new ResourceInUseException("Selected dates are already taken.");
        }

        Reservation reservationSaved = reservationRepository.save(reservation);
        emailService.sendReservationConfirmationEmail(user.getEmail(), user.getName(), user.getLastName(), experience.getTitle());
        return reservationSaved;
    }

    @Override
    public Reservation getReservationById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with ID: " + id));
    }

    @Override
    public void deleteReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with ID: " + id));
        reservationRepository.delete(reservation);
    }

    public boolean hasCheckoutPassed(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id: " + id));
        return reservation.getCheckOut().isBefore(LocalDateTime.now());
    }

    public List<ReservationDatesDTO> getReservationsByExperience(Long experienceId) {
        List<Reservation> reservations = reservationRepository.findByExperienceId(experienceId);
        return reservations.stream()
                .map(reservation -> new ReservationDatesDTO(reservation.getCheckIn(), reservation.getCheckOut()))
                .collect(Collectors.toList());
    }

    public List<Reservation> getReservationsByUser(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User with email: " + email + " not found");
        }

        return reservationRepository.findByUserId(user.get().getId());
    }

    private boolean isExperienceAvailable(Long experienceId, LocalDateTime checkin, LocalDateTime checkout) {
        List<Reservation> existingReservations = reservationRepository.findByExperienceId(experienceId);

        for (Reservation reservation : existingReservations) {
            if ((checkin.isBefore(reservation.getCheckOut()) && checkout.isAfter(reservation.getCheckIn())) ||
                    (checkin.isEqual(reservation.getCheckIn()) || checkout.isEqual(reservation.getCheckOut()))) {
                return false;
            }
        }
        return true;
    }
}
