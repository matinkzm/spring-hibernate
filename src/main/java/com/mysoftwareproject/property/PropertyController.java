package com.mysoftwareproject.property;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/property")
public class PropertyController {

    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @PostMapping // correct
    public Property createProperty(@RequestBody Property property) {
        return propertyService.addProperty(property);
    }

    @PutMapping("/{propertyId}") // correct
    public Property updateProperty(@PathVariable("propertyId") Integer id,@RequestBody Property property) {
        return propertyService.updateProperty(id, property);
    }
//
//    @DeleteMapping("/{propertyId}")
//    public void deleteProperty(@PathVariable("propertyId") Integer id) {
//        propertyService.deleteProperty(id);
//    }


    @GetMapping("/getPropertyByName/{name}") // correct
    public List<Property> getAllPropertyByName(@PathVariable("name") String name) {
        return propertyService.getAllPropertyByName(name);
    }

    @GetMapping("/getPropertyByAvailability/{availability}") // correct
    public List<Property> getAllPropertyByAvailability(@PathVariable("availability") String availability) {
        return propertyService.getAllPropertyByAvailability(availability);
    }

    @GetMapping // correct
    public List<Property> getAllProperty() {
        return propertyService.getAllProperty();
    }

    @GetMapping("/assignProperty/{propertyId}/{userId}") // correct
    public void assignProperty(@PathVariable Integer propertyId, @PathVariable Integer userId, @PathVariable LocalDate endDate){
        propertyService.assignPropertyToUser(propertyId, userId, endDate);
    }

    @GetMapping("/unassignPropertyFromUser/{propertyId}")
    public void unassignPropertyFromUser(@PathVariable Integer propertyId){
        propertyService.unassignPropertyFromUser(propertyId);
    }


}
