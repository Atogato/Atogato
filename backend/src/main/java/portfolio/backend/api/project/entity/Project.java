package portfolio.backend.api.project.entity;

import org.springframework.format.annotation.DateTimeFormat;
import portfolio.backend.authentication.api.entity.user.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="Project")
public class Project {

    public enum ProjectCategory{
        공연, 전시, 제작, 기획, 취미
    }

    public enum RequiredCategory{
        연기, 노래, 제작, 춤, 작곡
    }
    @Column(nullable = false)
    private String userId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;

    private Boolean ongoingStatus;

    @Column(nullable=false)
    private String remoteStatus;

    @Column(nullable=false)
    private String projectName;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate createdDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private ProjectCategory projectArtCategory;

    @Column(nullable=true)
    private String location;

    @Column(nullable=false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate projectDeadline;

    @Column(nullable=false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate applicationDeadline;

    @Column(nullable=false)
    private Long requiredPeople;

    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass=RequiredCategory.class)
    private List<RequiredCategory> requiredCategory;
    private Boolean swipeAlgorithm;

    @Lob
    private String image;

    @Column(nullable=false)
    private Integer liked = 0;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Long participantId;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {

        this.projectId = projectId;
    }

    public String getProjectName() {

        return projectName;
    }

    public void setProjectName(String projectName) {

        this.projectName = projectName;
    }

    public LocalDate getCreatedDate() {

        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {

        this.createdDate = createdDate;
    }

    public ProjectCategory getProjectArtCategory() {

        return projectArtCategory;
    }

    public void setProjectArtCategory(ProjectCategory projectArtCategory) {

        this.projectArtCategory = projectArtCategory;
    }


    public String getLocation() {

        return location;
    }

    public void setLocation(String location) {

        this.location = location;
    }

    public LocalDate getProjectDeadline() {

        return projectDeadline;
    }

    public void setProjectDeadline(LocalDate projectDeadline) {
        this.projectDeadline = projectDeadline;
    }

    public LocalDate getApplicationDeadline() {

        return applicationDeadline;
    }

    public void setApplicationDeadline(LocalDate applicationDeadline) {
        this.applicationDeadline = applicationDeadline;
    }


    public Long getRequiredPeople() {
        return requiredPeople;
    }

    public void setRequiredPeople(Long requiredPeople) {
        this.requiredPeople = requiredPeople;
    }

    public List<RequiredCategory> getRequiredCategory() {
        return requiredCategory;
    }

    public void setRequiredCategory(List<RequiredCategory> requiredCategory) {
        this.requiredCategory = requiredCategory;
    }

    public Boolean getSwipeAlgorithm() {
        return swipeAlgorithm;
    }

    public void setSwipeAlgorithm(Boolean swipeAlgorithm) {
        this.swipeAlgorithm = swipeAlgorithm;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public Integer getLiked() {
        return liked;
    }

    public void setLiked(Integer liked) {
        this.liked = liked;
    }

    public Boolean getOngoingStatus() {
        return ongoingStatus;
    }

    public void setOngoingStatus(Boolean ongoingStatus) {
        this.ongoingStatus = ongoingStatus;
    }

    public String getRemoteStatus() {
        return remoteStatus;
    }

    public void setRemoteStatus(String remoteStatus) {
        this.remoteStatus = remoteStatus;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public Long getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Long participantId) {
        this.participantId = participantId;
    }

}
