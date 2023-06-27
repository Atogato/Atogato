package portfolio.backend.api.auth.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import portfolio.backend.api.auth.constant.UserType;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Data
@Entity
@NoArgsConstructor
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

    private String provider;

    private String providerId;

    @Builder
    public SiteUser(Long id, String username, String userId, String password, String artistType, UserType userType) {
        this.id = id;
        this.username = username;
        this.userId = userId;
        this.password = password;
        this.artistType = artistType;
        this.userType = (userType != null) ? userType : UserType.USER; // Set 'user' as default if userType is null
        this.provider = provider;
        this.providerId = providerId;
    }
}
