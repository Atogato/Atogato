package portfolio.backend.authentication.api.controller.user;

import io.swagger.annotations.Api;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import portfolio.backend.api.artist.dto.FavoriteResponseDTO;
import portfolio.backend.api.artist.entity.Artist;
import portfolio.backend.api.artist.repository.ArtistRepository;
import portfolio.backend.api.artist.services.ArtistFavoriteService;
import portfolio.backend.api.artist.services.ArtistSwipeService;
import portfolio.backend.api.project.dto.FavoriteProjectResponseDTO;
import portfolio.backend.api.project.service.ProjectApplicationService;
import portfolio.backend.api.project.service.ProjectFavoriteService;
import portfolio.backend.authentication.api.entity.user.User;
import portfolio.backend.authentication.api.service.UserService;
import portfolio.backend.authentication.common.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Api(tags = {"Current User"})
public class UserController {

    private final UserService userService;
    private final ArtistSwipeService artistSwipeService;
    private final ArtistFavoriteService artistFavoriteService;
    private final ProjectFavoriteService projectFavoriteService;
    private final ProjectApplicationService projectApplicationService;
    private final ArtistRepository artistRepository;

    @GetMapping
    public ApiResponse getUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getUser(principal.getUsername());
        boolean isArtist = artistRepository.findByUserId(user.getUserId()).isPresent();

        List<Artist> matchedArtists = artistSwipeService.getMatchesWhereUserIsInvolved(principal.getUsername());

        List<FavoriteResponseDTO> favoriteArtists = artistFavoriteService.findAllByUserId(principal.getUsername());
        List<FavoriteProjectResponseDTO> favoriteProjects = projectFavoriteService.findAllByUserId(principal.getUsername());
        List<Long> acceptedProjects = projectApplicationService.getAcceptedProjectsForUser(principal.getUsername());

        UserWithMatches userWithMatches = new UserWithMatches(user, matchedArtists, favoriteArtists, favoriteProjects, acceptedProjects, isArtist);

        return ApiResponse.success("user", userWithMatches);
    }


    @Getter
    private static class UserWithMatches {
        private final User user;
        private final List<Artist> matchedArtists;
        private final List<FavoriteResponseDTO> favoriteArtists;
        private final List<FavoriteProjectResponseDTO> favoriteProjects;
        private final List<Long> acceptedProjects;
        private final boolean isArtist;

        public UserWithMatches(User user, List<Artist> matchedArtists, List<FavoriteResponseDTO> favoriteArtists, List<FavoriteProjectResponseDTO> favoriteProjects, List<Long> acceptedProjects, boolean isArtist) {
            this.user = user;
            this.matchedArtists = matchedArtists;
            this.favoriteArtists = favoriteArtists;
            this.favoriteProjects = favoriteProjects;
            this.acceptedProjects = acceptedProjects;
            this.isArtist = isArtist;
        }
    }
}
