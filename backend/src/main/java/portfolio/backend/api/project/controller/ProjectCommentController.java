package portfolio.backend.api.project.controller;


import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import portfolio.backend.api.project.entity.ProjectComment;
import portfolio.backend.api.project.exception.ResourceNotFoundException;
import portfolio.backend.api.project.repository.ProjectCommentRepository;
import portfolio.backend.api.project.repository.ProjectRepository;
import portfolio.backend.api.project.service.ProjectCommentService;
import springfox.documentation.annotations.ApiIgnore;

import javax.xml.stream.events.Comment;
import java.util.List;

@RestController
@RequestMapping("api/projects/comments")
@Api(tags = {"Project Comment"})
public class ProjectCommentController {
    private final ProjectCommentRepository projectCommentRepository;
    private final ProjectCommentService projectCommentService;

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectCommentController(ProjectCommentRepository projectCommentRepository, ProjectCommentService projectCommentService, ProjectRepository projectRepository) {
        this.projectCommentRepository = projectCommentRepository;
        this.projectCommentService = projectCommentService;
        this.projectRepository = projectRepository;
    }

    @GetMapping("/{projectId}")
    public List<ProjectComment> getCommentsByProjectId(@PathVariable Long projectId) {
        return projectCommentRepository.findByProjectId(projectId);
    }

    @PostMapping
    public ProjectComment createComment(@RequestParam("projectId") Long projectId,
                                        @RequestParam("comment") String comment,
                                        @ApiIgnore Authentication authentication) {
        if (!projectRepository.existsById(projectId)) {
            throw new ResourceNotFoundException("프로젝트 ID를 차지 못했습니다: " + projectId);
        }

        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = authentication.getName();

        ProjectComment newProjectComment = new ProjectComment();
        newProjectComment.setProjectId(projectId);
        newProjectComment.setCommentUserId(userId);
        newProjectComment.setComment(comment);
        return projectCommentService.createComment(newProjectComment);
    }

    @PatchMapping("/{id}")
    public ProjectComment updateComment(@PathVariable Long id,  @RequestParam("comment") String comment) {
        ProjectComment existingComment = projectCommentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ID not found: " + id));

        existingComment.setComment(comment);
        return projectCommentRepository.save(existingComment);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id) {
        ProjectComment projectComment = projectCommentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ID not found: " + id));
        projectCommentRepository.delete(projectComment);
    }
}
