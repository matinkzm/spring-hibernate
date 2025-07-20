package com.mysoftwareproject.login;

import com.mysoftwareproject.manager.Manager;
import com.mysoftwareproject.manager.ManagerRepository;
import com.mysoftwareproject.orphan.OrphanRepository;
import com.mysoftwareproject.sponsor.Sponsor;
import com.mysoftwareproject.sponsor.SponsorRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    private final ManagerRepository managerRepository;

    private final SponsorRepository sponsorRepository;

    public LoginService(ManagerRepository managerRepository, SponsorRepository sponsorRepository) {
        this.managerRepository = managerRepository;
        this.sponsorRepository = sponsorRepository;
    }

    public Object authenticateUser(String identifier, String password) throws Exception {
        // Check across entities
        Optional<?> user = findUserByIdentifier(identifier);

        if (user.isPresent()) {
            Object entity = user.get();

            // Check if password matches (assuming each entity has `getPassword`)
            if (passwordMatches(password, entity)) {
                return entity; // return user details on successful authentication
            }
        }
        // If no user was found or password does not match
        throw new Exception("Invalid identifier or password");
    }

    private Optional<?> findUserByIdentifier(String identifier) {
        // Try to find a Manager by identifier
        Optional<Manager> manager = managerRepository.findByUsername(identifier)
                .or(() -> managerRepository.findByEmail(identifier))
                .or(() -> managerRepository.findByPhoneNumber(identifier));
        if (manager.isPresent())
            return manager;


        // Try to find a Sponsor by identifier
        return sponsorRepository.findByUsername(identifier)
                .or(() -> sponsorRepository.findByEmail(identifier))
                .or(() -> sponsorRepository.findByPhoneNumber(identifier));
    }

    private boolean passwordMatches(String rawPassword, Object entity) {
        String hashedPassword;

        if (entity instanceof Manager) {
            hashedPassword = ((Manager) entity).getPassword();
        }
        else if (entity instanceof Sponsor) {
            hashedPassword = ((Sponsor) entity).getPassword();
        }
        else {
            return false;
        }

        return rawPassword.equals(hashedPassword);
    }
}
