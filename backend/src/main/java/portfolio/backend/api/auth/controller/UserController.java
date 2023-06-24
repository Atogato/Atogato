package portfolio.backend.api.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import portfolio.backend.api.auth.repository.UserRepository;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/auth")
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/login")
    public String login() {
        return "users/login_form";
    }

}