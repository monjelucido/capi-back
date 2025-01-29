package com.capitravel.Capitravel.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Check-in date and time are required")
    @FutureOrPresent(message = "Check-in date and time must be in the future or present")
    private LocalDateTime checkIn;

    private LocalDateTime checkOut;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "experience_id", nullable = false)
    private Experience experience;

    public void calculateCheckOut() {
        if (experience != null && checkIn != null) {
            long quantity = experience.getQuantity();
            String timeUnit = experience.getTimeUnit().toLowerCase();

            switch (timeUnit) {
                case "minutes":
                    checkOut = checkIn.plusMinutes(quantity);
                    break;
                case "hours":
                    checkOut = checkIn.plusHours(quantity);
                    break;
                case "days":
                    checkOut = checkIn.plusDays(quantity);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid time unit in experience");
            }
        }
    }
}
