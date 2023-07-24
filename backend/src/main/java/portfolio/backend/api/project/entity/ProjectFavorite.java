package portfolio.backend.api.project.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import portfolio.backend.authentication.api.entity.user.User;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="project_favorites")
public class ProjectFavorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_favorites_id")
    private Long id;

    @Column(name = "userId")
    private String userId;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projectId")
    private Project project;


    @Builder
    public ProjectFavorite(String userId, Project project){
        this.userId = userId;
        this.project = project;
    }

}