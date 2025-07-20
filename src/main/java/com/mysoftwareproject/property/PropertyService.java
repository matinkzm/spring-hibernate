package com.mysoftwareproject.property;

import com.mysoftwareproject.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final PropertyHistoryRepository propertyHistoryRepository;

    public PropertyService(PropertyRepository propertyRepository, PropertyHistoryRepository propertyHistoryRepository) {
        this.propertyRepository = propertyRepository;
        this.propertyHistoryRepository = propertyHistoryRepository;
    }

    public Property addProperty(Property property) {
        return propertyRepository.save(property);
    }

    public Property updateProperty(Integer id, Property property) {
        Property foundProperty = propertyRepository.findById(id).orElse(null);

        if (foundProperty != null) {
            if (property.getName() != null){
                foundProperty.setName(property.getName());
            }
            if (property.getAvailability() != null){
                foundProperty.setAvailability(property.getAvailability());
            }
            return propertyRepository.save(foundProperty);
        }

        else
            throw new NotFoundException("Property with id " + id + " not found");
    }
    public void deleteProperty(Integer id) {
        if (propertyRepository.existsById(id)) {
            propertyRepository.deleteById(id);
        }
        else
            throw new NotFoundException("Property with id " + id + " not found");
    }

    public List<Property> getAllPropertyByName(String name) {
        return propertyRepository.findAllByName(name);
    }


    public List<Property> getAllPropertyByAvailability(String availability) {
        return propertyRepository.findAllByAvailability(availability);
    }

    public List<Property> getAllProperty() {
        return propertyRepository.findAll();
    }

    public void assignPropertyToUser(Integer propertyId, Integer userId, LocalDate endDate) {
        Property property = propertyRepository.findById(propertyId).orElse(null);
        if (property != null && property.getPropertyCurrentUser() == null) {
            property.setPropertyCurrentUser(userId);
            property.setAvailability("false");
            PropertyHistory propertyHistory = new PropertyHistory();
            propertyHistory.setPropertyId(propertyId);
            propertyHistory.setConsumerId(userId);
            propertyHistory.setStartUsingDate(LocalDate.now());
            propertyHistory.setExpirationDate(endDate);
            propertyHistoryRepository.save(propertyHistory);
            property.setPropertyHistoryId(propertyHistory.getId());
            propertyRepository.save(property);
        } else if (property != null && property.getPropertyCurrentUser() != null) {
            PropertyHistory propertyHistory = propertyHistoryRepository.findById(property.getPropertyHistoryId()).orElse(null);
            propertyHistory.setEndUsingDate(LocalDate.now());
            property.setPropertyCurrentUser(userId);
            property.setAvailability("false");
            PropertyHistory propertyHistory1 = new PropertyHistory();
            propertyHistory1.setPropertyId(propertyId);
            propertyHistory1.setConsumerId(userId);
            propertyHistory1.setStartUsingDate(LocalDate.now());
            propertyHistory1.setExpirationDate(endDate);
            propertyHistoryRepository.save(propertyHistory1);
            property.setPropertyHistoryId(propertyHistory1.getId());
            propertyRepository.save(property);
        }
        else {
            throw new NotFoundException("Property with id " + propertyId + " not found");
        }

    }

    public void unassignPropertyFromUser(Integer propertyId) {
        Property property = propertyRepository.findById(propertyId).orElse(null);
        PropertyHistory propertyHistory = propertyHistoryRepository.findById(property.getPropertyHistoryId()).orElse(null);
        propertyHistory.setEndUsingDate(LocalDate.now());
        property.setPropertyCurrentUser(null);
        property.setAvailability("true");
        propertyRepository.save(property);
    }

}
