package portfolio.backend.api.auth.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import portfolio.backend.api.artist.entity.Artist;
import portfolio.backend.api.auth.constant.UserType;
import portfolio.backend.api.project.entity.Project;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "SiteUser")
public class SiteUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true)
    @Email
    private String username; //login할 때 id, uq

    private String password;

    @Enumerated(EnumType.STRING)
    private UserType userType; //권한 - user, admin

    private String provider;

    private String providerId;

    @OneToMany(mappedBy = "siteUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Project> project;

    @OneToOne(mappedBy = "siteUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Artist artist;


    @Builder
    public SiteUser(Long userId, String username, String password, UserType userType) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.userType = (userType != null) ? userType : UserType.USER; // Set 'user' as default if userType is null
        this.provider = provider;
        this.providerId = providerId;
    }
}
