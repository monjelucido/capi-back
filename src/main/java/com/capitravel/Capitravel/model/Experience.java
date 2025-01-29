package com.capitravel.Capitravel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.DayOfWeek;
import java.util.List;

@Data
@Entity
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String country;
    private String ubication;

    @Column(length = 512)
    private String description;

    private Integer quantity;
    private String timeUnit;
    private double reputation;

    private int ratingCount;

    @Lob
    @ElementCollection
    private List<String> images;

    @ManyToMany
    @JoinTable(
            name = "experience_category",
            joinColumns = @JoinColumn(name = "experience_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;

    @ManyToMany
    @JoinTable(
            name = "experience_property",
            joinColumns = @JoinColumn(name = "experience_id"),
            inverseJoinColumns = @JoinColumn(name = "property_id")
    )

    private List<Property> properties;
    private String serviceHours;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<DayOfWeek> availableDays;
}
