package portfolio.backend.api.artist.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;
import portfolio.backend.authentication.api.entity.user.User;

import javax.persistence.*;
import java.net.URI;
import java.net.URL;
import java.time.LocalDate;

@Getter
@DynamicUpdate
@DynamicInsert
@Entity
@Table(name="artists")
public class Artist {


    @Column(nullable = false)
    private String userId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(hidden = true)
    private Long artistId;

    @Column(nullable=false)
    private String artistName;

    @Column(nullable = true) // location can be null
    private String location;

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

    @Column(nullable=true)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthdate;


    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

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



    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

}
