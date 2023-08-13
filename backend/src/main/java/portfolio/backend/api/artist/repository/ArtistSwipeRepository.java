package portfolio.backend.api.artist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import portfolio.backend.api.artist.entity.ArtistSwipe;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ArtistSwipeRepository extends JpaRepository<ArtistSwipe, Long> {
    Optional<ArtistSwipe> findByLikedSenderIdAndLikedReceiverId(String likedSenderId, String likeReceiverId);
    boolean existsByLikedSenderIdAndLikedReceiverId(String likedSenderId, String likeReceiverId);
    List<ArtistSwipe> findByLikedReceiverIdAndMatchedAndRejected(String likeReceiverId, Boolean matched, Boolean rejected);

    @Query("SELECT a FROM ArtistSwipe a WHERE a.matched = true AND a.rejected = false AND (a.likedSenderId = :userId OR a.likedReceiverId = :userId)")
    List<ArtistSwipe> findMatchedAndNotRejectedSwipesForUser(@Param("userId") String userId);

    List<ArtistSwipe> findByRejectedDateBeforeAndRejected(LocalDate date, Boolean rejected);

}
