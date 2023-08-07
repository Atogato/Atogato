package portfolio.backend.api.imageupload.entity;

import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Entity
public class UploadFile {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String uploadFileName;
    private String storeFileUrl;


    public UploadFile(String uploadFileName, String storeFileUrl) {
        this.uploadFileName = uploadFileName;
        this.storeFileUrl = storeFileUrl;
    }
}
