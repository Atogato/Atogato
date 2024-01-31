package portfolio.backend.api.artist.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import portfolio.backend.api.artist.entity.Artist;
import portfolio.backend.api.artist.services.ArtistService;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;
import java.util.*;


@RestController
@RequestMapping("/api/artists")
@Api(tags = {"Artist"}, description = "아티스트 생성, 업데이트, 삭제 등의 REST API")
public class ArtistController {

    private final ArtistService artistService;

    @Autowired
    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    
    @GetMapping
    public List<Artist> getAllArtist() {
        return artistService.findAll();
    }

    @GetMapping("/{id}")
    public Artist getArtistById(@PathVariable Long id) {
        return artistService.findById(id);
    }

    @GetMapping("users/{userId}")
    public Artist getArtistByUserId(@PathVariable String userId) {
        return artistService.findByUserId(userId);
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

        return artistService.createArtist(imageFile, extraImageFiles, portfolioFile, artistName, description, location,
                selfIntroduction, creatorArtCategory, interestCategories, snsLink, birthdate, authentication);
    }

    @PatchMapping("/{id}")
    @ApiOperation(value = "아티스트 업데이트")
    public ResponseEntity<?> updateArtist(@ApiParam(value = "업데이트 하려는 유저", required = true) @PathVariable Long id,
                                          @RequestParam(value = "mainImage", required = false) MultipartFile mainImageFile,
                                          @RequestParam(value = "portfolio", required = false) MultipartFile portfolioFile,
                                          @RequestParam(value = "artistName", required = false) String artistName,
                                          @RequestParam(value = "description", required = false) String description,
                                          @RequestParam(value = "location", required = false) String location,
                                          @RequestParam(value = "selfIntroduction", required = false) String selfIntroduction,
                                          @RequestParam(value = "creatorArtCategory", required = false) String creatorArtCategory,
                                          @RequestParam(value = "interestCategories", required = false) List<String> interestCategories,
                                          @RequestParam(value = "snsLink", required = false) String snsLink,
                                          @RequestParam(value = "extraImages", required = false) List<MultipartFile> extraImageFiles,
                                          @RequestParam(value = "birthdate", required = false) String birthdate) {
        return artistService.updateArtist(id, mainImageFile, extraImageFiles, portfolioFile, artistName, description, location,
                selfIntroduction, creatorArtCategory, interestCategories, snsLink, birthdate);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteArtist(@PathVariable Long id) {
        return artistService.deleteArtist(id);
    }
}
