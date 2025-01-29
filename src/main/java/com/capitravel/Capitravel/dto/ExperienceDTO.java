package com.capitravel.Capitravel.dto;

import com.capitravel.Capitravel.util.TrimmingStringDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.DayOfWeek;
import java.util.List;

@Data
public class ExperienceDTO {

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 32, message = "Title must be between 3 and 32 characters")
    @JsonDeserialize(using = TrimmingStringDeserializer.class)
    private String title;

    @NotBlank(message = "Country is required")
    @Size(min = 2, max = 32, message = "Country must be between 2 and 32 characters")
    @JsonDeserialize(using = TrimmingStringDeserializer.class)
    private String country;

    @NotBlank(message = "Ubication is required")
    @Size(min = 2, max = 128, message = "Ubication must be between 2 and 128 characters")
    @JsonDeserialize(using = TrimmingStringDeserializer.class)
    private String ubication;

    @NotBlank(message = "Description is required")
    @Size(min = 32, max = 512, message = "Description must be between 32 and 512 characters")
    @JsonDeserialize(using = TrimmingStringDeserializer.class)
    private String description;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    @NotBlank(message = "Time unit is required")
    @Pattern(regexp = "^(?i)(minutes|hours|days)$", message = "Time unit must be one of: Minutes, Hours, Days")
    private String timeUnit;

    @NotEmpty(message = "Images cannot be empty")
    @Size(max = 10, message = "You can upload a maximum of 10 images")
    private List<@NotBlank(message = "Images cannot be blank") String> images;

    @NotEmpty(message = "Categories are required")
    @Size(max = 5, message = "You can select a maximum of 5 categories")
    private List<Long> categoryIds;

    private List<Long> propertyIds;

    @NotBlank(message = "Service hours are required")
    @Pattern(
            regexp = "^([01]\\d|2[0-3]):[0-5]\\d-([01]\\d|2[0-3]):[0-5]\\d$",
            message = "Service hours must be in the format HH:mm-HH:mm (e.g., 09:00-18:00)"
    )
    private String serviceHours;

    @NotEmpty(message = "Available days are required")
    private List<DayOfWeek> availableDays;
}
