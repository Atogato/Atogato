package portfolio.backend.api.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import portfolio.backend.api.entity.Project;


@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
