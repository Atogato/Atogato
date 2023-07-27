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

    @Column(nullable=false)
    private Long projectId;

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

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
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
