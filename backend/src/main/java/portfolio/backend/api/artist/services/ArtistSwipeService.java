package portfolio.backend.api.artist.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import portfolio.backend.api.artist.entity.Artist;
import portfolio.backend.api.artist.entity.ArtistSwipe;
import portfolio.backend.api.artist.repository.ArtistRepository;
import portfolio.backend.api.artist.repository.ArtistSwipeRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArtistSwipeService {
    private final ArtistSwipeRepository artistSwipeRepository;
    private final ArtistRepository artistRepository;

    @Autowired
    public ArtistSwipeService( ArtistSwipeRepository artistSwipeRepository, ArtistRepository artistRepository){
        this.artistSwipeRepository = artistSwipeRepository;
        this.artistRepository = artistRepository;
    }

    public void like(String receiverId) throws Exception{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String senderId = authentication.getName();
        if(!artistRepository.existsByUserId(receiverId)) {
            throw new Exception("좋아요한 ID는 아티스트가 아닙니다");
        }

        if(artistSwipeRepository.existsByLikedSenderIdAndLikedReceiverId(senderId, receiverId)){
            throw new Exception("매칭이 이미 존재합니다");
        }

        Optional<ArtistSwipe> optionalArtistSwipe = artistSwipeRepository.findByLikedSenderIdAndLikedReceiverId(receiverId, senderId);
        if(optionalArtistSwipe.isPresent() && !optionalArtistSwipe.get().getRejected()){
            ArtistSwipe artistSwipe = optionalArtistSwipe.get();
            artistSwipe.setMatched(true);
            artistSwipeRepository.save(artistSwipe);
        } else {
            ArtistSwipe artistSwipe = new ArtistSwipe();
            artistSwipe.setLikedSenderId(senderId);
            artistSwipe.setLikedReceiverId(receiverId);
            artistSwipeRepository.save(artistSwipe);
        }
    }


    public void reject(String receiverId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String senderId = authentication.getName();

        Optional<ArtistSwipe> optionalArtistSender = artistSwipeRepository.findByLikedSenderIdAndLikedReceiverId(senderId, receiverId);
        Optional<ArtistSwipe> optionalArtistReceiver = artistSwipeRepository.findByLikedSenderIdAndLikedReceiverId(receiverId, senderId);

        if(optionalArtistSender.isPresent()){
            ArtistSwipe artistSwipe = optionalArtistSender.get();
            artistSwipe.setRejected(true);
            artistSwipe.setRejectedDate(LocalDate.now());
            artistSwipe.setMatched(false);
            artistSwipeRepository.save(artistSwipe);
        }
        if(optionalArtistReceiver.isPresent()){
            ArtistSwipe artistSwipe = optionalArtistReceiver.get();
            artistSwipe.setRejected(true);
            artistSwipe.setRejectedDate(LocalDate.now());
            artistSwipe.setMatched(false);
            artistSwipeRepository.save(artistSwipe);
        }
    }

    public List<ArtistSwipe> getSwipesSortedByLikedReceiver() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = auth.getName();

        List<ArtistSwipe> swipes = artistSwipeRepository.findAll().stream()
                .filter(swipe -> !swipe.getMatched() && !swipe.getRejected())
                .filter(swipe -> !swipe.getLikedSenderId().equals(currentUserId))
                .filter(swipe -> artistRepository.existsByUserId(swipe.getLikedSenderId()) && artistRepository.existsByUserId(swipe.getLikedReceiverId()))
                .collect(Collectors.toList());

        swipes.sort((a, b) -> {
            if (a.getLikedReceiverId().equals(currentUserId) && !b.getLikedReceiverId().equals(currentUserId)) {
                return -1;
            } else if (!a.getLikedReceiverId().equals(currentUserId) && b.getLikedReceiverId().equals(currentUserId)) {
                return 1;
            }
            return 0;
        });

        return swipes;
    }

    @Scheduled(cron = "0 0 0 * * ?", zone="Asia/Seoul")
    public void deleteOldRejectedMatches() {
        LocalDate threeMonthsAgo = LocalDate.now().minusDays(1);
        List<ArtistSwipe> oldRejectedMatches = artistSwipeRepository.findByRejectedDateBeforeAndRejected(threeMonthsAgo, true);
        artistSwipeRepository.deleteAll(oldRejectedMatches);
    }

    public List<Artist> getArtistsWhoLikedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        List<ArtistSwipe> artistSwipes = artistSwipeRepository.findByLikedReceiverIdAndMatchedAndRejected(userId, false, false);
        List<String> userIdsWhoLiked = artistSwipes.stream()
                .map(ArtistSwipe::getLikedSenderId)
                .collect(Collectors.toList());

        List<Artist> artistsWhoLiked = artistRepository.findByUserIdIn(userIdsWhoLiked);
        return artistsWhoLiked;
    }


    public List<Artist> getMatchesWhereUserIsInvolved(String userId) {

        List<ArtistSwipe> artistSwipe = artistSwipeRepository.findMatchedAndNotRejectedSwipesForUser(userId);
        List<String> userIdsInvolved = artistSwipe.stream()
                .map(swipe -> swipe.getLikedSenderId().equals(userId) ? swipe.getLikedReceiverId() : swipe.getLikedSenderId())
                .collect(Collectors.toList());
        List<Artist> artistsInvolved = artistRepository.findByUserIdIn(userIdsInvolved);

        return artistsInvolved;
    }
}
