package com.mysoftwareproject.sponsor;


import com.mysoftwareproject.orphan.Orphan;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/sponsor")
public class SponsorController {

    private final SponsorService sponsorService;

    public SponsorController(SponsorService sponsorService) {
        this.sponsorService = sponsorService;
    }

    @PostMapping // correct // input birthday date like: "dateOfBirth": "1381-06-29"
    public Sponsor createSponsor(@Valid @RequestBody SponsorDto sponsorDto) {
        return sponsorService.createSponsor(sponsorDto);
    }

    @PutMapping("/{sponsorId}") // correct
    public Sponsor updateSponsor(@PathVariable("sponsorId") Integer sponsorId,@RequestBody SponsorDto sponsorDto) {
        return sponsorService.updateSponsor(sponsorId, sponsorDto);
    }

    @GetMapping("/getSponsorById/{sponsorId}") // correct
    public Sponsor getSponsorById(@PathVariable Integer sponsorId) {
        return sponsorService.findSponsorById(sponsorId);
    }

    @GetMapping// correct
    public List<Sponsor> getAllSponsors() {
        return sponsorService.findAllSponsors();
    }

    @GetMapping("/getSponsorByNationalCode/{nationalCode}") //correct
    public Sponsor getSponsorByNationalCode(@PathVariable String nationalCode) {
        return sponsorService.findSponsorByNationalCode(nationalCode);
    }

    @GetMapping("/getSponsorsByName/{name}") // correct
    public List<Sponsor> getSponsorsByName(@PathVariable String name) {
        return sponsorService.findSponsorByName(name);
    }

    @GetMapping("/getSponsorsByLastname/{lastname}") // correct
    public List<Sponsor> getSponsorsByLastName(@PathVariable String lastname) {
        return sponsorService.findSponsorByLastName(lastname);
    }

    @GetMapping("/getSponsorsByBirthdateGreaterThanOrEqual/{birthdate}") //correct // input date: 1381-06-29
    public List<Sponsor> getSponsorsWithBirthdateGreaterThanOrEqual(@PathVariable("birthdate") LocalDate birthdate) {
        return sponsorService.findAllSponsorsWithBirthdateGreaterThanOrEqual(birthdate);
    }

    @GetMapping("/getSponsorsByBirthdateLessThanOrEqual/{birthdate}") // correct
    public List<Sponsor> getSponsorsWithBirthdateLessThanOrEqual(@PathVariable("birthdate") LocalDate birthdate) {
        return sponsorService.findAllSponsorsWithBirthdateLessThanOrEqual(birthdate);
    }

    @GetMapping("/getSponsorsByBirthdateBetween/{birthdate1}/{birthdate2}") // correct
    public List<Sponsor> getSponsorsWithBirthdateBetween(@PathVariable("birthdate1") LocalDate birthdate1,@PathVariable("birthdate2") LocalDate birthdate2) {
        return sponsorService.findAllSponsorsWithBirthdateBetween(birthdate1, birthdate2);
    }

    @GetMapping("/getSponsorsByConnectorId/{connectorId}")
    public List<Sponsor> getSponsorsByConnectorId(@PathVariable Integer connectorId) {
        return sponsorService.findAllSponsorsWithConnectorId(connectorId);
    }

    @GetMapping("/requestForMoreOrphans/{sponsorId}")
    public void requestForMoreOrphans(@PathVariable Integer sponsorId) {
        sponsorService.requestForMoreOrphans(sponsorId);
    }

}
