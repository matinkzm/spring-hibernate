package com.mysoftwareproject.forgetPassword;
import com.mysoftwareproject.exception.NotFoundException;
import com.mysoftwareproject.manager.Manager;
import com.mysoftwareproject.manager.ManagerRepository;
import com.mysoftwareproject.sponsor.Sponsor;
import com.mysoftwareproject.sponsor.SponsorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@Service
public class ForgotPasswordService {

    private final SponsorRepository sponsorRepository;

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private ManagerRepository managerRepository;

    public ForgotPasswordService(SponsorRepository sponsorRepository) {
        this.sponsorRepository = sponsorRepository;
    }

    public static String generate6DigitCode() {
        SecureRandom random = new SecureRandom();
        int code = 100000 + random.nextInt(900000); // Generates a random number between 100000 and 999999
        return String.valueOf(code);
    }


    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    private Map<String, String> resetCodes = new HashMap<>();

    public void sendResetCode(String email) {
        // Check if the email exists
        Sponsor sponsor = sponsorRepository.findByEmail(email).orElse(null);
        Manager manager = managerRepository.findByEmail(email).orElse(null);
        if (manager == null && sponsor == null) {
            throw new NotFoundException("no one found");
        }

        // Generate a 6-digit reset code
        String resetCode = generate6DigitCode();

        // Store the code for validation later
        resetCodes.put(email, resetCode);

        // Send the code via email
        String subject = "Password Reset Request";
        String body = "Your password reset code is: " + resetCode;

        sendEmail(email, subject, body);
    }

    public boolean verifyResetCode(String email, String code) {
        return resetCodes.containsKey(email) && resetCodes.get(email).equals(code);
    }

    public void resetPassword(String email, String newPassword) {
        Sponsor sponsor = sponsorRepository.findByEmail(email).orElse(null);
        Manager manager = managerRepository.findByEmail(email).orElse(null);
        if (manager != null){
            manager.setPassword(newPassword);
            managerRepository.save(manager);
        }
        if (sponsor != null){
            sponsor.setPassword(newPassword);
            sponsorRepository.save(sponsor);
        }
    }

}
