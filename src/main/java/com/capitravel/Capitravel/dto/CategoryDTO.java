package com.capitravel.Capitravel.dto;

import com.capitravel.Capitravel.util.TrimmingStringDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryDTO {

    @NotBlank(message = "Name is required")
    @Size(min= 3,max = 16, message = "Name must be between 3 and 16 characters")
    @JsonDeserialize(using = TrimmingStringDeserializer.class)
    private String name;

    @NotBlank(message = "Description is required")
    @Size(min= 15,max = 256, message = "Description must be between 15 and 256 characters")
    @JsonDeserialize(using = TrimmingStringDeserializer.class)
    private String description;
}
