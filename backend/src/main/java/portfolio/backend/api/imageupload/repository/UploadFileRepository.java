package portfolio.backend.api.imageupload.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import portfolio.backend.api.imageupload.entity.UploadFile;

public interface UploadFileRepository extends JpaRepository<UploadFile, Long> {
}
