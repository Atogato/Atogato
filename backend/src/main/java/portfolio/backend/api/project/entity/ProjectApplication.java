package portfolio.backend.api.project.entity;

import javax.persistence.*;

@Entity
@Table(name="project_application")
public class ProjectApplication {

    public enum ApplicationStatus{
        WAITING, ACCEPTED, REJECTED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "projectId")
    private Project project;

    @Column(nullable=false)
    private String appliedArtistId;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private ApplicationStatus applicationStatus = ApplicationStatus.WAITING;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getAppliedArtistId() {
        return appliedArtistId;
    }

    public void setAppliedArtistId(String appliedArtistId) {
        this.appliedArtistId = appliedArtistId;
    }

    public ApplicationStatus getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(ApplicationStatus applicationStatus) {
        this.applicationStatus = applicationStatus;
    }
}
