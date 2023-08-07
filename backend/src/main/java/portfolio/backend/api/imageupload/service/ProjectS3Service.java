package portfolio.backend.api.imageupload.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import portfolio.backend.api.imageupload.entity.ProjectUploadFile;
import portfolio.backend.api.imageupload.repository.ProjectUploadFileRepository;
import portfolio.backend.api.project.repository.ProjectRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ProjectS3Service {

    private final AmazonS3 amazonS3;
    private final AmazonS3Client amazonS3Client;
    private final ProjectRepository projectRepository;
    private final ProjectUploadFileRepository projectUploadFileRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    //project api image 저장
    @Transactional
    public String projectSaveUploadFile(MultipartFile ProjectMultipartFile) throws IOException {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(ProjectMultipartFile.getContentType());
        objectMetadata.setContentLength(ProjectMultipartFile.getSize());

        String ProjectOriginalFilename = ProjectMultipartFile.getOriginalFilename();
        int ProjectIndex = ProjectOriginalFilename.lastIndexOf(".");
        String ProjectExt = ProjectOriginalFilename.substring(ProjectIndex + 1);

        String ProjectStoreFileName = UUID.randomUUID() + "." + ProjectExt;
        String ProjectKey = "projectImage/" + ProjectStoreFileName;

        try (InputStream inputStream = ProjectMultipartFile.getInputStream()) {
            amazonS3Client.putObject(new PutObjectRequest(bucket, ProjectKey, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        }

        String ProjectStoreFileUrl = amazonS3Client.getUrl(bucket, ProjectKey).toString();
        ProjectUploadFile projectUploadFile = new ProjectUploadFile(ProjectOriginalFilename, ProjectStoreFileUrl);
        projectUploadFileRepository.save(projectUploadFile);

        return ProjectKey;
    }


}
