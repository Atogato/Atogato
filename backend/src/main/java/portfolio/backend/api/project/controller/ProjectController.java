package portfolio.backend.api.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import portfolio.backend.api.auth.entity.SiteUser;
import portfolio.backend.api.auth.service.UserService;
import portfolio.backend.api.project.entity.Project;
import portfolio.backend.api.project.repository.ProjectRepository;

import portfolio.backend.api.project.exception.ResourceNotFoundException; // Import statement for ResourceNotFoundException

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private final ProjectRepository projectRepository;

    // inject both userService-> 로그인후 프로젝트 생성시 관계성 구축
    private final UserService userService;

    @Autowired // project Parameter 생성
    public ProjectController(ProjectRepository projectRepository, UserService userService) {
        this.projectRepository = projectRepository;
        this.userService = userService;
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
    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public Project createProject(Principal principal, // Principal 활용해서 현재 로그인 유저 가져오기
                                 @RequestParam String projectName,
                                 @RequestParam String creatorArtCategory,
                                 @RequestParam(defaultValue="0") Long liked,
                                 @RequestParam(defaultValue="Unknown") String location,
                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate deadline,
                                 @RequestParam Long requiredPeople,
                                 @RequestParam String requiredCategory,
                                 @RequestParam(defaultValue = "true") Boolean swipeAlgorithm,
                                 @RequestParam(defaultValue = "None") String image,
                                 @RequestParam String description,
                                 @RequestParam(defaultValue = "true") Boolean ongoingStatus,
                                 @RequestParam(defaultValue = "both") String remoteStatus,
                                 @RequestParam(defaultValue = "0") Long participantId) {

        SiteUser siteUser = this.userService.getUser(principal.getName());
        Long userId = siteUser.getId();
        Project project = new Project();

        // UserId는 가져온 로그인유저
        project.setUserId(userId);

        // 나머지 설정값에 따라
        project.setProjectName(projectName);
        project.setCreatorArtCategory(creatorArtCategory);
        project.setLiked(liked);
        project.setLocation(location);
        project.setCreatedDate(LocalDate.now());
        project.setOngoingStatus(ongoingStatus);
        project.setRemoteStatus(remoteStatus);
        project.setDeadline(deadline);
        project.setRequiredPeople(requiredPeople);
        project.setRequiredCategory(requiredCategory);
        project.setSwipeAlgorithm(swipeAlgorithm);
        project.setImage(image);
        project.setDescription(description);
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
        existingProject.setOngoingStatus(updatedProject.getOngoingStatus());
        existingProject.setRemoteStatus(updatedProject.getRemoteStatus());
        existingProject.setDescription(updatedProject.getDescription());
        existingProject.setUserId(updatedProject.getUserId());
        existingProject.setParticipantId(updatedProject.getParticipantId());
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
