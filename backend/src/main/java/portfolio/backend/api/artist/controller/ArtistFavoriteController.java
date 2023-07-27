package portfolio.backend.api.artist.controller;


import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import portfolio.backend.api.artist.dto.FavoriteRequestDTO;
import portfolio.backend.api.artist.exception.HttpResponseEntity;
import portfolio.backend.api.artist.services.ArtistFavoriteService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/artists/favorite")
@Api(value = "아티스트 즐겨찾기 API")
public class ArtistFavoriteController {

    private final ArtistFavoriteService artistFavoriteService;

    // 아티스트 좋아요 누를시 insert function 실행

    @PostMapping
    public HttpResponseEntity.ResponseResult<?> insert(@RequestBody @Valid FavoriteRequestDTO favoriteRequestDTO) throws Exception {
        artistFavoriteService.insert(favoriteRequestDTO);
        return HttpResponseEntity.success();
    }

    // 아티스트 좋아요 한번 더 누를시 delete function 실행

    @DeleteMapping
    public HttpResponseEntity.ResponseResult<?> delete(@RequestBody @Valid FavoriteRequestDTO favoriteRequestDTO){
        artistFavoriteService.delete(favoriteRequestDTO);
        return HttpResponseEntity.success();
    }

}