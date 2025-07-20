package com.mysoftwareproject.sponsorOrphanHistory;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/sponsorOrphanHistory")
public class SponsorOrphanHistoryController {

    private final SponsorOrphanHistoryService sponsorOrphanHistoryService;

    public SponsorOrphanHistoryController(SponsorOrphanHistoryService sponsorOrphanHistoryService) {
        this.sponsorOrphanHistoryService = sponsorOrphanHistoryService;
    }

    @GetMapping
    public Object getAllSponsorOrphanHistory(@RequestParam(required = false,defaultValue = "false") Boolean csv,@RequestParam(required = false,defaultValue = "false") Boolean excel) throws IOException {
        if(csv == Boolean.TRUE) {
            return sponsorOrphanHistoryService.exportToCsv(sponsorOrphanHistoryService.getAllSponsorOrphanHistory());
        }
        if(excel == Boolean.TRUE) {
            return sponsorOrphanHistoryService.exportToExcel(sponsorOrphanHistoryService.getAllSponsorOrphanHistory());
        }
        return sponsorOrphanHistoryService.getAllSponsorOrphanHistory();
    }

    @GetMapping("/getSponsorOrphanHistoryBySponsorId/{sponsorId}")
    public Object getSponsorOrphanHistoryBySponsorId(@PathVariable Integer sponsorId,@RequestParam(required = false,defaultValue = "false") Boolean csv,@RequestParam(required = false,defaultValue = "false") Boolean excel) throws IOException {
        if(csv == Boolean.TRUE) {
            return sponsorOrphanHistoryService.exportToCsv(sponsorOrphanHistoryService.getSponsorOrphanHistoryBySponsorId(sponsorId));
        }
        if(excel == Boolean.TRUE) {
            return sponsorOrphanHistoryService.exportToExcel(sponsorOrphanHistoryService.getSponsorOrphanHistoryBySponsorId(sponsorId));
        }
        return sponsorOrphanHistoryService.getSponsorOrphanHistoryBySponsorId(sponsorId);
    }

    @GetMapping("/getSponsorOrphanHistoryByOrphanId/{orphanId}")
    public Object getSponsorOrphanHistoryByOrphanId(@PathVariable Integer orphanId,@RequestParam(required = false,defaultValue = "false") Boolean csv, @RequestParam(required = false,defaultValue = "false") Boolean excel) throws IOException {
        if(csv == Boolean.TRUE) {
            return sponsorOrphanHistoryService.exportToCsv(sponsorOrphanHistoryService.getSponsorOrphanHistoryByOrphanId(orphanId));
        }
        if(excel == Boolean.TRUE) {
            return sponsorOrphanHistoryService.exportToExcel(sponsorOrphanHistoryService.getSponsorOrphanHistoryByOrphanId(orphanId));
        }
        return sponsorOrphanHistoryService.getSponsorOrphanHistoryByOrphanId(orphanId);
    }

    @GetMapping("/getSponsorOrphanHistoryById/{sponsorOrphanHistoryById}")
    public SponsorOrphanHistory getSponsorOrphanHistoryById(@PathVariable Integer sponsorOrphanHistoryById) {
        return sponsorOrphanHistoryService.findSponsorOrphanHistoryById(sponsorOrphanHistoryById);
    }

    @PostMapping
    public SponsorOrphanHistory addSponsorOrphanHistory(@RequestBody SponsorOrphanHistoryDto sponsorOrphanHistoryDto) {
        return sponsorOrphanHistoryService.addSponsorOrphanHistory(sponsorOrphanHistoryDto);
    }

    @PutMapping("/{sponsorOrphanHistoryById}")
    public SponsorOrphanHistory updateSponsorOrphanHistory(@PathVariable("sponsorOrphanHistoryById") Integer sponsorOrphanHistoryById,@RequestBody SponsorOrphanHistoryDto sponsorOrphanHistoryDto) {
        return sponsorOrphanHistoryService.updateSponsorOrphanHistory(sponsorOrphanHistoryById, sponsorOrphanHistoryDto);
    }

//    @DeleteMapping("/{sponsorOrphanHistoryById}")
//    public void deleteSponsorOrphanHistory(@PathVariable("sponsorOrphanHistoryById") Integer sponsorOrphanHistoryById) {
//        sponsorOrphanHistoryService.deleteSponsorOrphanHistory(sponsorOrphanHistoryById);
//    }

    @GetMapping("/getSponsorOrphanHistoryByDate/{date}")
    public Object getSponsorOrphanHistoryByDate(@PathVariable("date") LocalDate date,@RequestParam(required = false,defaultValue = "false") Boolean csv, @RequestParam(required = false,defaultValue = "false") Boolean excel) throws IOException {
        if (csv == Boolean.TRUE) {
            return sponsorOrphanHistoryService.exportToCsv(sponsorOrphanHistoryService.findSponsorOrphanHistoryByDate(date));
        }
        if(excel == Boolean.TRUE) {
            return sponsorOrphanHistoryService.exportToExcel(sponsorOrphanHistoryService.findSponsorOrphanHistoryByDate(date));
        }
        return sponsorOrphanHistoryService.findSponsorOrphanHistoryByDate(date);
    }

    @GetMapping("/getSponsorOrphanHistoryByStartDate/{startDate}")
    public Object getSponsorOrphanHistoryByStartDateLessThanEqual(@PathVariable("startDate") LocalDate startDate, @RequestParam(required = false,defaultValue = "false") Boolean csv,@RequestParam(required = false,defaultValue = "false") Boolean excel) throws IOException {
        if (csv == Boolean.TRUE) {
            return sponsorOrphanHistoryService.exportToCsv(sponsorOrphanHistoryService.findSponsorOrphanHistoryByStartDateLessThanEqual(startDate));
        }
        if (excel == Boolean.TRUE) {
            return sponsorOrphanHistoryService.exportToExcel(sponsorOrphanHistoryService.findSponsorOrphanHistoryByStartDateLessThanEqual(startDate));
        }
        return sponsorOrphanHistoryService.findSponsorOrphanHistoryByStartDateLessThanEqual(startDate);
    }

    @GetMapping("/getSponsorOrphanHistoryByEndDate/{endDate}")
    public Object getSponsorOrphanHistoryByEndDate(@PathVariable("endDate") LocalDate endDate, @RequestParam(required = false,defaultValue = "false") Boolean csv, @RequestParam(required = false,defaultValue = "false") Boolean excel) throws IOException {
        if (csv == Boolean.TRUE) {
            return sponsorOrphanHistoryService.exportToCsv(sponsorOrphanHistoryService.findSponsorOrphanHistoryByEndDateGreaterThanEqual(endDate));
        }
        if (excel == Boolean.TRUE) {
            return sponsorOrphanHistoryService.exportToExcel(sponsorOrphanHistoryService.findSponsorOrphanHistoryByEndDateGreaterThanEqual(endDate));
        }
        return sponsorOrphanHistoryService.findSponsorOrphanHistoryByEndDateGreaterThanEqual(endDate);
    }

}
