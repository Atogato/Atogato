package portfolio.backend.authentication.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import portfolio.backend.authentication.api.repository.user.UserRepository;
import portfolio.backend.authentication.api.entity.user.User;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUser(String userId) {
        return userRepository.findByUserId(userId);
    }
}
