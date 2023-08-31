package portfolio.backend.api.artist.services;

import com.amazonaws.services.s3.AmazonS3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import portfolio.backend.api.artist.entity.Artist;
import portfolio.backend.api.artist.entity.ExtraImage;
import portfolio.backend.api.artist.repository.ArtistRepository;
import portfolio.backend.api.imageupload.service.S3Service;
import portfolio.backend.api.project.exception.ResourceNotFoundException;
import portfolio.backend.authentication.api.entity.user.User;
import portfolio.backend.authentication.api.repository.user.UserRepository;
import portfolio.backend.authentication.api.service.UserContextService;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final UserRepository userRepository;
    private final UserContextService userContextService;
    private final S3Service s3Service;
    private final AmazonS3 s3Client;

    @Autowired
    public ArtistService(ArtistRepository artistRepository, UserRepository userRepository,
                         UserContextService userContextService, S3Service s3Service, AmazonS3 s3Client) {
        this.artistRepository = artistRepository;
        this.userRepository = userRepository;
        this.userContextService = userContextService;
        this.s3Service = s3Service;
        this.s3Client = s3Client;
    }

    public List<Artist> findAll() {
        return artistRepository.findAll();
    }

    public Artist findById(Long id) {
        return artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ID 찾을 수 없음 " + id));
    }

    public Artist findByUserId(String userId) {
        return artistRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("유저는 아티스트 등록이 되어 있지 않습니다 "));
    }

    public ResponseEntity<String> createArtist(MultipartFile imageFile, List<MultipartFile> extraImageFiles,
                                               MultipartFile portfolioFile, String artistName, String description,
                                               String location, String selfIntroduction, String creatorArtCategory,
                                               List<String> interestCategories, String snsLink, String birthdate,
                                               Authentication authentication) throws IOException {
        User user = userContextService.getCurrentUser();

        Optional<Artist> existingArtist = artistRepository.findByUserId(user.getUserId());
        if (existingArtist.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("계정의 아티스트가 이미 등록 되어 있습니다.");
        }
        Artist artist = new Artist();

        Set<ExtraImage> extraImageEntities = new HashSet<>();
        System.out.println("extraImageEntities" + extraImageEntities);
        System.out.println("extraImageFiles" + extraImageFiles);
        if (extraImageFiles != null) {

            for (MultipartFile extraImageFile : extraImageFiles) {
                System.out.println("extraImageFile" + extraImageFile);

                try {
                    String extraKey = s3Service.extraSaveUploadFile(extraImageFile);
                    System.out.println("extraKey" + extraKey);

                    URL extraImageUrl = s3Client.getUrl("atogato", extraKey);
                    System.out.println("로그 extraImageUrl" + extraImageUrl);

                    ExtraImage extraImageEntity = new ExtraImage();
                    extraImageEntity.setImageUrl(extraImageUrl.toString());
                    extraImageEntity.setArtist(artist);

                    System.out.println("로그 extraImageEntity" + extraImageEntity);

                    extraImageEntities.add(extraImageEntity);

                    System.out.println("로그 extraImageEntities" + extraImageEntities);

                    artist.setExtraImages(extraImageEntities);
                    System.out.println("로그 artist" + artist.getExtraImages());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        String key = s3Service.saveUploadFile(imageFile);
        URL imageUrl = s3Client.getUrl("atogato", key);

        String portfolioKey = s3Service.portfolioSaveUploadFile(portfolioFile);
        URL portfolioUrl = s3Client.getUrl("atogato", portfolioKey);


        try {
            artist.setMainImage(String.valueOf(imageUrl));
            artist.setPortfolio(String.valueOf(portfolioUrl));
            artist.setSelfIntroduction(selfIntroduction);
            artist.setArtistName(artistName);
            artist.setDescription(description);
            artist.setLocation(location);
            artist.setCreatorArtCategory(creatorArtCategory);
            artist.setSnsLink(snsLink);
            artist.setExtraImages(extraImageEntities);


            if (interestCategories != null && !interestCategories.isEmpty()) {
                artist.setInterestCategories(interestCategories);
            }

            if (birthdate != null) {
                LocalDate birthdateObj = LocalDate.parse(birthdate);
                artist.setBirthdate(birthdateObj);
            }
            artist.setUserId(user.getUserId());

            artistRepository.save(artist);
            return ResponseEntity.ok("아티스트 생성!");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("아티스트 생성 실패ㅠ");
        }

    }
    public ResponseEntity<?> updateArtist(Long id, MultipartFile mainImageFile, List<MultipartFile> extraImageFiles, MultipartFile portfolioFile,
                                           String artistName, String description, String location,
                                           String selfIntroduction, String creatorArtCategory,
                                           List<String> interestCategories, String snsLink, String birthdate) {
        User user = userContextService.getCurrentUser();

        Artist existingArtist = artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ID가 존재하지 않습니다 " + id));
        if (!existingArtist.getUserId().equals(user.getUserId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("현재 로그인 유저의 아티스트가 아닙니다");
        }

        if (creatorArtCategory != null) {
            existingArtist.setCreatorArtCategory(creatorArtCategory);
        }
        if (artistName != null) {
            existingArtist.setArtistName(artistName);
        }
        if (selfIntroduction != null) {
            existingArtist.setSelfIntroduction(selfIntroduction);
        }
        if (location != null) {
            existingArtist.setLocation(location);
        }
        if (description != null) {
            existingArtist.setDescription(description);
        }
        if (interestCategories != null) {
            existingArtist.setInterestCategories(interestCategories);
        }
        if (snsLink != null) {
            existingArtist.setSnsLink(snsLink);
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

        if (extraImageFiles != null && !extraImageFiles.isEmpty()) {
            Set<ExtraImage> extraImageEntities = new HashSet<>();
            for (MultipartFile extraImageFile : extraImageFiles) {
                try {
                    String extraKey = s3Service.extraSaveUploadFile(extraImageFile);
                    URL extraImageUrl = s3Client.getUrl("atogato", extraKey);

                    ExtraImage extraImageEntity = new ExtraImage();
                    extraImageEntity.setImageUrl(extraImageUrl.toString());
                    extraImageEntity.setArtist(existingArtist);
                    extraImageEntities.add(extraImageEntity);
                    existingArtist.setExtraImages(extraImageEntities);

                } catch (IOException e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("S3 파일 업로드 중 실패");
                }
            }
            existingArtist.setExtraImages(extraImageEntities);
        }

        if (birthdate != null) {
            LocalDate birthdateObj = LocalDate.parse(birthdate);
            existingArtist.setBirthdate(birthdateObj);
        }

        return ResponseEntity.ok(artistRepository.save(existingArtist));
    }


    public ResponseEntity<?> deleteArtist(Long id) {
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
