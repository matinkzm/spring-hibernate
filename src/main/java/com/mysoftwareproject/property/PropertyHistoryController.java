package com.mysoftwareproject.property;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/propertyHistory")
public class PropertyHistoryController {

    private final PropertyHistoryService propertyHistoryService;

    public PropertyHistoryController(PropertyHistoryService propertyHistoryService) {
        this.propertyHistoryService = propertyHistoryService;
    }

    @PostMapping
    public PropertyHistory addPropertyHistory(@RequestBody PropertyHistoryDto propertyHistoryDto) {
        return propertyHistoryService.addPropertyHistory(propertyHistoryDto);
    }

    @PutMapping("/{propertyHistoryId}")
    public PropertyHistory updatePropertyHistory(@PathVariable("propertyHistoryId") Integer propertyHistoryId, PropertyHistoryDto propertyHistoryDto) {
        return propertyHistoryService.updatePropertyHistory(propertyHistoryId, propertyHistoryDto);
    }

    @GetMapping("/getPropertyHistoryByDate/{date}")
    public Object getPropertyHistoryByDate(@PathVariable("date") LocalDate date, @RequestParam(required = false,defaultValue = "false") Boolean csv,@RequestParam(required = false,defaultValue = "false") Boolean excel) throws IOException {
        if (csv == Boolean.TRUE)
            return propertyHistoryService.exportToCsv(propertyHistoryService.findPropertyHistoryByDate(date));
        if (excel == Boolean.TRUE)
            return propertyHistoryService.exportToExcel(propertyHistoryService.findPropertyHistoryByDate(date));
        return propertyHistoryService.findPropertyHistoryByDate(date);
    }

    @GetMapping("/getPropertyHistoryByConsumerId/{consumerId}")
    public Object getPropertyHistoryByConsumerId(@PathVariable("consumerId") Integer consumerId, @RequestParam(required = false,defaultValue = "false") Boolean csv,@RequestParam(required = false,defaultValue = "false") Boolean excel) throws IOException {
        if (csv == Boolean.TRUE)
            return propertyHistoryService.exportToCsv(propertyHistoryService.findPropertyHistoryByConsumerId(consumerId));
        if (excel == Boolean.TRUE)
            return propertyHistoryService.exportToExcel(propertyHistoryService.findPropertyHistoryByConsumerId(consumerId));
        return propertyHistoryService.findPropertyHistoryByConsumerId(consumerId);
    }

    @GetMapping("/getPropertyHistoryByPropertyId/{propertyId}")
    public Object getPropertyHistoryByPropertyId(@PathVariable("propertyId") Integer propertyId, @RequestParam(required = false,defaultValue = "false") Boolean csv,@RequestParam(required = false,defaultValue = "false") Boolean excel) throws IOException {
        if (csv == Boolean.TRUE)
            return propertyHistoryService.exportToCsv(propertyHistoryService.findPropertyHistoryByPropertyId(propertyId));
        if (excel == Boolean.TRUE)
            return propertyHistoryService.exportToExcel(propertyHistoryService.findPropertyHistoryByPropertyId(propertyId));
        return propertyHistoryService.findPropertyHistoryByPropertyId(propertyId);
    }

    @GetMapping("/getPropertyHistoryByPropertyName/{propertyName}")
    public Object getPropertyHistoryByPropertyName(@PathVariable("propertyName") String propertyName, @RequestParam(required = false,defaultValue = "false") Boolean csv,@RequestParam(required = false,defaultValue = "false") Boolean excel) throws IOException {
        if (csv == Boolean.TRUE)
            return propertyHistoryService.exportToCsv(propertyHistoryService.findPropertyHistoryByPropertyName(propertyName));
        if (excel == Boolean.TRUE)
            return propertyHistoryService.exportToExcel(propertyHistoryService.findPropertyHistoryByPropertyName(propertyName));
        return propertyHistoryService.findPropertyHistoryByPropertyName(propertyName);
    }

    @GetMapping
    public Object getAllPropertyHistory(@RequestParam(required = false,defaultValue = "false") Boolean csv,@RequestParam(required = false,defaultValue = "false") Boolean excel) throws IOException {
        if (csv == Boolean.TRUE)
            return propertyHistoryService.exportToCsv(propertyHistoryService.findAllPropertyHistory());
        if (excel == Boolean.TRUE)
            return propertyHistoryService.exportToExcel(propertyHistoryService.findAllPropertyHistory());
        return propertyHistoryService.findAllPropertyHistory();
    }

    @GetMapping("/getPropertyHistoryById/{propertyHistoryId}")
    public PropertyHistory getPropertyHistoryById(@PathVariable("propertyHistoryId") Integer propertyHistoryId) {
        return propertyHistoryService.findPropertyHistoryById(propertyHistoryId);
    }

    @GetMapping("/getPropertyHistoryByStartDate/{date}")
    public Object getPropertyHistoryByStartUsingDate(@PathVariable("date") LocalDate date, @RequestParam(required = false,defaultValue = "false") Boolean csv,@RequestParam(required = false,defaultValue = "false") Boolean excel) throws IOException {
        if (csv == Boolean.TRUE)
            return propertyHistoryService.exportToCsv(propertyHistoryService.findAllPropertyHistoryByStartUsingDateLessThanEqual(date));
        if (excel == Boolean.TRUE)
            return propertyHistoryService.exportToExcel(propertyHistoryService.findAllPropertyHistoryByStartUsingDateLessThanEqual(date));
        return propertyHistoryService.findAllPropertyHistoryByStartUsingDateLessThanEqual(date);
    }

    @GetMapping("/getPropertyHistoryByEndDate/{date}")
    public Object getPropertyHistoryByEndUsingDate(@PathVariable("date") LocalDate date, @RequestParam(required = false,defaultValue = "false") Boolean csv,@RequestParam(required = false,defaultValue = "false") Boolean excel) throws IOException {
        if (csv == Boolean.TRUE)
            return propertyHistoryService.exportToCsv(propertyHistoryService.findAllPropertyHistoryByEndUsingDateGreaterThanEqual(date));
        if (excel == Boolean.TRUE)
            return propertyHistoryService.exportToExcel(propertyHistoryService.findAllPropertyHistoryByEndUsingDateGreaterThanEqual(date));
        return propertyHistoryService.findAllPropertyHistoryByEndUsingDateGreaterThanEqual(date);
    }

}
