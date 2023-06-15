package portfolio.backend.api.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import portfolio.backend.api.auth.Exception.DataNotFoundException;
import portfolio.backend.api.auth.constant.UserType;
import portfolio.backend.api.auth.entity.SiteUser;
import portfolio.backend.api.auth.repository.UserRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteUser create(String username, String userId,
                           String password, String artistType) {
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setUserId(userId);

        //BCrypt hashing 함수를 사용해서 비밀번호를 암호화한다.
        //PasswordEncoder는 BCryptPasswordEncoder의 인터페이스이다.
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // 객체를 직접 new로 생성

        user.setPassword(passwordEncoder.encode(password)); //빈으로 등록한 PasswordEncoder 객체를 주입받아 사용
        user.setArtistType(artistType);
        user.setUserType(UserType.USER);
        this.userRepository.save(user);
        return user;
    }

    //SiteUser 서비스를 통해 SiteUser를 조회할 수 있는 getUser 메서드
    public SiteUser getUser(String username) {
        Optional<SiteUser> siteUser = this.userRepository.findByUsername(username);
        if (siteUser.isPresent()) {
            return siteUser.get();
        } else {
            throw new DataNotFoundException("siteuser not found");
        }
    }

}
