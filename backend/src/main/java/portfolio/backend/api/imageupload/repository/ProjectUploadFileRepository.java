package portfolio.backend.api.imageupload.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import portfolio.backend.api.imageupload.entity.ProjectUploadFile;
import portfolio.backend.api.imageupload.entity.UploadFile;

public interface ProjectUploadFileRepository extends JpaRepository<ProjectUploadFile, Long> {

}
