package portfolio.backend.api.artist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import portfolio.backend.api.artist.entity.Artist;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
    List<Artist> findByUserIdIn(List<String> userIds);
    Optional<Artist> findByUserId(String userId);
    boolean existsByUserId(String userId);
}
