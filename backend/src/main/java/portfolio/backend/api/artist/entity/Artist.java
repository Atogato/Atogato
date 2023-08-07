package portfolio.backend.api.artist.entity;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.License;
import lombok.Getter;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;
import portfolio.backend.authentication.api.entity.user.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Getter
@DynamicUpdate
@DynamicInsert
@Entity
@Table(name="artists")
public class Artist {

    @Column(nullable = false, unique = true)
    private String userId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(hidden = true)
    private Long artistId;

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

    @Column(nullable=false)
    private String interestCategory;

    @Column(nullable=true)
    private String snsLink;

    @Lob
    private String mainImage;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "artist", cascade = CascadeType.ALL)
    private List<ExtraImage> extraImages = new ArrayList<>();


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

    public String getInterestCategory() {
        return interestCategory;
    }

    public void setInterestCategory(String interestCategory) {
        this.interestCategory = interestCategory;
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


    public List<ExtraImage> getExtraImage() {
        return extraImages;
    }

    public void setExtraImages(List<ExtraImage> extraImage) {
        this.extraImages = extraImage;
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

}
