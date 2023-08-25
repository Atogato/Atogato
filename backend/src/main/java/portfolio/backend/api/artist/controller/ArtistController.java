package portfolio.backend.api.artist.controller;

import com.amazonaws.services.s3.AmazonS3;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import portfolio.backend.api.artist.entity.Artist;
import portfolio.backend.api.artist.entity.ExtraImage;
import portfolio.backend.api.artist.repository.ArtistRepository;
import portfolio.backend.api.imageupload.service.S3Service;
import portfolio.backend.api.project.exception.ResourceNotFoundException;
import portfolio.backend.authentication.api.entity.user.User;
import portfolio.backend.authentication.api.repository.user.UserRepository;
import portfolio.backend.authentication.api.service.UserContextService;
import portfolio.backend.authentication.api.service.UserService;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/artists")
@Api(tags = {"Artist"}, description = "아티스트 생성, 업데이트, 삭제 등의 REST API")
public class ArtistController {
    private final ArtistRepository artistRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final AmazonS3 s3Client;
    private final S3Service s3Service;

    @Autowired
    private UserContextService userContextService;

    @Autowired
    public ArtistController(ArtistRepository artistRepository, UserRepository userRepository, UserService userService, AmazonS3 s3Client, S3Service s3Service) {
        this.artistRepository = artistRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.s3Client = s3Client;
        this.s3Service = s3Service;
    }

    @GetMapping
    public List<Artist> getAllArtist() {
        return artistRepository.findAll();
    }

    // 아티스트id 일치 아티스트 GET
    @GetMapping("/{id}")
    public Artist getArtistById(@PathVariable Long id) {
        return artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ID 찾을 수 없음 " + id));

    }

    // userId 일치 아티스트 GET
    @GetMapping("users/{userId}")
    public Artist getArtistByUserId(@PathVariable String userId) {
        return artistRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("유저는 아티스트 등록이 되어 있지 않습니다 "));
    }

    @PostMapping("")
    public ResponseEntity<String> createArtist(@RequestParam(value = "mainImage") MultipartFile imageFile,
                                               @RequestParam(value = "extraImage", required = false) List<MultipartFile> extraImageFiles,
                                               @RequestParam(value = "portfolio") MultipartFile portfolioFile,
                                               @RequestParam(value = "artistName") String artistName,
                                               @RequestParam(value = "description") String description,
                                               @RequestParam(value = "location", required = false) String location,
                                               @RequestParam(value = "selfIntroduction") String selfIntroduction,
                                               @RequestParam(value = "creatorArtCategory") String creatorArtCategory,
                                               @RequestParam(value = "interestCategory") List<String> interestCategories,
                                               @RequestParam(value = "snsLink", required = false) String snsLink,
                                               @RequestParam(value = "birthdate", required = false) String birthdate,
                                               @ApiIgnore Authentication authentication) throws IOException {

        User user = userContextService.getCurrentUser();

        Optional<Artist> existingArtist = artistRepository.findByUserId(user.getUserId());
        if (existingArtist.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("계정의 아티스트가 이미 등록 되어 있습니다.");
        }

        String key = s3Service.saveUploadFile(imageFile);
        URL imageUrl = s3Client.getUrl("atogato", key);
        List<ExtraImage> extraImageEntities = new ArrayList<>();

        if (extraImageFiles != null && !extraImageFiles.isEmpty()) {
            for (MultipartFile extraImageFile : extraImageFiles) {
                String extraImageUrl = s3Service.extraSaveUploadFile(extraImageFile);
                ExtraImage extraImageEntity = new ExtraImage();
                extraImageEntity.setImageUrl(extraImageUrl);
                extraImageEntities.add(extraImageEntity);
            }
        }

        String portfolioKey = s3Service.portfolioSaveUploadFile(portfolioFile);
        URL portfolioUrl = s3Client.getUrl("atogato", portfolioKey);


        try {
            Artist artist = new Artist();
            artist.setMainImage(String.valueOf(imageUrl));
            artist.setExtraImages(extraImageEntities);
            artist.setPortfolio(String.valueOf(portfolioUrl));
            artist.setSelfIntroduction(selfIntroduction);
            artist.setArtistName(artistName);
            artist.setDescription(description);
            artist.setLocation(location);
            artist.setCreatorArtCategory(creatorArtCategory);
            artist.setSnsLink(snsLink);

            if (interestCategories != null && !interestCategories.isEmpty()) {
                artist.setInterestCategories(interestCategories);
            }

            if (birthdate != null) {
                LocalDate birthdateObj = LocalDate.parse(birthdate);
                artist.setBirthdate(birthdateObj);
            }
            artist.setUserId(user.getUserId());

            if (!extraImageEntities.isEmpty()) {
                for (ExtraImage extraImage : extraImageEntities) {
                    extraImage.setArtist(artist);
                }
            }
            artistRepository.save(artist);
            return ResponseEntity.ok("아티스트 생성!");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("아티스트 생성 실패ㅠ");
        }
    }
    @PatchMapping("/{id}")
    @ApiOperation(value = "아티스트 업데이트")
    public ResponseEntity<?> updateArtist(@ApiParam(value = "업데이트 하려는 유저", required = true)@PathVariable Long id,
                                          @RequestParam(value = "mainImage", required = false) MultipartFile mainImageFile,
                                          @RequestParam(value = "portfolio", required = false) MultipartFile portfolioFile,
                                          @RequestParam Map<String, Object> updates) {
        User user = userContextService.getCurrentUser();

        Artist existingArtist = artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ID가 존재하지 않습니다 " + id));
        if (!existingArtist.getUserId().equals(user.getUserId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("현재 로그인 유저의 아티스트가 아닙니다");
        }

        if (updates.containsKey("creatorArtCategory")) {
            existingArtist.setCreatorArtCategory((String) updates.get("creatorArtCategory"));
        }
        if (updates.containsKey("artistName")) {
            existingArtist.setArtistName((String) updates.get("artistName"));
        }
        if (updates.containsKey("selfIntroduction")) {
            existingArtist.setSelfIntroduction((String) updates.get("selfIntroduction"));
        }
        if (updates.containsKey("location")) {
            existingArtist.setLocation((String) updates.get("location"));
        }
        if (updates.containsKey("description")) {
            existingArtist.setDescription((String) updates.get("description"));
        }
        if (updates.containsKey("interestCategories")) {
            List<String> newInterestCategories = (List<String>) updates.get("interestCategories");
            existingArtist.setInterestCategories(newInterestCategories);
        }
        if (updates.containsKey("snsLink")) {
            existingArtist.setSnsLink((String) updates.get("snsLink"));
        }
        try {
            if (mainImageFile != null) {
                String key = s3Service.saveUploadFile(mainImageFile);
                URL imageUrl = s3Client.getUrl("atogato", key);
                existingArtist.setMainImage(String.valueOf(imageUrl));
            }
            if (portfolioFile != null) {
                String portfolioKey = s3Service.portfolioSaveUploadFile(portfolioFile);
                URL portfolioUrl = s3Client.getUrl("atogato", portfolioKey);
                existingArtist.setPortfolio(String.valueOf(portfolioUrl));
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("S3 파일 업로드 중 실패");
        }

        if (updates.containsKey("birthdate")) {
            LocalDate birthdateObj = LocalDate.parse((String) updates.get("birthdate"));
            existingArtist.setBirthdate(birthdateObj);
        }
        return ResponseEntity.ok(artistRepository.save(existingArtist));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteArtist(@PathVariable Long id) {
        User user = userContextService.getCurrentUser();

        Artist artistToDelete = artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ID가 존재하지 않습니다 " + id));
        if (!artistToDelete.getUserId().equals(user.getUserId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("현재 로그인 유저의 아티스트가 아닙니다");
        }

        artistRepository.delete(artistToDelete);
        return ResponseEntity.ok("아티스트 삭제 성공!");
    }
}
