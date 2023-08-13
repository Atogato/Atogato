package portfolio.backend.api.artist.controller;


import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import portfolio.backend.api.artist.entity.Artist;
import portfolio.backend.api.artist.entity.ArtistSwipe;
import portfolio.backend.api.artist.services.ArtistSwipeService;
import portfolio.backend.authentication.api.entity.user.User;
import portfolio.backend.authentication.api.service.UserService;

import java.util.List;

@RestController
@RequestMapping("api/artists/swipe")
@Api(tags = {"Artist Swipe"})
public class ArtistSwipeController {
    private final ArtistSwipeService artistSwipeService;
    private final UserService userService;

    public ArtistSwipeController(ArtistSwipeService artistSwipeService, UserService userService) {
        this.artistSwipeService = artistSwipeService;
        this.userService = userService;
    }

    // 매칭되었다
    @GetMapping("/matched")
    public List<Artist> getMatchesWhereUserIsInvolved() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getUser(principal.getUsername());
        return artistSwipeService.getMatchesWhereUserIsInvolved(principal.getUsername());
    }

    @GetMapping("/sorted")
    public List<ArtistSwipe> getSwipesSortedByLikedReceiver() {
        return artistSwipeService.getSwipesSortedByLikedReceiver();
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
