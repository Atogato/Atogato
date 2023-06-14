package portfolio.backend.api.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import portfolio.backend.api.auth.entity.SiteUser;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<SiteUser, Long> {

    //username(username=이메일)으로 회원 검색
    Optional<SiteUser> findByUsername(String username);

    //회원정보 모두를 출력
    List<SiteUser> findAll();
}
