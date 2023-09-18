package portfolio.backend.api.project.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import portfolio.backend.api.project.entity.Project;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>, JpaSpecificationExecutor<Project> {
    List<Project> findAllByOrderByCreatedDateDesc();
    List<Project> findByApplicationDeadlineAfter(LocalDate date, Sort sort);
    List<Project> findByUserId(String userId);

}
