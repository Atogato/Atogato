package portfolio.backend.api.artist.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Entity
@Table(name="artist_swipe")
public class ArtistSwipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "artist_swipe_id")
    private Long id;

    @Column(nullable=false)
    private String likedSenderId;

    @Column(nullable=false)
    private String likedReceiverId;

    @Column(nullable=false)
    private Boolean matched = false;

    @Column(nullable=false)
    private Boolean rejected = false;

    @Column()
    private LocalDate rejectedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLikedSenderId() {
        return likedSenderId;
    }

    public void setLikedSenderId(String likedSenderId) {
        this.likedSenderId = likedSenderId;
    }

    public String getLikedReceiverId() {
        return likedReceiverId;
    }

    public void setLikedReceiverId(String likedReceiverId) {
        this.likedReceiverId = likedReceiverId;
    }

    public Boolean getMatched() {
        return matched;
    }

    public void setMatched(Boolean matched) {
        this.matched = matched;
    }

    public Boolean getRejected() {
        return rejected;
    }

    public void setRejected(Boolean rejected) {
        this.rejected = rejected;
    }

    public LocalDate getRejectedDate() {
        return rejectedDate;
    }

    public void setRejectedDate(LocalDate rejectedDate) {
        this.rejectedDate = rejectedDate;
    }
}