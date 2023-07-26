package portfolio.backend.api.artist.controller;

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
import portfolio.backend.api.artist.repository.ArtistRepository;
import portfolio.backend.api.project.entity.Project;
import portfolio.backend.api.project.exception.ResourceNotFoundException;
import portfolio.backend.authentication.api.entity.user.User;
import portfolio.backend.authentication.api.repository.user.UserRepository;
import portfolio.backend.authentication.api.service.UserService;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/artists")
@Api(tags = "Artist")
public class ArtistController {
    private final ArtistRepository artistRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public ArtistController(ArtistRepository artistRepository, UserRepository userRepository, UserService userService) {
        this.artistRepository = artistRepository;
        this.userRepository = userRepository;
        this.userService = userService;
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
    @GetMapping("users/{id}")
    public Artist getArtistByUserId(@PathVariable String userId) {
        return artistRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user does not have an artist: "));
    }

    @PostMapping("")
    public ResponseEntity<String> createArtist(@RequestParam(value = "mainImage") MultipartFile imageFile,
                                               @RequestParam(value = "artistName") String artistName,
                                               @RequestParam(value = "description") String description,
                                               @RequestParam(value = "location", required = false) String location,
                                               @RequestParam(value = "creatorArtCategory") String creatorArtCategory,
                                               @RequestParam(value = "interestCategory") String interestCategory,
                                               @RequestParam(value = "snsLink", required = false) String snsLink,
                                               @RequestParam(value = "birthdate", required = false) String birthdate,
                                               @ApiIgnore Authentication authentication) {

        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String userId = authentication.getName();

        try {
            Artist artist = new Artist();
            artist.setMainImage(imageFile.getBytes());
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
            artistRepository.save(artist);
            return ResponseEntity.ok("Artist created successfully.");

        } catch (IOException e) {
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
