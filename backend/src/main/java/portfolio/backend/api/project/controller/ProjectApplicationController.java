package portfolio.backend.api.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import portfolio.backend.api.project.entity.ProjectApplication;
import portfolio.backend.api.project.service.ProjectApplicationService;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequestMapping("api/projects_apply_api")
public class ProjectApplicationController {
    private final ProjectApplicationService projectApplicationService;

    public ProjectApplicationController(ProjectApplicationService projectApplicationService) {
        this.projectApplicationService = projectApplicationService;
    }

    @PostMapping("/apply")
    public ProjectApplication apply(@RequestParam Long projectId, @ApiIgnore Authentication authentication) {
        String currentUserId = authentication.getName();
        return projectApplicationService.apply(projectId, currentUserId);
    }

    @GetMapping("/my_projects_applications")
    public List<ProjectApplication> getApplicationsForMyProjects(@ApiIgnore Authentication authentication) {
        String currentUserId = authentication.getName();
        return projectApplicationService.getApplicationsForMyProjects(currentUserId);
    }

    @GetMapping("/my_applications")
    public List<ProjectApplication> getMyApplications(@ApiIgnore Authentication authentication) {
        String currentUserId = authentication.getName();
        return projectApplicationService.getMyApplications(currentUserId);
    }

    @PutMapping("/{id}")
    public ProjectApplication updateApplicationStatus(@PathVariable Long id, @RequestBody ProjectApplication.ApplicationStatus newStatus, @ApiIgnore Authentication authentication) {
        String currentUserId = authentication.getName();
        return projectApplicationService.updateApplicationStatus(id, newStatus, currentUserId);
    }

    @DeleteMapping("/{id}")
    public void deleteMyApplication(@PathVariable Long id, @ApiIgnore Authentication authentication) {
        String currentUserId = authentication.getName();
        projectApplicationService.deleteMyApplication(id, currentUserId);
    }

}
