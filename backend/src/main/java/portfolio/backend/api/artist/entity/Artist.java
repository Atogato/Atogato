package portfolio.backend.api.artist.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Getter
@DynamicUpdate
@DynamicInsert
@Entity
@Table(name="artists")
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(hidden = true)
    private Long artistId;

    @Column(nullable = false, unique = true)
    private String userId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "artist", orphanRemoval = true)
    @JsonIgnore
    private List<ArtistFavorite> favorites = new ArrayList<>();

    @Column(nullable=false)
    private String artistName;

    @Column(nullable = true)
    private String location;

    @Column(nullable = false)
    private String selfIntroduction;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, columnDefinition = "integer default 0")
    private Integer liked;

    @Column(nullable=false)
    private String creatorArtCategory;

    @ElementCollection
    @CollectionTable(name = "artist_interest_categories", joinColumns = @JoinColumn(name = "artist_id"))
    @Column(name = "interest_category", nullable = false)
    private List<String> interestCategories = new ArrayList<>();

    @Column(nullable=true)
    private String snsLink;

    @Lob
    private String mainImage;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "artist", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ExtraImage> extraImages;

    @Lob
    private String portfolio;

    @Column(nullable=true)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthdate;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getArtistId() {
        return artistId;
    }

    public void setArtistId(Long artistId) {
        this.artistId = artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getLocation() {
        return location;
    }

    public String getSelfIntroduction() {
        return selfIntroduction;
    }

    public void setSelfIntroduction(String selfIntroduction) {
        this.selfIntroduction = selfIntroduction;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public String getCreatorArtCategory() {
        return creatorArtCategory;
    }

    public void setCreatorArtCategory(String creatorArtCategory) {
        this.creatorArtCategory = creatorArtCategory;
    }

    public List<String> getInterestCategories() {
        return interestCategories;
    }

    public void setInterestCategories(List<String> interestCategories) {
        this.interestCategories = interestCategories;
    }

    public String getSnsLink() {
        return snsLink;
    }

    public void setSnsLink(String snsLink) {
        this.snsLink = snsLink;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public Set<ExtraImage> getExtraImages() {
        return extraImages;
    }

    public void setExtraImages(Set<ExtraImage> extraImages) {
        this.extraImages = extraImages;
    }

    public String getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(String portfolio) {
        this.portfolio = portfolio;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public void addExtraImage(ExtraImage extraImage) {
        extraImages.add(extraImage);
        extraImage.setArtist(this);
    }

    public void removeExtraImage(ExtraImage extraImage) {
        extraImages.remove(extraImage);
        extraImage.setArtist(null);
    }

}
