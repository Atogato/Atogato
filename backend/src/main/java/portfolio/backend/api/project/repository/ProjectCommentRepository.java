package portfolio.backend.api.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import portfolio.backend.api.project.entity.ProjectComment;

public interface ProjectCommentRepository extends JpaRepository<ProjectComment, Long> {
}
