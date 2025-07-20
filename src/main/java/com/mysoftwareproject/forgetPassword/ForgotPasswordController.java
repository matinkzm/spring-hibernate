package com.mysoftwareproject.forgetPassword;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/forgotPassword")
public class ForgotPasswordController {

    private final ForgotPasswordService forgotPasswordService;

    public ForgotPasswordController(ForgotPasswordService forgotPasswordService) {
        this.forgotPasswordService = forgotPasswordService;
    }

    @PostMapping("/resetCode/{email}")
    public String forgotPassword(@PathVariable String email) {
        forgotPasswordService.sendResetCode(email);
        return "Reset code sent to email.";
    }

    @PostMapping("/verifyResetCode/{email}/{code}")
    public Boolean verifyResetCode(@PathVariable String email, @PathVariable String code) {
        if (forgotPasswordService.verifyResetCode(email, code)) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    @PostMapping("/resetPassword/{email}/{newPassword}")
    public void resetPassword(@PathVariable String email, @PathVariable String newPassword) {
        forgotPasswordService.resetPassword(email, newPassword);
    }
}
