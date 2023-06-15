package portfolio.backend.api.project.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Project {

    // PK
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

    @Column(nullable=false)
    private String creatorArtCategory;

    @Column(nullable=true)
    private String location;

    @Column(nullable=false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate deadline;

    @Column(nullable=false)
    private Long requiredPeople;

    @Column(nullable=false)
    private String requiredCategory;
    private Boolean swipeAlgorithm;

    @Column(nullable=true)
    private String image;
    private Long liked;

    @Column(columnDefinition = "TEXT")
    private String description;
    private Long userId;

    private Long participantId;

    // Getters & setters...
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

    public String getCreatorArtCategory() {

        return creatorArtCategory;
    }

    public void setCreatorArtCategory(String creatorArtCategory) {

        this.creatorArtCategory = creatorArtCategory;
    }


    public String getLocation() {

        return location;
    }

    public void setLocation(String location) {

        this.location = location;
    }

    public LocalDate getDeadline() {

        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public Long getRequiredPeople() {
        return requiredPeople;
    }

    public void setRequiredPeople(Long requiredPeople) {
        this.requiredPeople = requiredPeople;
    }

    public String getRequiredCategory() {
        return requiredCategory;
    }

    public void setRequiredCategory(String requiredCategory) {
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


    public Long getLiked() {
        return liked;
    }

    public void setLiked(Long liked) {
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


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    public Long getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Long participantId) {
        this.participantId = participantId;
    }

}
