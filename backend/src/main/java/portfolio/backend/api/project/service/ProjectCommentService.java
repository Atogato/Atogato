package portfolio.backend.api.project.service;


import org.springframework.stereotype.Service;
import portfolio.backend.api.project.entity.ProjectComment;
import portfolio.backend.api.project.repository.ProjectCommentRepository;

import java.time.LocalDateTime;

@Service
public class ProjectCommentService {
    private final ProjectCommentRepository projectCommentRepository;

    public ProjectCommentService(ProjectCommentRepository projectCommentRepository){
        this.projectCommentRepository = projectCommentRepository;
    }

    public ProjectComment createComment(ProjectComment projectComment){
        projectComment.setCreatedDate(LocalDateTime.now());
        return projectCommentRepository.save(projectComment);
    }
}
