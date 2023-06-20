package portfolio.backend.api.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import portfolio.backend.api.auth.entity.UserCreateForm;
import portfolio.backend.api.auth.repository.UserRepository;
import portfolio.backend.api.auth.service.UserService;
import portfolio.backend.api.project.repository.ProjectRepository;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("/signup") //회원가입
    public String signup(UserCreateForm userCreateForm) {
        return "users/signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "users/signup_form";
        }

        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            //회원 가입시 비밀번호1과 비밀번호2가 동일한지를 검증하는 로직
            //만약 2개의 값이 일치하지 않을 경우에는 bindingResult.rejectValue를 사용하여 오류가 발생
            //bindingResult.rejectValue(필드명, 오류코드, 에러메시지), 오류코드: "passwordInCorrect"
            bindingResult.rejectValue("password2", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "users/signup_form";
        }

        try {
            userService.create(userCreateForm.getUsername(), userCreateForm.getUserId(),
                    userCreateForm.getPassword1(),  userCreateForm.getArtistType());
        } catch (DataIntegrityViolationException e) {
            //이메일 주소가 동일할 경우에는 DataIntegrityViolationException이 발생
            e.printStackTrace();
            bindingResult.rejectValue("username","signupFailed", "이미 등록된 이메일입니다.");
            return "users/signup_form";

        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "users/signup_form";
        }

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "users/login_form";
    }

}