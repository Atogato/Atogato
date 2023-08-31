package portfolio.backend.authentication.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import portfolio.backend.authentication.api.entity.user.User;

@Service
public class UserContextService {
    private final UserService userService;

    public UserContextService(UserService userService) {
        this.userService = userService;
    }

    public User getCurrentUser(){
        org.springframework.security.core.userdetails.User principal =
                (org.springframework.security.core.userdetails.User) SecurityContextHolder
                        .getContext().getAuthentication().getPrincipal();
        return userService.getUser(principal.getUsername());
    }
}
