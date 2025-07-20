package com.mysoftwareproject.orphan;

import com.mysoftwareproject.enums.ActiveType;
import com.mysoftwareproject.exception.NotActiveException;
import com.mysoftwareproject.exception.NotFoundException;
import com.mysoftwareproject.manager.Manager;
import com.mysoftwareproject.manager.ManagerService;
import com.mysoftwareproject.notification.NotificationService;
import com.mysoftwareproject.sponsor.Sponsor;
import com.mysoftwareproject.sponsor.SponsorRepository;
import com.mysoftwareproject.sponsor.SponsorService;
import com.mysoftwareproject.sponsorOrphanHistory.SponsorOrphanHistory;
import com.mysoftwareproject.sponsorOrphanHistory.SponsorOrphanHistoryRepository;
import com.mysoftwareproject.sponsorOrphanHistory.SponsorOrphanHistoryService;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrphanService {
    private final OrphanRepository orphanRepository;
    private final SponsorService sponsorService;
    private final NotificationService notificationService;
    private final ManagerService managerService;
    private final SponsorRepository sponsorRepository;
    private final SponsorOrphanHistoryService sponsorOrphanHistoryService;
    private final SponsorOrphanHistoryRepository sponsorOrphanHistoryRepository;

    public OrphanService(OrphanRepository orphanRepository, @Lazy SponsorService sponsorService, NotificationService notificationService, ManagerService managerService, SponsorRepository sponsorRepository, SponsorOrphanHistoryService sponsorOrphanHistoryService, SponsorOrphanHistoryRepository sponsorOrphanHistoryRepository) {
        this.orphanRepository = orphanRepository;
        this.sponsorService = sponsorService;
        this.notificationService = notificationService;
        this.managerService = managerService;
        this.sponsorRepository = sponsorRepository;
        this.sponsorOrphanHistoryService = sponsorOrphanHistoryService;
        this.sponsorOrphanHistoryRepository = sponsorOrphanHistoryRepository;
    }

    public Orphan getOrphanById(Integer id) {
        return orphanRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Orphan with id: "+ id +" not found"));
    }

    public List<Orphan> getAllOrphans() {
        return orphanRepository.findAll();
    }

    public Orphan createOrphan(OrphanDto orphanDto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate birthDate = LocalDate.parse(orphanDto.getDateOfBirth(), formatter);
        Orphan orphan = new Orphan(orphanDto.getName(),orphanDto.getLastName(),orphanDto.getNationalCode(), orphanDto.getEmail(), orphanDto.getPhoneNumber(),birthDate,orphanDto.getGender(), orphanDto.getAddress(),orphanDto.getFathersName(), orphanDto.getMothersName(), orphanDto.getTaken(), orphanDto.getAccountNumber(), orphanDto.getSheba(), orphanDto.getSponsorId());
        orphan.setAge(Period.between(orphan.getDateOfBirth(),LocalDate.now()).getYears());
        return orphanRepository.save(orphan);
    }

    public Orphan updateOrphan(Integer id ,OrphanDto orphanDto) {
        Orphan foundOrphan = orphanRepository.findById(id).orElse(null);
        Boolean flag = Boolean.FALSE;
        if(foundOrphan != null) {
            if (orphanDto.getName() != null){
                foundOrphan.setName(orphanDto.getName());
            }
            if (orphanDto.getLastName() != null){
                foundOrphan.setLastName(orphanDto.getLastName());
            }
            if (orphanDto.getNationalCode() != null){
                foundOrphan.setNationalCode(orphanDto.getNationalCode());
            }
            if (orphanDto.getEmail() != null){
                foundOrphan.setEmail(orphanDto.getEmail());
            }
            if (orphanDto.getPhoneNumber() != null){
                foundOrphan.setPhoneNumber(orphanDto.getPhoneNumber());
            }
            if (orphanDto.getDateOfBirth() != null){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate birthDate = LocalDate.parse(orphanDto.getDateOfBirth(), formatter);
                foundOrphan.setDateOfBirth(birthDate);
                foundOrphan.setAge(Period.between(foundOrphan.getDateOfBirth(),LocalDate.now()).getYears());
            }
            if (orphanDto.getGender() != null){
                foundOrphan.setGender(orphanDto.getGender());
            }
            if (orphanDto.getFathersName() != null){
                foundOrphan.setFathersName(orphanDto.getFathersName());
            }
            if (orphanDto.getMothersName() != null){
                foundOrphan.setMothersName(orphanDto.getMothersName());
            }
            if (orphanDto.getSponsorId() != null){
                // send -1 if you want to remove sponsor
                if (orphanDto.getSponsorId() == -1) {
                    Integer sponsorOrphanHistoryId = foundOrphan.getSponsorOrphanHistoryId();
                    foundOrphan.setTaken(false);
                    SponsorOrphanHistory sponsorOrphanHistory = sponsorOrphanHistoryService.findSponsorOrphanHistoryById(sponsorOrphanHistoryId);
                    sponsorOrphanHistory.setEndDate(LocalDate.now());
                    // sponsorOrphanHistoryService.updateSponsorOrphanHistory(sponsorOrphanHistoryId, sponsorOrphanHistory);
                    sponsorOrphanHistoryRepository.save(sponsorOrphanHistory);
                    foundOrphan.setWithoutSponsorStartDate(LocalDate.now());
                }
                boolean tempFlag = false;
                if (foundOrphan.getSponsorId() == null){
                    tempFlag = true;
                    foundOrphan.setSponsorId(orphanDto.getSponsorId());
                    foundOrphan.setTaken(true);
                    notificationService.createNotification(orphanDto.getSponsorId(), "you are new sponsor for orphan with id :" +foundOrphan.getId());
                    SponsorOrphanHistory newSponsorOrphanHistory = new SponsorOrphanHistory(orphanDto.getSponsorId(), id, LocalDate.now());
                    sponsorOrphanHistoryRepository.save(newSponsorOrphanHistory);
                    foundOrphan.setSponsorOrphanHistoryId(newSponsorOrphanHistory.getId());
                }
                if (orphanDto.getSponsorId() != -1){
                    Sponsor sponsor = sponsorRepository.findById(orphanDto.getSponsorId()).orElse(null);
                    if (sponsor.getActive().equals("false"))
                        throw new NotActiveException("Sponsor with id " + sponsor.getId() + " is not active");
                    foundOrphan.setSponsorId(orphanDto.getSponsorId());
                    notificationService.createNotification(foundOrphan.getSponsorId(), "You are no longer "+ foundOrphan.getId()+"'s sponsor");
                    if (sponsor == null)
                        throw new NotFoundException("Sponsor with id " + sponsor.getId() + " not found");
                    if (orphanDto.getSponsorId() != -1 && !tempFlag) {
                        Integer sponsorOrphanHistoryId = foundOrphan.getSponsorOrphanHistoryId();
                        notificationService.createNotification(orphanDto.getSponsorId(), "You are new sponsor for orphan with id " + foundOrphan.getId());
                        SponsorOrphanHistory sponsorOrphanHistory = sponsorOrphanHistoryService.findSponsorOrphanHistoryById(sponsorOrphanHistoryId);
                        sponsorOrphanHistory.setEndDate(LocalDate.now());
                        SponsorOrphanHistory newSponsorOrphanHistory = new SponsorOrphanHistory(orphanDto.getSponsorId(), id, LocalDate.now());
                        sponsorOrphanHistoryRepository.save(newSponsorOrphanHistory);
                        foundOrphan.setSponsorOrphanHistoryId(newSponsorOrphanHistory.getId());
                    }
                    flag = Boolean.TRUE;
                }

            }
            if (orphanDto.getActive() != null){
                foundOrphan.setActive(orphanDto.getActive());
            }
            if (flag.equals(Boolean.TRUE))
                notificationService.createNotification(orphanDto.getSponsorId(), "Orphan with id " + orphanDto.getSponsorId() + " updated");
            else
                notificationService.createNotification(foundOrphan.getSponsorId(), "Orphan with id " + orphanDto.getSponsorId() + " updated");
            return orphanRepository.save(foundOrphan);
        }
        else
            throw new NotFoundException("Orphan with id: "+ id +" not found");
    }


    public List<Orphan> getOrphanByDateOfBirthGreaterThanEqual(LocalDate date) {
        return orphanRepository.findAllByDateOfBirthGreaterThanEqual(date);
    }

    public List<Orphan> getOrphanByDateOfBirthLessThanEqual(LocalDate date) {
        return orphanRepository.findAllByDateOfBirthLessThanEqual(date);
    }

    public List<Orphan> getOrphanByDateOfBirthBetween(LocalDate start, LocalDate end) {
        return orphanRepository.findAllByDateOfBirthBetween(start, end);
    }

    public Orphan getOrphanByEmail(String email) {
        return orphanRepository.findByEmail(email);
    }

    public List<Orphan> getOrphansByName(String name) {
        return orphanRepository.findAllByNameContains(name);
    }

    public List<Orphan> getOrphansByLastName(String Lastname) {
        return orphanRepository.findAllByLastNameContains(Lastname);
    }

    public Orphan getOrphanByNationalCode(String nationalCode) {
        return orphanRepository.findByNationalCode(nationalCode);
    }

    @Scheduled(fixedRate = 300000) // Runs every 5 minutes
    public void checkDateAndSendNotification() {
        LocalDate today = LocalDate.now();

        getAllOrphans().forEach(orphan -> {
            LocalDate targetDate = orphan.getWithoutSponsorStartDate();

            // Calculate the days difference
            long daysDifference = ChronoUnit.DAYS.between(targetDate, today);

            if (orphan.getSponsorId().equals(null) && daysDifference >= 10) {
                List<Manager> myList = managerService.getAllManagers();
                for (Manager manager : myList) {
                    notificationService.createNotification(manager.getId(), "orphan with id "+ orphan.getId()+" has no sponsor for 10 days or more");
                }
            }
        });
    }

    public List<Orphan> getAllOrphansWithSponsorIdEqualsToNull(){
        return orphanRepository.findAllBySponsorIdIsNull();
    }

    public List<Orphan> findAllOrphansBySponsorName(String sponsorName) {
        List<Sponsor> sponsors = sponsorService.findSponsorByName(sponsorName);
        List<Orphan> orphans = getAllOrphans();
        List<Orphan> foundOrphans = new ArrayList<>();
        for (Sponsor sponsor : sponsors) {
            for (Orphan orphan : orphans) {
                if(sponsor.getId().equals(orphan.getSponsorId())){
                    foundOrphans.add(orphan);
                }
            }
        }
        if (foundOrphans.isEmpty()) {
            throw new NotFoundException("orphan with sponsor name: "+ sponsorName + " not found");
        }
        return foundOrphans;
    }

    public void removeSponsor(Integer orphanId){
        Orphan foundOrphan = orphanRepository.findById(orphanId).orElse(null);
        if (foundOrphan != null) {
            foundOrphan.setSponsorId(null);
            Integer sponsorOrphanHistoryId = foundOrphan.getSponsorOrphanHistoryId();
            SponsorOrphanHistory sponsorOrphanHistory = sponsorOrphanHistoryService.findSponsorOrphanHistoryById(sponsorOrphanHistoryId);
            sponsorOrphanHistory.setEndDate(LocalDate.now());
            sponsorOrphanHistoryService.updateSponsorOrphanHistory(sponsorOrphanHistoryId, sponsorOrphanHistory);
            foundOrphan.setWithoutSponsorStartDate(LocalDate.now());
        }
    }

    public List<Orphan> getAllOrphansBySponsorId(Integer sponsorId) {
        return orphanRepository.findAllBySponsorId(sponsorId);
    }

}
