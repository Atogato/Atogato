package portfolio.backend.api.imageupload.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import portfolio.backend.api.imageupload.entity.ExtraUploadFile;
import portfolio.backend.api.imageupload.entity.ProjectUploadFile;

public interface ExtraUploadFileRepository extends JpaRepository<ExtraUploadFile, Long> {

}
