package portfolio.backend.api.artist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import portfolio.backend.api.artist.entity.Artist;

public interface ArtistRepostiroy extends JpaRepository<Artist, Long> {
}
