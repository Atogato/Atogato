package portfolio.backend.api.artist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import portfolio.backend.api.artist.entity.Artist;
import portfolio.backend.api.artist.entity.ArtistFavorite;
import portfolio.backend.authentication.api.entity.user.User;

import java.util.List;
import java.util.Optional;

public interface ArtistFavoriteRepository extends JpaRepository<ArtistFavorite, Long> {
    Optional<ArtistFavorite> findByUserIdAndArtist(String userId, Artist artist);
    List<ArtistFavorite> findAllByUserId(String userId);

}