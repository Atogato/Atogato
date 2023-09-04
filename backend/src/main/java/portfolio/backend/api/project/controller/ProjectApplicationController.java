package portfolio.backend.api.project.controller;

import io.swagger.annotations.Api;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import portfolio.backend.api.project.entity.Project;
import portfolio.backend.api.project.entity.ProjectApplication;
import portfolio.backend.api.project.exception.ResourceNotFoundException;
import portfolio.backend.api.project.repository.ProjectApplicationRepository;
import portfolio.backend.api.project.repository.ProjectRepository;
import portfolio.backend.api.project.service.ProjectApplicationService;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequestMapping("api/projects-apply")
@Api(tags = {"Project Application"})

public class ProjectApplicationController {
    private final ProjectApplicationService projectApplicationService;
    private final ProjectApplicationRepository projectApplicationRepository;
    private final ProjectRepository projectRepository;

    public ProjectApplicationController(ProjectApplicationService projectApplicationService, ProjectRepository projectRepository, ProjectApplicationRepository projectApplicationRepository) {
        this.projectApplicationService = projectApplicationService;
        this.projectRepository = projectRepository;
        this.projectApplicationRepository = projectApplicationRepository;
    }

    @PostMapping("/apply")
    public ProjectApplication apply(@RequestParam Long projectId, @ApiIgnore Authentication authentication) {
        String currentUserId = authentication.getName();
        return projectApplicationService.apply(projectId, currentUserId);
    }

    @GetMapping("/recieved")
    public List<ProjectApplication> getApplicationsForMyProjects(@ApiIgnore Authentication authentication) {
        String currentUserId = authentication.getName();
        return projectApplicationService.getApplicationsForMyProjects(currentUserId);
    }

    @GetMapping("/submitted")
    public List<ProjectApplication> getMyApplications(@ApiIgnore Authentication authentication) {
        String currentUserId = authentication.getName();
        return projectApplicationService.getMyApplications(currentUserId);
    }

    @PutMapping("/{id}")
    public ProjectApplication updateApplicationStatus(@PathVariable Long id, @RequestBody ProjectApplication.ApplicationStatus newStatus, @ApiIgnore Authentication authentication) {
        String currentUserId = authentication.getName();

        ProjectApplication updatedApplication = projectApplicationService.updateApplicationStatus(id, newStatus, currentUserId);
        if (newStatus == ProjectApplication.ApplicationStatus.ACCEPTED) {
            Project project = updatedApplication.getProject();

            project.getParticipantArtistIds().add(updatedApplication.getAppliedArtistId());

            projectRepository.save(project);
        }

        return updatedApplication;

    }

    @DeleteMapping("/{id}")
    public void deleteMyApplication(@PathVariable Long id, @ApiIgnore Authentication authentication) {
        String currentUserId = authentication.getName();
        ProjectApplication application = projectApplicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found: " + id));

        if (application.getApplicationStatus() == ProjectApplication.ApplicationStatus.ACCEPTED) {
//            Project project = projectRepository.findById(application.getProjectId())
//                    .orElseThrow(() -> new ResourceNotFoundException("Project not found: " + application.getProjectId()));
            Project project = application.getProject();

            project.getParticipantArtistIds().remove(application.getAppliedArtistId());

            projectRepository.save(project);
        }

        projectApplicationService.deleteMyApplication(id, currentUserId);
    }

}
