package portfolio.backend.api.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import portfolio.backend.api.project.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
