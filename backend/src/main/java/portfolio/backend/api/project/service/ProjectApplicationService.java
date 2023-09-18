package portfolio.backend.api.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import portfolio.backend.api.messenge.exception.UnauthorizedAccessException;
import portfolio.backend.api.project.entity.Project;
import portfolio.backend.api.project.entity.ProjectApplication;
import portfolio.backend.api.project.exception.ResourceNotFoundException;
import portfolio.backend.api.project.repository.ProjectApplicationRepository;
import portfolio.backend.api.project.repository.ProjectRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectApplicationService {

    private final ProjectApplicationRepository projectApplicationRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectApplicationService(ProjectRepository projectRepository, ProjectApplicationRepository projectApplicationRepository) {
        this.projectApplicationRepository = projectApplicationRepository;
        this.projectRepository = projectRepository;
    }

    public ProjectApplication apply(Long projectId, String currentUserId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("프로젝트가 존재하지 않습니다 : " + projectId));

        List<ProjectApplication> existingApplications = projectApplicationRepository.findByAppliedArtistId(currentUserId);
        if (existingApplications.stream().anyMatch(application -> application.getProject().getProjectId().equals(projectId))) {
            throw new IllegalArgumentException("이미 이 프로젝트에 지원했습니다: " + projectId);
        }

        ProjectApplication application = new ProjectApplication();
        application.setProject(project);
        application.setAppliedArtistId(currentUserId);
        return projectApplicationRepository.save(application);
    }

    public List<ProjectApplication> getApplicationsForMyProjects(String currentUserId) {
        List<Project> myProjects = projectRepository.findByUserId(currentUserId);

        return myProjects.stream()
                .flatMap(project -> project.getApplications().stream())
                .collect(Collectors.toList());
    }

    public List<ProjectApplication> getMyApplications(String currentUserId) {
        return projectApplicationRepository.findByAppliedArtistId(currentUserId);
    }


    public List<Long> getAcceptedProjectsForUser(String userId) {
        return projectApplicationRepository.findByAppliedArtistIdAndApplicationStatus(userId, ProjectApplication.ApplicationStatus.ACCEPTED)
                .stream()
                .map(application -> application.getProject().getProjectId())
                .collect(Collectors.toList());
    }


    public ProjectApplication updateApplicationStatus(Long applicationId, ProjectApplication.ApplicationStatus newStatus, String currentUserId) {
        ProjectApplication application = projectApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("지원서 찾을수 없음: " + applicationId));


        Project relatedProject = application.getProject();

        if (!relatedProject.getUserId().equals(currentUserId)) {
            throw new UnauthorizedAccessException("수정 권한이 없는 지원서입니다");
        }

        application.setApplicationStatus(newStatus);
        return projectApplicationRepository.save(application);
    }

    public void deleteMyApplication(Long applicationId, String currentUserId) {
        ProjectApplication application = projectApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("지원서 찾을 수 없음: " + applicationId));

        if (!application.getAppliedArtistId().equals(currentUserId)) {
            throw new UnauthorizedAccessException("삭제 권한이 없는 유저입니다");
        }

        projectApplicationRepository.delete(application);
    }
}
