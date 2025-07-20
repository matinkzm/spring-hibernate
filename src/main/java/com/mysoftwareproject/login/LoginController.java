package com.mysoftwareproject.login;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authentication")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login/{username}/{password}") //correct
    public Object login(@PathVariable String username, @PathVariable String password) {
        try {
            return loginService.authenticateUser(username, password);
        } catch (Exception e) {
            return "Invalid username or password";
        }
    }
}
