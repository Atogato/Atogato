package portfolio.backend.api.imageupload.entity;

import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Entity
public class ExtraUploadFile {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String uploadFileName;
    private String storeFileUrl;


    public ExtraUploadFile(String uploadFileName, String storeFileUrl) {
        this.uploadFileName = uploadFileName;
        this.storeFileUrl = storeFileUrl;
    }
}
