package portfolio.backend.api.artist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import portfolio.backend.api.artist.entity.Artist;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
}
