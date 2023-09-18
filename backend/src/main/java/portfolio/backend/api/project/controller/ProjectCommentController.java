package portfolio.backend.api.project.controller;


import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import portfolio.backend.api.project.entity.Project;
import portfolio.backend.api.project.entity.ProjectComment;
import portfolio.backend.api.project.exception.ResourceNotFoundException;
import portfolio.backend.api.project.repository.ProjectCommentRepository;
import portfolio.backend.api.project.repository.ProjectRepository;
import portfolio.backend.api.project.service.ProjectCommentService;
import portfolio.backend.authentication.api.service.UserContextService;
import springfox.documentation.annotations.ApiIgnore;

import javax.xml.stream.events.Comment;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/projects/comments")
@Api(tags = {"Project Comment"})
public class ProjectCommentController {
    private final ProjectCommentRepository projectCommentRepository;
    private final ProjectCommentService projectCommentService;
    private final ProjectRepository projectRepository;
    private final UserContextService userContextService;

    @Autowired
    public ProjectCommentController(UserContextService userContextService, ProjectCommentRepository projectCommentRepository, ProjectCommentService projectCommentService, ProjectRepository projectRepository) {
        this.projectCommentRepository = projectCommentRepository;
        this.projectCommentService = projectCommentService;
        this.projectRepository = projectRepository;
        this.userContextService = userContextService;

    }

    @GetMapping("/{projectId}")
    public List<ProjectComment> getCommentsByProjectId(@PathVariable Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("프로젝트 ID를 차지 못했습니다: " + projectId));
        List<ProjectComment> sortedComments = project.getComments();
        sortedComments.sort(Comparator.comparing(ProjectComment::getCreatedDate));
        return sortedComments;
    }

@PostMapping
    public ProjectComment createComment(@RequestParam("projectId") Long projectId,
                                        @RequestParam("comment") String comment,
                                        @ApiIgnore Authentication authentication) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("프로젝트 ID를 차지 못했습니다: " + projectId));

        String currentUserId = userContextService.getCurrentUser().getUserId();

        ProjectComment newProjectComment = new ProjectComment();
        newProjectComment.setCommentUserId(currentUserId);
        newProjectComment.setComment(comment);
        project.getComments().add(newProjectComment);
        newProjectComment.setProject(project);
        newProjectComment.setCreatedDate(LocalDateTime.now());
    projectRepository.save(project);

        return newProjectComment;

    }

    @PatchMapping("/{id}")
    public ProjectComment updateComment(@PathVariable Long id,  @RequestParam("comment") String comment) {
        ProjectComment existingComment = projectCommentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("아이디 찾을 수 없습니다: " + id));

        String currentUserId = userContextService.getCurrentUser().getUserId();

        if (!existingComment.getCommentUserId().equals(currentUserId)) {
            throw new ResourceNotFoundException("유저는 이 댓글의 소유자가 아닙니다: " + id);
        }

        existingComment.setComment(comment);
        return projectCommentRepository.save(existingComment);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id) {
        ProjectComment projectComment = projectCommentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("아이디를 찾을 수 없습니다 " + id));

        String currentUserId = userContextService.getCurrentUser().getUserId();

        if (!projectComment.getCommentUserId().equals(currentUserId)) {
            throw new ResourceNotFoundException("유저는 이 댓글의 소유자가 아닙니다: " + id);
        }

        Project project = projectComment.getProject();
        if (project == null) {
            throw new ResourceNotFoundException("프로젝트를 찾을 수 없습니다: " + id);
        }

        project.getComments().remove(projectComment);

        projectCommentRepository.delete(projectComment);
    }
}
