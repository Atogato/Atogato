package portfolio.backend.authentication.api.controller.user;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import portfolio.backend.authentication.api.entity.user.User;
import portfolio.backend.authentication.api.service.UserService;
import portfolio.backend.authentication.common.ApiResponse;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Api(value = "user")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ApiResponse getUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userService.getUser(principal.getUsername());

        return ApiResponse.success("user", user);
    }
}
