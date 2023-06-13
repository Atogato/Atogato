package portfolio.backend.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import portfolio.backend.api.entity.Project;
import portfolio.backend.api.repository.ProjectRepository;
import portfolio.backend.api.exception.ResourceNotFoundException; // Import statement for ResourceNotFoundException

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    // 전체 프로젝트 GET
    @GetMapping
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }


    // 특정 프로젝트 PK Param GET
    @GetMapping("/{id}")
    public Project getProjectById(@PathVariable Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ID not found: " + id));
    }


    // 새로운 프로젝트 POST
    @PostMapping
    public Project createProject(@RequestParam String projectName,
                                 @RequestParam String creatorArtCategory,
                                 @RequestParam(defaultValue="0") Long liked,
                                 @RequestParam(defaultValue="Unknown") String location,
                                 @RequestParam() @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate deadline,
                                 @RequestParam() Long requiredPeople,
                                 @RequestParam() String requiredCategory,
                                 @RequestParam(defaultValue = "true") Boolean swipeAlgorithm,
                                 @RequestParam(defaultValue = "None") String image,
                                 @RequestParam String description,
                                 @RequestParam Long userId,
                                 @RequestParam(defaultValue = "0") Long participantId) {
        Project project = new Project();
        project.setProjectName(projectName);
        project.setCreatorArtCategory(creatorArtCategory);
        project.setCreatorArtCategory(creatorArtCategory);
        project.setLocation(location);
        project.setCreatedDate(LocalDate.now());
        project.setLiked(liked);
        project.setDeadline(deadline);
        project.setRequiredPeople(requiredPeople);
        project.setRequiredCategory(requiredCategory);
        project.setRequiredCategory(requiredCategory);
        project.setSwipeAlgorithm(swipeAlgorithm);
        project.setImage(image);
        project.setDescription(description);
        project.setUserId(userId);
        project.setParticipantId(participantId);

        return projectRepository.save(project);
    }

    // 특정 프로젝트 PUT
    @PutMapping("/{id}")
    public Project updateProject(@PathVariable Long id, @RequestBody Project updatedProject) {
        Project existingProject = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ID not found: " + id));

        existingProject.setProjectName(updatedProject.getProjectName());
        existingProject.setCreatedDate(updatedProject.getCreatedDate());
        existingProject.setCreatorArtCategory(updatedProject.getCreatorArtCategory());
        existingProject.setLocation(updatedProject.getLocation());
        existingProject.setSwipeAlgorithm(updatedProject.getSwipeAlgorithm());
        existingProject.setLiked(updatedProject.getLiked());
        existingProject.setImage(updatedProject.getImage());
        existingProject.setRequiredCategory(updatedProject.getRequiredCategory());
        existingProject.setRequiredPeople(updatedProject.getRequiredPeople());
        existingProject.setDeadline(updatedProject.getDeadline());

        existingProject.setDescription(updatedProject.getDescription());
        existingProject.setUserId(updatedProject.getUserId());

        return projectRepository.save(existingProject);
    }

    // 특정 프레제트 DELETE
    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ID not found: " + id));
        projectRepository.delete(project);
    }
}
