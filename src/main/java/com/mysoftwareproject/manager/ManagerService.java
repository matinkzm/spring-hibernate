package com.mysoftwareproject.manager;

import com.mysoftwareproject.exception.NotFoundException;
import com.mysoftwareproject.orphan.OrphanRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
public class ManagerService {

    private final ManagerRepository managerRepository;

    public ManagerService(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    public Manager createManager(ManagerDto managerDto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(managerDto.getDateOfBirth(), formatter);
        Integer age = Period.between(date,LocalDate.now()).getYears();
        Manager manager = new Manager(managerDto.getName(), managerDto.getLastName(), managerDto.getNationalCode(), managerDto.getEmail(), managerDto.getPhoneNumber(), date, managerDto.getGender(), managerDto.getUsername(), managerDto.getPassword(), age);
        return managerRepository.save(manager);
    }

    public Manager getManagerById(Integer id) {
        return managerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Manager with id: "+ id +" not found"));
    }

    public List<Manager> getAllManagers() {
        return managerRepository.findAll();
    }

    public Manager updateManager(Integer id ,ManagerDto managerDto) {
        Manager foundManager = managerRepository.findById(id).orElse(null);
        if(foundManager != null) {
            if (managerDto.getName() != null){
                foundManager.setName(managerDto.getName());
            }
            if (managerDto.getLastName() != null){
                foundManager.setLastName(managerDto.getLastName());
            }
            if (managerDto.getNationalCode() != null){
                foundManager.setNationalCode(managerDto.getNationalCode());
            }
            if (managerDto.getEmail() != null){
                foundManager.setEmail(managerDto.getEmail());
            }
            if (managerDto.getPhoneNumber() != null){
                foundManager.setPhoneNumber(managerDto.getPhoneNumber());
            }
            if (managerDto.getDateOfBirth() != null){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate date = LocalDate.parse(managerDto.getDateOfBirth(), formatter);
                foundManager.setDateOfBirth(date);
                foundManager.setAge(Period.between(foundManager.getDateOfBirth(),LocalDate.now()).getYears());
            }
            if (managerDto.getGender() != null){
                foundManager.setGender(managerDto.getGender());
            }
            if (managerDto.getActive() != null){
                foundManager.setActive(managerDto.getActive());
            }
            return managerRepository.save(foundManager);
        }
        else
            throw new NotFoundException("Manager with id: "+ id +" not found");
    }

//    public void deleteManager(Integer id) {
//        managerRepository.deleteById(id);
//    }



    public Manager getManagerByNationalCode(String nationalCode) {
        return managerRepository.findByNationalCode(nationalCode);
    }




}
