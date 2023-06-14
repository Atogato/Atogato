package portfolio.backend.api.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import portfolio.backend.api.auth.Exception.DataNotFoundException;
import portfolio.backend.api.auth.constant.USER_TYPE;
import portfolio.backend.api.auth.entity.SiteUser;
import portfolio.backend.api.auth.repository.UserRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteUser create(String username, String user_id, String password,
                           String first_name, String last_name, String artist_type) {
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setUser_id(user_id);

        //BCrypt hashing 함수를 사용해서 비밀번호를 암호화한다.
        //PasswordEncoder는 BCryptPasswordEncoder의 인터페이스이다.
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // 객체를 직접 new로 생성

        user.setPassword(passwordEncoder.encode(password)); //빈으로 등록한 PasswordEncoder 객체를 주입받아 사용
        user.setFirst_name(first_name);
        user.setLast_name(last_name);
        user.setArtist_type(artist_type);
        user.setUser_type(USER_TYPE.USER);
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
