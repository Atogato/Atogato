package portfolio.backend.api.auth.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import portfolio.backend.api.auth.constant.UserType;
import portfolio.backend.api.auth.entity.SiteUser;
import portfolio.backend.api.auth.repository.UserRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Test
    void signTest() {
        SiteUser user = new SiteUser();
        user.setUsername("longlee@naver.com");
        user.setUserId("성구랍니다");
        user.setPassword("12345678");
        user.setArtistType("악기연주");
        user.setUserType(UserType.USER);
        this.userRepository.save(user); //회원가입 저장

        //회원 조회(데이터 조회)
        List<SiteUser> all = this.userRepository.findAll();
        assertEquals(1, all.size());

        SiteUser s = all.get(0);
        assertEquals("longlee@naver.com", s.getUsername());
        assertEquals("성구랍니다", s.getUserId());
        assertEquals("12345678", s.getPassword());
        assertEquals("악기연주", s.getArtistType());

    }


}