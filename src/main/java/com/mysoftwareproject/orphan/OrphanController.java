package com.mysoftwareproject.orphan;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/orphan")
public class OrphanController {

    private final OrphanService orphanService;

    public OrphanController(OrphanService orphanService) {
        this.orphanService = orphanService;
    }

    @GetMapping // correct
    public List<Orphan> getAllOrphans() {
        return orphanService.getAllOrphans();
    }

    @PostMapping // correct
    public Orphan createOrphan(@RequestBody OrphanDto orphanDto) {
        return orphanService.createOrphan(orphanDto);
    }

    @PutMapping("/{orphanId}") // correct
    public Orphan updateOrphan(@PathVariable("orphanId") Integer orphanId,@RequestBody OrphanDto orphanDto) {
        return orphanService.updateOrphan(orphanId, orphanDto);
    }

    @GetMapping("/getOrphanWithOrphanId/{orphanId}") //correct
    public Orphan findOrphanWithOrphanId(@PathVariable("orphanId") Integer orphanId) {
        return orphanService.getOrphanById(orphanId);
    }

    @GetMapping("/getOrphansByDateOfBirthGreaterThan/{birthDate}") // correct  // input date: 1381-06-29
    public List<Orphan> getOrphansByDateOfBirthGreaterThanEqual(@PathVariable("birthDate") LocalDate birthDate) {
        return orphanService.getOrphanByDateOfBirthGreaterThanEqual(birthDate);
    }

    @GetMapping("/getOrphansByDateOfBirthLessThanEqual/{birthDate}") // correct  // input date: 1381-06-29
    public List<Orphan> getOrphansByDateOfBirthLessThanEqual(@PathVariable("birthDate") LocalDate birthDate) {
        return orphanService.getOrphanByDateOfBirthLessThanEqual(birthDate);
    }

    @GetMapping("/getOrphansByDateOfBirthBetween/{startDate}/{endDate}") // correct  // input date: 1381-06-29
    public List<Orphan> getOrphansByDateOfBirthBetween(@PathVariable("startDate") LocalDate startDate, @PathVariable("endDate") LocalDate endDate) {
        return orphanService.getOrphanByDateOfBirthBetween(startDate, endDate);
    }

    @GetMapping("/getOrphanByEmail/{email}") // correct
    public Orphan getOrphanByEmail(@PathVariable("email") String email) {
        return orphanService.getOrphanByEmail(email);
    }

    @GetMapping("/getOrphansByName/{name}") // correct
    public List<Orphan> getOrphansByName(@PathVariable("name") String name) {
        return orphanService.getOrphansByName(name);
    }

    @GetMapping("/getOrphansByLastname/{lastname}") // correct
    public List<Orphan> getOrphansByLastName(@PathVariable("lastname") String Lastname) {
        return orphanService.getOrphansByLastName(Lastname);
    }

    @GetMapping("/getOrphanByNationalCode/{nationalCode}") //correct
    public Orphan getOrphanByNationalCode(@PathVariable("nationalCode") String nationalCode) {
        return orphanService.getOrphanByNationalCode(nationalCode);
    }

    @GetMapping("/getAllOrphansWithNoSponsor") // correct
    public List<Orphan> getAllOrphansWithNoSponsor() {
        return orphanService.getAllOrphansWithSponsorIdEqualsToNull();
    }

    @GetMapping("/getOrphansWithSponsorName/{sponsorName}") // correct
    public List<Orphan> getOrphansWithSponsorName(@PathVariable String sponsorName) {
        return orphanService.findAllOrphansBySponsorName(sponsorName);
    }

    @GetMapping("/getAllOrphansBySponsorId/{sponsorId}") // correct
    public List<Orphan> getAllOrphansBySponsorId(@PathVariable Integer sponsorId) {
        return orphanService.getAllOrphansBySponsorId(sponsorId);
    }
}
