package portfolio.backend.api.artist.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import portfolio.backend.api.artist.entity.Artist;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
}
