package portfolio.backend.api.project.controller;

import io.swagger.annotations.Api;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import portfolio.backend.api.project.entity.Project;

import portfolio.backend.api.project.service.ProjectService;
import portfolio.backend.authentication.api.service.UserContextService;
import springfox.documentation.annotations.ApiIgnore;


import java.time.LocalDate;
import java.util.*;

@Api(tags = {"Project"})
@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private final ProjectService projectService;
    private final UserContextService userContextService;

    public ProjectController(ProjectService projectService, UserContextService userContextService) {
        this.projectService = projectService;
        this.userContextService = userContextService;
    }

    @GetMapping("/{id}")
    public Project getProjectById(@PathVariable Long id) {
        return projectService.getProjectById(id);
    }

    @GetMapping()
    public List<Project> getAllProjectsByCreatedDate() {
        return projectService.getAllProjectsByCreatedDate();
    }

    @GetMapping("/sorted")
    public List<Project> getAllProjectsByApplicationDeadline() {
        return projectService.getAllProjectsByApplicationDeadline();
    }


    @PostMapping
    public Project createProject(
            @RequestParam String projectName,
            @RequestParam Project.ProjectCategory projectArtCategory,
            @RequestParam(defaultValue = "Unknown") String location,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate projectDeadline,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate applicationDeadline,
            @RequestParam List<Project.RequiredCategory> requiredCategory,
            @RequestParam(defaultValue = "true") Boolean swipeAlgorithm,
            @RequestParam(value = "image") List<MultipartFile> projectImageFiles,
            @RequestParam String description,
            @RequestParam(defaultValue = "true") Boolean ongoingStatus,
            @RequestParam(defaultValue = "both") String remoteStatus,
            @RequestParam(defaultValue = "0") Long requiredPeople,
            @ApiIgnore Authentication authentication) {
        String currentUserId = userContextService.getCurrentUser().getUserId();
        return projectService.createProject(currentUserId, projectName, projectArtCategory, location, projectDeadline, applicationDeadline, requiredCategory, swipeAlgorithm, projectImageFiles, description, ongoingStatus, remoteStatus, requiredPeople);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateProject(@PathVariable Long id,
                                           @RequestParam(value = "projectName", required = false) String projectName,
                                           @RequestParam(value = "projectArtCategory", required = false) Project.ProjectCategory projectArtCategory,
                                           @RequestParam(value = "location", required = false) String location,
                                           @RequestParam(value = "projectDeadline", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate projectDeadline,
                                           @RequestParam(value = "applicationDeadline", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate applicationDeadline,
                                           @RequestParam(value = "requiredCategory", required = false) List<Project.RequiredCategory> requiredCategory,
                                           @RequestParam(value = "swipeAlgorithm", required = false) Boolean swipeAlgorithm,
                                           @RequestParam(value = "projectImageFiles", required = false) List<MultipartFile> projectImageFiles,
                                           @RequestParam(value = "description", required = false) String description,
                                           @RequestParam(value = "ongoingStatus", required = false) Boolean ongoingStatus,
                                           @RequestParam(value = "remoteStatus", required = false) String remoteStatus,
                                           @RequestParam(value = "requiredPeople", required = false) Long requiredPeople) {
        String currentUserId = userContextService.getCurrentUser().getUserId();
        return projectService.updateProject(id, projectName, projectArtCategory, location, projectDeadline, applicationDeadline, requiredCategory, swipeAlgorithm, projectImageFiles, description, ongoingStatus, remoteStatus, requiredPeople, currentUserId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable Long id) {
        String currentUserId = userContextService.getCurrentUser().getUserId();
        return projectService.deleteProject(id, currentUserId);
    }


}