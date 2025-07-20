package com.mysoftwareproject.sponsor;

import com.mysoftwareproject.activeHistory.ActiveHistory;
import com.mysoftwareproject.activeHistory.ActiveHistoryRepository;
import com.mysoftwareproject.activeHistory.ActiveHistoryService;
import com.mysoftwareproject.enums.ActiveType;
import com.mysoftwareproject.exception.NotFoundException;
import com.mysoftwareproject.manager.Manager;
import com.mysoftwareproject.manager.ManagerService;
import com.mysoftwareproject.notification.NotificationService;
import com.mysoftwareproject.orphan.Orphan;
import com.mysoftwareproject.orphan.OrphanService;
import com.mysoftwareproject.sponsorOrphanHistory.SponsorOrphanHistory;
import com.mysoftwareproject.sponsorOrphanHistory.SponsorOrphanHistoryService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

@Service
public class SponsorService {

    private final SponsorRepository sponsorRepository;
    private final OrphanService orphanService;
    private final ActiveHistoryRepository activeHistoryRepository;
    private final NotificationService notificationService;
    private final ManagerService managerService;
    private final SponsorOrphanHistoryService sponsorOrphanHistoryService;

    public SponsorService(SponsorRepository sponsorRepository, OrphanService orphanService, ActiveHistoryRepository activeHistoryRepository, NotificationService notificationService, ManagerService managerService, SponsorOrphanHistoryService sponsorOrphanHistoryService) {
        this.sponsorRepository = sponsorRepository;
        this.orphanService = orphanService;
        this.activeHistoryRepository = activeHistoryRepository;
        this.notificationService = notificationService;
        this.managerService = managerService;
        this.sponsorOrphanHistoryService = sponsorOrphanHistoryService;
    }
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Sponsor createSponsor(SponsorDto sponsorDto) {

        LocalDate date = LocalDate.parse(sponsorDto.getDateOfBirth(), formatter);

        Sponsor sponsor = new Sponsor(sponsorDto.getName(),sponsorDto.getLastName(),sponsorDto.getNationalCode(),sponsorDto.getEmail(), sponsorDto.getPhoneNumber(), date, sponsorDto.getAddress(), sponsorDto.getGender(), sponsorDto.getConnectorId(), "true", sponsorDto.getUsername(), sponsorDto.getPassword());

        String tempDate = String.valueOf(LocalDate.now());
        LocalDate tempDate2 = LocalDate.parse(tempDate, formatter1);
        sponsor.setCreatedAt(tempDate2);
        sponsorRepository.save(sponsor);
        ActiveHistory activeHistory = new ActiveHistory(sponsor.getId(), tempDate2, "true");
        activeHistoryRepository.save(activeHistory);
        sponsor.setActiveHistoryId(activeHistory.getId());
        sponsor.setActiveHistoryId(activeHistory.getId());
        sponsorRepository.save(sponsor);
        Sponsor tempSponsor = findSponsorByNationalCode(sponsorDto.getNationalCode());
        notificationService.createNotification(tempSponsor.getId(), "An account has been created for you");
        return tempSponsor;
    }

    public Sponsor updateSponsor(Integer id, SponsorDto sponsorDto) {
        Sponsor foundSponsor = sponsorRepository.findById(id).orElse(null);

        if (foundSponsor != null) {
            if (sponsorDto.getName() != null) {
                foundSponsor.setName(sponsorDto.getName());
            }
            if (sponsorDto.getLastName() != null){
                foundSponsor.setLastName(sponsorDto.getLastName());
            }
            if (sponsorDto.getNationalCode() != null){
                foundSponsor.setNationalCode(sponsorDto.getNationalCode());
            }
            if (sponsorDto.getEmail() != null) {
                foundSponsor.setEmail(sponsorDto.getEmail());
            }
            if (sponsorDto.getPhoneNumber() != null){
                foundSponsor.setPhoneNumber(sponsorDto.getPhoneNumber());
            }
            if (sponsorDto.getDateOfBirth() != null){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate date = LocalDate.parse(sponsorDto.getDateOfBirth(), formatter);
                foundSponsor.setDateOfBirth(date);
            }
            if (sponsorDto.getAddress() != null) {
                foundSponsor.setAddress(sponsorDto.getAddress());
            }
            if (sponsorDto.getGender() != null){
                foundSponsor.setGender(sponsorDto.getGender());
            }
            if (sponsorDto.getConnectorId() != null){
                foundSponsor.setConnectorId(sponsorDto.getConnectorId());
            }
            if (sponsorDto.getUsername() != null){
                foundSponsor.setUsername(sponsorDto.getUsername());
            }
            if (sponsorDto.getPassword() != null){
                foundSponsor.setPassword(sponsorDto.getPassword());
            }
            if (sponsorDto.getActive() != null){
                foundSponsor.setActive(sponsorDto.getActive());
                if (Objects.equals(sponsorDto.getActive(), "false")){
                    ActiveHistory foundActiveHistory = activeHistoryRepository.findById(foundSponsor.getActiveHistoryId()).orElse(null);
                    if (foundActiveHistory != null){
                        foundActiveHistory.setIsActive("false");
                        String tempDate = String.valueOf(LocalDate.now());
                        LocalDate tempDate2 = LocalDate.parse(tempDate, formatter1);
                        foundActiveHistory.setEndDate(tempDate2);
                        activeHistoryRepository.save(foundActiveHistory);
                    }
                    List<Orphan> orphans = orphanService.getAllOrphans();
                    for (Orphan orphan : orphans){
                        if (orphan.getSponsorId().equals(foundSponsor.getId())){
                            orphanService.removeSponsor(orphan.getId());
                        }
                    }
                }
                if (Objects.equals(sponsorDto.getActive(), "true")){
                    String tempDate = String.valueOf(LocalDate.now());
                    LocalDate tempDate2 = LocalDate.parse(tempDate, formatter1);
                    ActiveHistory newActiveHistory = new ActiveHistory(foundSponsor.getId(), tempDate2, "true");
                    activeHistoryRepository.save(newActiveHistory);
                    foundSponsor.setActiveHistoryId(newActiveHistory.getId());
                }
            }
            return sponsorRepository.save(foundSponsor);
        }
        else
            throw new NotFoundException("Sponsor with id: "+ id +" not found");
    }

//    public void deleteSponsor(Integer id) {
//        sponsorRepository.deleteById(id);
//    }

    public Sponsor findSponsorById(Integer id) {
        return sponsorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Sponsor with id: "+ id +" not found"));
    }

    public List<Sponsor> findAllSponsors() {
        return sponsorRepository.findAll();
    }

    public Sponsor findSponsorByNationalCode(String nationalCode) {
        return sponsorRepository.findByNationalCode(nationalCode);
    }

    public List<Sponsor> findSponsorByName(String name) {
        return sponsorRepository.findAllByNameContains(name);
    }

    public List<Sponsor> findSponsorByLastName(String lastName) {
        return sponsorRepository.findAllByLastNameContains(lastName);
    }

    public List<Sponsor> findAllSponsorsWithBirthdateGreaterThanOrEqual(LocalDate birthdate) {
        return sponsorRepository.findAllByDateOfBirthGreaterThanEqual(birthdate);
    }

    public List<Sponsor> findAllSponsorsWithBirthdateLessThanOrEqual(LocalDate birthdate) {
        return sponsorRepository.findAllByDateOfBirthLessThanEqual(birthdate);
    }

    public List<Sponsor> findAllSponsorsWithBirthdateBetween(LocalDate birthdate1, LocalDate birthdate2) {
        return sponsorRepository.findAllByDateOfBirthBetween(birthdate1, birthdate2);
    }

    public List<Sponsor> findAllSponsorsWithConnectorId(Integer connectorId) {
        return sponsorRepository.findAllByConnectorId(connectorId);
    }

    public void requestForMoreOrphans(Integer id){
        List<Manager> managerList = managerService.getAllManagers();
        for (Manager manager : managerList) {
            notificationService.createNotification(manager.getId(), "Sponsor with id: " + id + " has requested for more orphans");
        }
    }

    @Scheduled(fixedRate = 300000) // Runs every 5 minutes
    public void checkDateAndSendNotification() {

        LocalDate today = LocalDate.now();
        List<SponsorOrphanHistory> sponsorOrphanHistoryList = sponsorOrphanHistoryService.getAllSponsorOrphanHistory();

        findAllSponsors().forEach(sponsor -> {
            boolean flag = false;
            LocalDate targetDate = sponsor.getCreatedAt();
            for (SponsorOrphanHistory sponsorHistory : sponsorOrphanHistoryList) {
                if (sponsorHistory.getSponsorId().equals(sponsor.getId())){
                    flag = true;
                    break;
                }
            }

            if (flag){
                return;
            }

            // Calculate the days difference
            long daysDifference = ChronoUnit.DAYS.between(targetDate, today);

            if (daysDifference >= 10) {
                List<Manager> myList = managerService.getAllManagers();
                for (Manager manager : myList) {
                    notificationService.createNotification(manager.getId(), "sponsor with id "+ sponsor.getId()+" has no orphan for 10 days or more");
                }
            }
        });
    }

//    public void assignOrphanToSponsor(Integer orphanId, Integer sponsorId){
//        Orphan orphan = orphanService.getOrphanById(orphanId);
//        orphan.setSponsorId(sponsorId);
//    }
}
