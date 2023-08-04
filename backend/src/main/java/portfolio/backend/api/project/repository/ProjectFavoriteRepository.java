package portfolio.backend.api.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import portfolio.backend.api.artist.entity.ArtistFavorite;
import portfolio.backend.api.project.entity.Project;
import portfolio.backend.api.project.entity.ProjectFavorite;
import portfolio.backend.authentication.api.entity.user.User;

import java.util.List;
import java.util.Optional;

public interface ProjectFavoriteRepository extends JpaRepository<ProjectFavorite, Long> {

    Optional<ProjectFavorite> findByUserIdAndProject(String userId, Project project);

    List<ProjectFavorite> findAllByUserId(String userId);

}
