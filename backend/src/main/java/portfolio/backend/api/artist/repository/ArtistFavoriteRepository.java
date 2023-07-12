package portfolio.backend.api.artist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import portfolio.backend.api.artist.entity.Artist;
import portfolio.backend.api.artist.entity.ArtistFavorite;
import portfolio.backend.authentication.api.entity.user.User;

import java.util.Optional;

public interface ArtistFavoriteRepository extends JpaRepository<ArtistFavorite, Long> {

    Optional<ArtistFavorite> findByUserAndArtist(User user, Artist artist);

<<<<<<< HEAD
}
=======
}
>>>>>>> 875fa295654b6b6edf6aeea850c13e61153075fe
