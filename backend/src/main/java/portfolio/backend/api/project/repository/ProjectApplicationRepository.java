package portfolio.backend.api.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import portfolio.backend.api.project.entity.ProjectApplication;

import java.util.List;

@Repository
public interface ProjectApplicationRepository extends JpaRepository<ProjectApplication, Long> {
    List<ProjectApplication> findByAppliedArtistId(String appliedArtistId);

    List<ProjectApplication> findByAppliedArtistIdAndApplicationStatus(String appliedArtistId, ProjectApplication.ApplicationStatus applicationStatus);

}

