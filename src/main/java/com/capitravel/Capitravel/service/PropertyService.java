package com.capitravel.Capitravel.service;

import com.capitravel.Capitravel.dto.PropertyDTO;
import com.capitravel.Capitravel.model.Property;

import java.util.List;

public interface PropertyService {

    Property getPropertyById(Long id);

    List<Property> getAllProperty();

    Property createProperty(PropertyDTO propertyDTO);

    Property updateProperty(Long id, PropertyDTO propertyDTO);

    void deleteProperty(Long id);
}
