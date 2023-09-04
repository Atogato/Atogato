package portfolio.backend.api.project.service;

import com.amazonaws.services.s3.AmazonS3;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import portfolio.backend.api.imageupload.service.ProjectS3Service;
import portfolio.backend.api.project.entity.Project;
import portfolio.backend.api.project.entity.ProjectImages;
import portfolio.backend.api.project.exception.ResourceNotFoundException;
import portfolio.backend.api.project.repository.ProjectRepository;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final AmazonS3 s3Client;
    private final ProjectS3Service projectS3Service;


    public ProjectService(ProjectRepository projectRepository, AmazonS3 s3Client, ProjectS3Service projectS3Service) {
        this.projectRepository = projectRepository;
        this.s3Client = s3Client;
        this.projectS3Service = projectS3Service;
    }

    public Project createProject(String userId, String projectName, Project.ProjectCategory projectArtCategory, String location, LocalDate projectDeadline, LocalDate applicationDeadline, List<Project.RequiredCategory> requiredCategory, Boolean swipeAlgorithm, List<MultipartFile> projectImageFiles, String description, Boolean ongoingStatus, String remoteStatus, Long requiredPeople) {
        Project project = new Project();
        Set<ProjectImages> projectImages = new HashSet<>();

        for (MultipartFile projectImageFile : projectImageFiles) {
            try {
                String extraKey = projectS3Service.projectSaveUploadFile(projectImageFile);
                URL projectImageUrl = s3Client.getUrl("atogato", extraKey);

                ProjectImages projectImage = new ProjectImages();
                projectImage.setImageUrl(projectImageUrl.toString());
                projectImage.setProject(project);
                projectImages.add(projectImage);

                project.setProjectImages(projectImages);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

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
        project.setDescription(description);

        return projectRepository.save(project);
    }

    public Project getProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("프로젝트 ID 찾을 수 없음: " + id));
    }

    public List<Project> getAllProjectsByCreatedDate() {
        return projectRepository.findAllByOrderByCreatedDateDesc();
    }

    public List<Project> getAllProjectsByApplicationDeadline() {
        Sort sort = Sort.by(Sort.Order.asc("applicationDeadline"), Sort.Order.desc("liked"));
        return projectRepository.findByApplicationDeadlineAfter(LocalDate.now(), sort);
    }

    public ResponseEntity<?> updateProject(Long id, String projectName, Project.ProjectCategory projectArtCategory, String location, LocalDate projectDeadline, LocalDate applicationDeadline, List<Project.RequiredCategory> requiredCategory, Boolean swipeAlgorithm, List<MultipartFile> projectImageFiles, String description, Boolean ongoingStatus, String remoteStatus, Long requiredPeople, String currentUserId) {
        Project existingProject = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("프로젝트 ID 찾을 수 없음: " + id));

        if (!existingProject.getUserId().equals(currentUserId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("프로젝트 관리자가 아닙니다");
        }

        if (projectName != null) {
            existingProject.setProjectName(projectName);
        }
        if (projectArtCategory != null) {
            existingProject.setProjectArtCategory(projectArtCategory);
        }
        if (location != null) {
            existingProject.setLocation(location);
        }
        if (projectDeadline != null) {
            existingProject.setProjectDeadline(projectDeadline);
        }
        if (applicationDeadline != null) {
            existingProject.setApplicationDeadline(applicationDeadline);
        }
        if (requiredPeople != null) {
            existingProject.setRequiredPeople(requiredPeople);
        }
        if (requiredCategory != null) {
            existingProject.setRequiredCategory(requiredCategory);
        }
        if (swipeAlgorithm != null) {
            existingProject.setSwipeAlgorithm(swipeAlgorithm);
        }
        if (description != null) {
            existingProject.setDescription(description);
        }
        if (ongoingStatus != null) {
            existingProject.setOngoingStatus(ongoingStatus);
        }
        if (remoteStatus != null) {
            existingProject.setRemoteStatus(remoteStatus);
        }

        if (projectImageFiles != null) {
            Set<ProjectImages> projectImages = new HashSet<>();
            for (MultipartFile projectImageFile : projectImageFiles) {
                try {
                    String extraKey = projectS3Service.projectSaveUploadFile(projectImageFile);
                    URL projectImageUrl = s3Client.getUrl("atogato", extraKey);

                    ProjectImages projectImage = new ProjectImages();
                    projectImage.setImageUrl(projectImageUrl.toString());
                    projectImage.setProject(existingProject);
                    projectImages.add(projectImage);
                    existingProject.setProjectImages(projectImages);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            existingProject.setProjectImages(projectImages);
        }
        return ResponseEntity.ok(projectRepository.save(existingProject));
    }


    public ResponseEntity<?> deleteProject(Long id, String currentUserId) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("프로젝트 ID 찾을 수 없음: " + id));
        if (!project.getUserId().equals(currentUserId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("프로젝트 관리자가 아닙니다");
        }

        projectRepository.delete(project);
        return ResponseEntity.ok("프로젝트 삭제 성공");
    }
}
