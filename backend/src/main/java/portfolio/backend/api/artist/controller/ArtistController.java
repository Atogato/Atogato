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
import portfolio.backend.authentication.api.repository.user.UserRepository;
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
                .orElseThrow(() -> new ResourceNotFoundException("ID not found: " + id));

    }

    // userId 일치 아티스트 GET
    @GetMapping("users/{userId}")
    public Artist getArtistByUserId(@PathVariable String userId) {
        return artistRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user does not have an artist: "));
    }

    @PostMapping("")
    public ResponseEntity<String> createArtist(@RequestParam(value = "mainImage") MultipartFile imageFile,
                                               @RequestParam(value = "extraImage") List<MultipartFile> extraImageFiles,
                                               @RequestParam(value = "portfolio") MultipartFile portfolioFile,
                                               @RequestParam(value = "artistName") String artistName,
                                               @RequestParam(value = "description") String description,
                                               @RequestParam(value = "location", required = false) String location,
                                               @RequestParam(value = "selfIntroduction") String selfIntroduction,
                                               @RequestParam(value = "creatorArtCategory") String creatorArtCategory,
                                               @RequestParam(value = "interestCategory") String interestCategory,
                                               @RequestParam(value = "snsLink", required = false) String snsLink,
                                               @RequestParam(value = "birthdate", required = false) String birthdate,
                                               @ApiIgnore Authentication authentication) throws IOException {

        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String userId = authentication.getName();



        Optional<Artist> existingArtist = artistRepository.findByUserId(userId);
        if (existingArtist.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("계정의 아티스트가 이미 등록 되어 있습니다.");
        }

        String key = s3Service.saveUploadFile(imageFile);
        URL imageUrl = s3Client.getUrl("atogato", key);

        List<ExtraImage> extraImageEntities = new ArrayList<>();


        for (MultipartFile extraImageFile : extraImageFiles) {
            String extraImageUrl = s3Service.extraSaveUploadFile(extraImageFile);
            ExtraImage extraImageEntity = new ExtraImage();
            extraImageEntity.setImageUrl(extraImageUrl);
            extraImageEntities.add(extraImageEntity);
        }

        String portfolioKey = s3Service.portfolioSaveUploadFile(portfolioFile);
        URL portfolioUrl = s3Client.getUrl("atogatobucket", portfolioKey);



        try {
            Artist artist = new Artist();
            //이미지 한 장 저장
            artist.setMainImage(String.valueOf(imageUrl));
            //이미지 여러 장 저장

            artist.setExtraImages(extraImageEntities);


            //portfolio file 첨부
            artist.setPortfolio(String.valueOf(portfolioUrl));
            artist.setSelfIntroduction(selfIntroduction);
            artist.setArtistName(artistName);
            artist.setDescription(description);
            artist.setLocation(location);
            artist.setCreatorArtCategory(creatorArtCategory);
            artist.setSnsLink(snsLink);

            if (interestCategory != null) {
                artist.setInterestCategory(interestCategory);
            }

            if (birthdate != null) {
                LocalDate birthdateObj = LocalDate.parse(birthdate);
                artist.setBirthdate(birthdateObj);
            }
            artist.setUserId(userId);

            for (ExtraImage extraImage : extraImageEntities) {
                extraImage.setArtist(artist);
            }

            artistRepository.save(artist);
            return ResponseEntity.ok("Artist created successfully.");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create artist.");
        }
    }
    @PutMapping("/{id}")
    @ApiOperation(value = "아티스트 업데이트")
    public Artist updateArtist(@ApiParam(value = "업데이트 하려는 유저", required = true)@PathVariable Long id,
                               @RequestBody Artist updateArtist) {

        Artist existingArtist = artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ID not found: " + id));

        existingArtist.setCreatorArtCategory(updateArtist.getCreatorArtCategory());
        existingArtist.setArtistName(updateArtist.getArtistName());
        existingArtist.setSelfIntroduction(updateArtist.getSelfIntroduction());
        existingArtist.setLocation(updateArtist.getLocation());
        existingArtist.setDescription(updateArtist.getDescription());
        existingArtist.setBirthdate(updateArtist.getBirthdate());
        existingArtist.setInterestCategory(updateArtist.getInterestCategory());
        existingArtist.setSnsLink(updateArtist.getSnsLink());
        return artistRepository.save(existingArtist);

    }

    @DeleteMapping("/{id}")
    public void deleteArtist(@PathVariable Long id) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ID not found: " + id));
        artistRepository.delete(artist);
    }

}
