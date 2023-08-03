package portfolio.backend.api.project.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import portfolio.backend.api.artist.entity.ArtistFavorite;
import portfolio.backend.api.artist.exception.HttpResponseEntity;
import portfolio.backend.api.project.dto.FavoriteProjectRequestDTO;
import portfolio.backend.api.project.entity.ProjectFavorite;
import portfolio.backend.api.project.service.ProjectFavoriteService;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/projects/favorite")
@Api(tags = {"Project Likes"})
public class ProjectFavoriteController {

    private final ProjectFavoriteService projectFavoriteService;
    // 프로젝트를 좋아요 누를시 insert function 실행

    @GetMapping
    public List<ProjectFavorite> getAllByUserId(@ApiIgnore Authentication authentication) {
        String currentUserId = authentication.getName();
        List<ProjectFavorite> projectFavorites = projectFavoriteService.findAllByUserId(currentUserId);
        return projectFavorites;
    }

    @PostMapping
    public HttpResponseEntity.ResponseResult<?> insert(@RequestBody @Valid FavoriteProjectRequestDTO favoriteProjectRequestDTO) throws Exception {
        projectFavoriteService.insert(favoriteProjectRequestDTO);
        return HttpResponseEntity.success();
    }

    // 프로젝트를 좋아요 한번 더 누를시 delete function 실행

    @DeleteMapping
    public HttpResponseEntity.ResponseResult<?> delete(@RequestBody @Valid FavoriteProjectRequestDTO favoriteProjectRequestDTO){
        projectFavoriteService.delete(favoriteProjectRequestDTO);
        return HttpResponseEntity.success();
    }

}