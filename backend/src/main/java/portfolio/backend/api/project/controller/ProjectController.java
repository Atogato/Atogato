package portfolio.backend.api.project.controller;

import com.amazonaws.services.s3.AmazonS3;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import portfolio.backend.api.imageupload.service.ProjectS3Service;
import portfolio.backend.api.project.entity.Project;
import portfolio.backend.api.project.repository.ProjectRepository;

import portfolio.backend.api.project.exception.ResourceNotFoundException; // Import statement for ResourceNotFoundException
import portfolio.backend.authentication.api.entity.user.User;
import portfolio.backend.authentication.api.repository.user.UserRepository;
import portfolio.backend.authentication.api.service.UserService;
import springfox.documentation.annotations.ApiIgnore;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Api(tags = {"Project"})
@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private final ProjectRepository projectRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final AmazonS3 s3Client;
    private final ProjectS3Service projectS3Service;


    @Autowired // project Parameter 생성
    public ProjectController(ProjectRepository projectRepository, UserRepository userRepository, UserService userService, AmazonS3 s3Client, ProjectS3Service projectS3Service) {
        this.projectRepository = projectRepository;
        this.userService = userService;
        this.userRepository = userRepository;
        this.s3Client = s3Client;
        this.projectS3Service = projectS3Service;
    }


    // 특정 프로젝트 PK Param GET
    @GetMapping("/{id}")
    public Project getProjectById(@PathVariable Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ID not found: " + id));
    }

    // GET 프로젝트 시간순
    @GetMapping()
    public List<Project> getAllProjectsByCreatedDate() {
        return projectRepository.findAllByOrderByCreatedDateDesc();
    }

    // GET 프로젝트 데드라인 정렬
    @GetMapping("/sorted")
    public List<Project> getAllProjectsByApplicationDeadline() {
        Sort sort = Sort.by(Sort.Order.asc("applicationDeadline"), Sort.Order.desc("liked"));
        return projectRepository.findByApplicationDeadlineAfter(LocalDate.now(), sort);
    }


    // 새로운 프로젝트 POST
    @PostMapping
    public Project createProject(
            @RequestParam String projectName,
            @RequestParam Project.ProjectCategory projectArtCategory,
            @RequestParam(defaultValue="Unknown") String location,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate projectDeadline,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate applicationDeadline,
            @RequestParam List<Project.RequiredCategory> requiredCategory,
            @RequestParam(defaultValue = "true") Boolean swipeAlgorithm,
            @RequestParam(value = "image") List<MultipartFile> projectImageFiles,
            @RequestParam String description,
            @RequestParam(defaultValue = "true") Boolean ongoingStatus,
            @RequestParam(defaultValue = "both") String remoteStatus,
            @RequestParam(defaultValue = "0") Long requiredPeople,
            @RequestParam(defaultValue = "0") Long participantId,
            @ApiIgnore Authentication authentication) throws IOException {


        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String userId = authentication.getName();

        List<Map<String, Object>> projectImageUrlList = new ArrayList<>();

        for (MultipartFile projectImageFile : projectImageFiles) {
            //업로드된 파일 정보 저장: [1] Map에 저장
            Map<String, Object> projectImageUrls = new HashMap<>();
            String extraKey = projectS3Service.projectSaveUploadFile(projectImageFile);
            URL projectImageUrl = s3Client.getUrl("atogatobucket", extraKey);
            projectImageUrls.put("projectImageUrl", projectImageUrl);
            //[2] 여러 개의 Map을 List에 저장
            projectImageUrlList.add(projectImageUrls);
        }


        Project project = new Project();

        project.setUserId(userId);
        project.setProjectName(projectName);
        project.setProjectArtCategory(projectArtCategory);
        project.setLocation(location);
        project.setCreatedDate(LocalDate.now());
        project.setOngoingStatus(ongoingStatus);
        project.setRemoteStatus(remoteStatus);
        project.setProjectDeadline(projectDeadline);
        project.setApplicationDeadline(applicationDeadline);
        project.setRequiredCategory(requiredCategory);
        project.setRequiredPeople(requiredPeople);
        project.setSwipeAlgorithm(swipeAlgorithm);
        project.setImage(projectImageUrlList.toString());
        project.setDescription(description);

        return projectRepository.save(project);
    }

    // 특정 프로젝트 PUT
    @PutMapping("/{id}")
    public Project updateProject(@PathVariable Long id, @RequestBody Project updatedProject) {
        Project existingProject = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ID not found: " + id));

        existingProject.setProjectName(updatedProject.getProjectName());
        existingProject.setCreatedDate(updatedProject.getCreatedDate());
        existingProject.setProjectArtCategory(updatedProject.getProjectArtCategory());
        existingProject.setLocation(updatedProject.getLocation());
        existingProject.setSwipeAlgorithm(updatedProject.getSwipeAlgorithm());
        existingProject.setImage(updatedProject.getImage());
        existingProject.setRequiredCategory(updatedProject.getRequiredCategory());
        existingProject.setRequiredPeople(updatedProject.getRequiredPeople());
        existingProject.setProjectDeadline(updatedProject.getProjectDeadline());
        existingProject.setApplicationDeadline(updatedProject.getApplicationDeadline());
        existingProject.setOngoingStatus(updatedProject.getOngoingStatus());
        existingProject.setRemoteStatus(updatedProject.getRemoteStatus());
        existingProject.setDescription(updatedProject.getDescription());
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
