package portfolio.backend.api.artist.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import portfolio.backend.api.artist.entity.Artist;
import portfolio.backend.api.artist.entity.ArtistSwipe;
import portfolio.backend.api.artist.services.ArtistSwipeService;
import portfolio.backend.authentication.api.entity.user.User;
import portfolio.backend.authentication.api.service.UserContextService;
import portfolio.backend.authentication.api.service.UserService;

import java.util.List;

@RestController
@RequestMapping("api/artists/swipe")
@Api(tags = {"Artist Swipe"})
public class ArtistSwipeController {
    private final ArtistSwipeService artistSwipeService;
    private final UserService userService;

    @Autowired
    private UserContextService userContextService;

    public ArtistSwipeController(ArtistSwipeService artistSwipeService, UserService userService) {
        this.artistSwipeService = artistSwipeService;
        this.userService = userService;
    }

    @GetMapping("/matched")
    public List<Artist> getMatchesWhereUserIsInvolved() {
        User user = userContextService.getCurrentUser();
        return artistSwipeService.getMatchesWhereUserIsInvolved(user.getUserId());
    }

    @GetMapping("/sorted")
    public List<Artist> getArtistsSortedByLikedReceiver() {
        return artistSwipeService.getArtistsSortedByLikedReceiver();
    }

    @PostMapping("/like")
    public ResponseEntity<String> like(@RequestParam String receiverId) {
        try {
            artistSwipeService.like(receiverId);
            return ResponseEntity.ok("좋아요!!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/reject")
    public ResponseEntity<String> reject(@RequestParam String receiverId) {
        artistSwipeService.reject(receiverId);
        return ResponseEntity.ok("싫어요 ㅠㅠ");
    }
}
