package portfolio.backend.api.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;
import portfolio.backend.api.artist.entity.ArtistFavorite;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)

    @JsonIgnore
    private List<ProjectComment> comments = new ArrayList<>();

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProjectImages> projectImages;

    @Column(nullable=false)
    private Integer liked = 0;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project", orphanRemoval = true)
    @JsonIgnore
    private List<ProjectFavorite> favorites = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<ProjectApplication> applications = new ArrayList<>();

    @Column(columnDefinition = "TEXT")
    private String description;

    @ElementCollection
    private Set<String> participantArtistIds = new HashSet<>();

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

    public Set<String> getParticipantArtistIds() {
        return participantArtistIds;
    }

    public void setParticipantArtistIds(Set<String> participantArtistIds) {
        this.participantArtistIds = participantArtistIds;
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

    public Set<ProjectImages> getProjectImages() {
        return projectImages;
    }

    public void setProjectImages(Set<ProjectImages> projectImages) {
        this.projectImages = projectImages;
    }


    public Boolean getSwipeAlgorithm() {
        return swipeAlgorithm;
    }

    public void setSwipeAlgorithm(Boolean swipeAlgorithm) {
        this.swipeAlgorithm = swipeAlgorithm;
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

    public List<ProjectComment> getComments() {
        return comments;
    }

    public void setComments(List<ProjectComment> comments) {
        this.comments = comments;
    }

    public List<ProjectFavorite> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<ProjectFavorite> favorites) {
        this.favorites = favorites;
    }

    public List<ProjectApplication> getApplications() {
        return applications;
    }

    public void setApplications(List<ProjectApplication> applications) {
        this.applications = applications;
    }
}
