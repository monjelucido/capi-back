package com.capitravel.Capitravel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ReservationDatesDTO {
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
}
