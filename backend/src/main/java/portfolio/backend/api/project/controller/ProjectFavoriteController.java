package portfolio.backend.api.project.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import portfolio.backend.api.artist.exception.HttpResponseEntity;
import portfolio.backend.api.project.dto.FavoriteProjectRequestDTO;
import portfolio.backend.api.project.dto.FavoriteProjectResponseDTO;
import portfolio.backend.api.project.service.ProjectFavoriteService;
import portfolio.backend.authentication.api.service.UserContextService;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/projects/favorite")
@Api(tags = {"Project Likes"})
public class ProjectFavoriteController {

    private final ProjectFavoriteService projectFavoriteService;

    private final UserContextService userContextService;

    @GetMapping
    public List<FavoriteProjectResponseDTO> getAllByUserId(@ApiIgnore Authentication authentication) {
        String currentUserId = userContextService.getCurrentUser().getUserId();
        List<FavoriteProjectResponseDTO> projectFavorites = projectFavoriteService.findAllByUserId(currentUserId);

        return projectFavorites;
    }

    @PostMapping
    public HttpResponseEntity.ResponseResult<?> insert(@RequestParam Long projectId) throws Exception {
        FavoriteProjectRequestDTO favoriteProjectRequestDTO = new FavoriteProjectRequestDTO(projectId);
        projectFavoriteService.insert(favoriteProjectRequestDTO);
        return HttpResponseEntity.success();
    }

    // 프로젝트를 좋아요 한번 더 누를시 delete function 실행
    @DeleteMapping
    public HttpResponseEntity.ResponseResult<?> delete(@RequestParam Long projectId){
        FavoriteProjectRequestDTO favoriteProjectRequestDTO = new FavoriteProjectRequestDTO(projectId);
        projectFavoriteService.delete(favoriteProjectRequestDTO);
        return HttpResponseEntity.success();
    }
}