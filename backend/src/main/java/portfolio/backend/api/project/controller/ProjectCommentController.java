package portfolio.backend.api.project.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import portfolio.backend.api.project.entity.ProjectComment;
import portfolio.backend.api.project.exception.ResourceNotFoundException;
import portfolio.backend.api.project.repository.ProjectCommentRepository;
import portfolio.backend.api.project.repository.ProjectRepository;
import portfolio.backend.api.project.service.ProjectCommentService;

import javax.xml.stream.events.Comment;
import java.util.List;

@RestController
@RequestMapping("api/projects/comments")
public class ProjectCommentController {
    private final ProjectCommentRepository projectCommentRepository;
    private final ProjectCommentService projectCommentService;

    @Autowired
    public ProjectCommentController(ProjectCommentRepository projectCommentRepository, ProjectCommentService projectCommentService) {
        this.projectCommentRepository = projectCommentRepository;
        this.projectCommentService = projectCommentService;
    }

    @GetMapping
    public List<ProjectComment> getAllComments() {
        return projectCommentRepository.findAll();
    }

    @GetMapping("/{id}")
    public ProjectComment getCommentById(@PathVariable Long id) {
        return projectCommentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ID not found: " + id));
    }

    @PostMapping
    public ProjectComment createComment(@RequestParam("projectId") Long projectId,
                                        @RequestParam("artistId") String artistId,
                                        @RequestParam("comment") String comment) {
        ProjectComment newProjectComment = new ProjectComment();
        newProjectComment.setProjectId(projectId);
        newProjectComment.setArtistId(artistId);
        newProjectComment.setComment(comment);
        return projectCommentService.createComment(newProjectComment);
    }

    @PutMapping("/{id}")
    public ProjectComment updateComment(@PathVariable Long id, @RequestBody ProjectComment updatedComment) {
        ProjectComment existingComment = projectCommentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ID not found: " + id));

        existingComment.setComment(updatedComment.getComment());
        return projectCommentRepository.save(existingComment);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id) {
        ProjectComment projectComment = projectCommentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ID not found: " + id));
        projectCommentRepository.delete(projectComment);
    }
}
