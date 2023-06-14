package portfolio.backend.api.project.service;

import org.springframework.stereotype.Service;
import portfolio.backend.api.project.entity.Project;
import portfolio.backend.api.project.repository.ProjectRepository;

import java.time.LocalDate;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository; // Instance of Project Created

    public ProjectService(ProjectRepository projectRepository){ // Constructor
        this.projectRepository = projectRepository;
    }

    public Project createProject(Project project){
        project.setCreatedDate(LocalDate.now()); // created date = today
        return projectRepository.save(project);
    }

}
