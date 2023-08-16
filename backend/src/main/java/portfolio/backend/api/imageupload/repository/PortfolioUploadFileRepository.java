package portfolio.backend.api.imageupload.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import portfolio.backend.api.imageupload.entity.PortfolioUploadFile;

public interface PortfolioUploadFileRepository extends JpaRepository<PortfolioUploadFile, Long> {

}
