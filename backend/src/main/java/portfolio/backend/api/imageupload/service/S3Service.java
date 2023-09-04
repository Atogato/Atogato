package portfolio.backend.api.imageupload.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import portfolio.backend.api.artist.repository.ArtistRepository;
import portfolio.backend.api.imageupload.entity.ExtraUploadFile;
import portfolio.backend.api.imageupload.entity.PortfolioUploadFile;
import portfolio.backend.api.imageupload.entity.UploadFile;
import portfolio.backend.api.imageupload.repository.ExtraUploadFileRepository;
import portfolio.backend.api.imageupload.repository.PortfolioUploadFileRepository;
import portfolio.backend.api.imageupload.repository.ProjectUploadFileRepository;
import portfolio.backend.api.imageupload.repository.UploadFileRepository;


import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class S3Service {

    private final AmazonS3Client amazonS3Client;
    private final UploadFileRepository uploadFileRepository;
    private final ExtraUploadFileRepository extraUploadFileRepository;
    private final PortfolioUploadFileRepository portfolioUploadFileRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

    //artist api mainImage 저장
    @Transactional
    public String saveUploadFile(MultipartFile multipartFile) throws IOException {

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        objectMetadata.setContentLength(multipartFile.getSize());

        String originalFilename = multipartFile.getOriginalFilename();
        int index = originalFilename.lastIndexOf(".");
        String ext = originalFilename.substring(index + 1);

        String storeFileName = UUID.randomUUID() + "." + ext;
        String key = "mainImage/" + storeFileName;

        try (InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3Client.putObject(new PutObjectRequest(bucket, key, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        }

        String storeFileUrl = amazonS3Client.getUrl(bucket, key).toString();
        UploadFile uploadFile = new UploadFile(originalFilename, storeFileUrl);
        uploadFileRepository.save(uploadFile);

        return key;
    }

    //artist api addtionalImage 저장
    @Transactional

    public String extraSaveUploadFile(MultipartFile extraMultipartFile) throws IOException {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(extraMultipartFile.getContentType());
        objectMetadata.setContentLength(extraMultipartFile.getSize());

        String extraOriginalFilename = extraMultipartFile.getOriginalFilename();
        int extraIndex = extraOriginalFilename.lastIndexOf(".");
        String extraExt = extraOriginalFilename.substring(extraIndex + 1);

        String extraStoreFileName = UUID.randomUUID() + "." + extraExt;
        String extraKey = "extraImage/" + extraStoreFileName;

        try (InputStream inputStream = extraMultipartFile.getInputStream()) {
            amazonS3Client.putObject(new PutObjectRequest(bucket, extraKey, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        }

        String extraStoreFileUrl = amazonS3Client.getUrl(bucket, extraKey).toString();
        ExtraUploadFile extraUploadFile = new ExtraUploadFile(extraOriginalFilename, extraStoreFileUrl);
        extraUploadFileRepository.save(extraUploadFile);

        return extraKey;
    }

    //artist api portfolio 저장
    @Transactional
    public String portfolioSaveUploadFile(MultipartFile portfolioMultipartFile) throws IOException {

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(portfolioMultipartFile.getContentType());
        objectMetadata.setContentLength(portfolioMultipartFile.getSize());

        String portfolioOriginalFilename = portfolioMultipartFile.getOriginalFilename();
        int index = portfolioOriginalFilename.lastIndexOf(".");
        String ext = portfolioOriginalFilename.substring(index + 1);

        String portfolioStoreFileName = UUID.randomUUID() + "." + ext;
        String portfolioKey = "portfolio/" + portfolioStoreFileName;

        try (InputStream inputStream = portfolioMultipartFile.getInputStream()) {
            amazonS3Client.putObject(new PutObjectRequest(bucket, portfolioKey, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        }

        String portfolioStoreFileUrl = amazonS3Client.getUrl(bucket, portfolioKey).toString();
        PortfolioUploadFile portfolioUploadFile = new PortfolioUploadFile(portfolioOriginalFilename, portfolioStoreFileUrl);
        portfolioUploadFileRepository.save(portfolioUploadFile);

        return portfolioKey;
    }

}