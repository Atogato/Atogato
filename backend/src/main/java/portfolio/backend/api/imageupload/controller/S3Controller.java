package portfolio.backend.api.imageupload.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import portfolio.backend.api.imageupload.service.S3Service;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@ApiIgnore
public class S3Controller {

    private final S3Service s3Service;

    @PostMapping
    public void uploadFile(@RequestParam MultipartFile multipartFile)
            throws IOException {
        s3Service.saveUploadFile(multipartFile);
    }
}
