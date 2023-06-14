package portfolio.backend.api.auth.entity;

import lombok.Data;
import portfolio.backend.api.auth.constant.USER_TYPE;

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
    private String user_id; //창작자 고유 id, uq

    private String password;

    private String first_name;

    private String last_name;

    private String artist_type; //15가지 option 존재

    @Enumerated(EnumType.STRING)
    private USER_TYPE user_type; //권한 - user, admin

}
