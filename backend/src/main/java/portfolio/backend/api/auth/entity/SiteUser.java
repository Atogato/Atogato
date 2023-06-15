package portfolio.backend.api.auth.entity;

import lombok.Data;
import portfolio.backend.api.auth.constant.UserType;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Data
@Entity
public class SiteUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @Email
    private String username; //login할 때 id, uq

    @Column(unique = true)
    private String userId; //창작자 고유 id, uq

    private String password;

    private String artistType; //15가지 option 존재

    @Enumerated(EnumType.STRING)
    private UserType userType; //권한 - user, admin

}
