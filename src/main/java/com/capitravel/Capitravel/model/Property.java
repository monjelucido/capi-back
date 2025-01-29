package com.capitravel.Capitravel.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Property {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;
        private String description;

        @Lob
        private String image;
}


