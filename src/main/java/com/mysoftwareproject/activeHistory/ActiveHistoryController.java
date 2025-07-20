package com.mysoftwareproject.activeHistory;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequestMapping("/activeHistory")
public class ActiveHistoryController {

    private final ActiveHistoryService activeHistoryService;

    public ActiveHistoryController(ActiveHistoryService activeHistoryService) {
        this.activeHistoryService = activeHistoryService;
    }

    @GetMapping// correct
    public Object getAllActiveHistories(@RequestParam(required = false,defaultValue = "false") Boolean csv,@RequestParam(required = false,defaultValue = "false") Boolean excel) throws IOException {
        if (csv == Boolean.TRUE) {
            return activeHistoryService.exportToCsv(activeHistoryService.getAllActiveHistory());
        }
        if (excel == Boolean.TRUE) {
            return activeHistoryService.exportToExcel(activeHistoryService.getAllActiveHistory());
        }
        return activeHistoryService.getAllActiveHistory();
    }

    @GetMapping("/getActiveHistoryById/{activeHistoryId}") // correct
    public ActiveHistory getActiveHistoryById(@PathVariable("activeHistoryId") Integer activeHistoryId) {
        return activeHistoryService.getActiveHistoryById(activeHistoryId);
    }

    @GetMapping("/getActiveHistoryBySponsorId/{sponsorId}") // correct
    public Object getActiveHistoryBySponsorId(@PathVariable("sponsorId") Integer sponsorId,@RequestParam(required = false,defaultValue = "false") Boolean csv,@RequestParam(required = false,defaultValue = "false") Boolean excel) throws IOException {
        if (csv == Boolean.TRUE) {
            return activeHistoryService.exportToCsv(activeHistoryService.getActiveHistoryBySponsorId(sponsorId));
        }
        if (excel == Boolean.TRUE) {
            return activeHistoryService.exportToExcel(activeHistoryService.getActiveHistoryBySponsorId(sponsorId));
        }
        return activeHistoryService.getActiveHistoryBySponsorId(sponsorId);
    }

    @PutMapping("/{activeHistoryId}") // correct
    public ActiveHistory updateActiveHistory(@PathVariable("activeHistoryId") Integer activeHistoryId,@RequestBody ActiveHistoryDto activeHistoryDto) {
        return activeHistoryService.updateActiveHistory(activeHistoryId, activeHistoryDto);
    }

    @PostMapping // correct
    public ActiveHistory createActiveHistory(@RequestBody ActiveHistoryDto activeHistoryDto) {
        return activeHistoryService.addActiveHistory(activeHistoryDto);
    }

    @GetMapping("/getActiveHistoryByStartDate/{startDate}") // correct // input date format: 2024-12-24
    public Object getActiveHistoryByStartDateLessThan(@PathVariable("startDate") LocalDate startDate,@RequestParam(required = false,defaultValue = "false") Boolean csv,@RequestParam(required = false,defaultValue = "false") Boolean excel) throws IOException {
        if (csv == Boolean.TRUE) {
            return activeHistoryService.exportToCsv(activeHistoryService.getActiveHistoryByStartDateLessThan(startDate));
        }
        if (excel == Boolean.TRUE) {
            return activeHistoryService.exportToExcel(activeHistoryService.getActiveHistoryByStartDateLessThan(startDate));
        }
        return activeHistoryService.getActiveHistoryByStartDateLessThan(startDate);
    }

    @GetMapping("/getActiveHistoryByEndDate/{endDate}") // correct // input date format: 2024-12-24
    public Object getActiveHistoryByEndDateGreaterThan(@PathVariable("endDate") LocalDate endDate, @RequestParam(required = false,defaultValue = "false") Boolean csv, @RequestParam(required = false,defaultValue = "false") Boolean excel) throws IOException {
        if (csv == Boolean.TRUE) {
            return activeHistoryService.exportToCsv(activeHistoryService.getActiveHistoryByEndDateGreaterThan(endDate));
        }
        if (excel == Boolean.TRUE) {
            return activeHistoryService.exportToExcel(activeHistoryService.getActiveHistoryByEndDateGreaterThan(endDate));
        }
        return activeHistoryService.getActiveHistoryByEndDateGreaterThan(endDate);
    }

    // get a date and find records that has start date less than the date and end date bigger than the date
    @GetMapping("/getActiveHistoryByDateBetween/{date}") // correct // input date format: 2024-12-24
    public Object getActiveHistoryByDateBetween(@PathVariable("date") LocalDate date, @RequestParam(required = false,defaultValue = "false") Boolean csv,@RequestParam(required = false,defaultValue = "false") Boolean excel) throws IOException {
        if (csv == Boolean.TRUE) {
            return activeHistoryService.exportToCsv(activeHistoryService.getActiveHistoryByDateBetween(date));
        }
        if (excel == Boolean.TRUE) {
            return activeHistoryService.exportToExcel(activeHistoryService.getActiveHistoryByDateBetween(date));
        }
        return activeHistoryService.getActiveHistoryByDateBetween(date);
    }
}
