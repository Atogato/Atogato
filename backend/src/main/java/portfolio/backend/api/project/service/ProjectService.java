package portfolio.backend.api.project.service;

import org.springframework.stereotype.Service;
import portfolio.backend.api.project.entity.Project;
import portfolio.backend.api.project.repository.ProjectRepository;

import java.time.LocalDate;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository){ // Constructor
        this.projectRepository = projectRepository;
    }

    public Project createProject(Project project){
        project.setCreatedDate(LocalDate.now());
        return projectRepository.save(project);
    }
}
