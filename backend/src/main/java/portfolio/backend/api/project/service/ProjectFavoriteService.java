package portfolio.backend.api.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import portfolio.backend.api.artist.exception.DuplicateResourceException;
import portfolio.backend.api.artist.exception.NotFoundException;
import portfolio.backend.api.project.dto.FavoriteProjectRequestDTO;
import portfolio.backend.api.project.entity.Project;
import portfolio.backend.api.project.entity.ProjectFavorite;
import portfolio.backend.api.project.repository.ProjectFavoriteRepository;
import portfolio.backend.api.project.repository.ProjectRepository;
//import portfolio.backend.authentication.api.entity.user.User;
import portfolio.backend.authentication.api.service.UserService;

@Service
@RequiredArgsConstructor
public class ProjectFavoriteService {
    private final UserService userService;
    private final ProjectFavoriteRepository projectFavoriteRepository;
    private final ProjectRepository projectRepository;

    @Transactional
    public void insert(FavoriteProjectRequestDTO favoriteProjectRequestDTO) throws Exception{
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String userId = principal.getUsername();
//        User user = userService.getUser(principal.getUsername());
        Project project = projectRepository.findById(favoriteProjectRequestDTO.getProjectId())
                .orElseThrow(() -> new NotFoundException("프로젝트를 찾을 수 없음" + favoriteProjectRequestDTO.getProjectId()));

        if (projectFavoriteRepository.findByUserIdAndProject(userId, project).isPresent()){

            throw new DuplicateResourceException("이미 좋아요를 한 프로젝트입니다" );
        }
        ProjectFavorite projectFavorite = ProjectFavorite.builder()
                .userId(userId)
                .project(project)
                .build();
        projectFavoriteRepository.save(projectFavorite);
    }

    @Transactional
    public void delete(FavoriteProjectRequestDTO favoriteProjectRequestDTO){
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

//        User user = userService.getUser(principal.getUsername());
        String userId = principal.getUsername();


        Project project = projectRepository.findById(favoriteProjectRequestDTO.getProjectId())
                .orElseThrow(() -> new NotFoundException("프로젝트를 찾을 수 없음" + favoriteProjectRequestDTO.getProjectId()));

        ProjectFavorite projectFavorite = projectFavoriteRepository.findByUserIdAndProject(userId, project)
                .orElseThrow(() -> new NotFoundException("즐겨찾기 찾을 수 없음"));

        projectFavoriteRepository.delete(projectFavorite);
    }

}