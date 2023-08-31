package portfolio.backend.api.artist.controller;


import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import portfolio.backend.api.artist.dto.FavoriteRequestDTO;
import portfolio.backend.api.artist.dto.FavoriteResponseDTO;
import portfolio.backend.api.artist.exception.HttpResponseEntity;
import portfolio.backend.api.artist.services.ArtistFavoriteService;
import portfolio.backend.authentication.api.entity.user.User;
import portfolio.backend.authentication.api.service.UserContextService;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/artists/favorite")
@Api(tags = {"Artist Likes"})
public class ArtistFavoriteController {

    private final ArtistFavoriteService artistFavoriteService;

    @Autowired
    private UserContextService userContextService;

    @GetMapping
    public List<FavoriteResponseDTO> getAllByUserId(@ApiIgnore Authentication authentication) {
        User user = userContextService.getCurrentUser();
        List<FavoriteResponseDTO> artistFavorites = artistFavoriteService.findAllByUserId(user.getUserId());

        return artistFavorites;
    }

    // 아티스트 좋아요 누를시 insert function 실행
    @PostMapping
    public HttpResponseEntity.ResponseResult<?> insert(@RequestParam Long artistId,
                                                       @ApiIgnore Authentication authentication) throws Exception {
        FavoriteRequestDTO favoriteRequestDTO = new FavoriteRequestDTO(artistId);
        artistFavoriteService.insert(favoriteRequestDTO);
        return HttpResponseEntity.success();
    }

    // 아티스트 좋아요 한번 더 누를시 delete function 실행
    @DeleteMapping
    public HttpResponseEntity.ResponseResult<?> delete(@RequestParam Long artistId){
        FavoriteRequestDTO favoriteRequestDTO = new FavoriteRequestDTO(artistId);
        artistFavoriteService.delete(favoriteRequestDTO);
        return HttpResponseEntity.success();
    }

}