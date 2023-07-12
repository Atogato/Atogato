package portfolio.backend.api.artist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import portfolio.backend.api.artist.entity.Artist;
<<<<<<< HEAD
import portfolio.backend.api.artist.entity.ArtistRepository;
=======
import portfolio.backend.api.artist.repository.ArtistRepository;
import portfolio.backend.api.project.entity.Project;
import portfolio.backend.api.project.exception.ResourceNotFoundException;
>>>>>>> 875fa295654b6b6edf6aeea850c13e61153075fe
import portfolio.backend.authentication.api.entity.user.User;
import portfolio.backend.authentication.api.repository.user.UserRepository;
import portfolio.backend.authentication.api.service.UserService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/artists")
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

    @GetMapping("/{id}")
    public Artist getArtistById(@PathVariable Long id) {
//        Optional<Artist> optionalArtist = artistRepository.findById(id);
//        return optionalArtist.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
        return artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ID not found: " + id));

    }

    @PostMapping("")
    public ResponseEntity<String> createArtist(@RequestParam(value = "mainImage") MultipartFile imageFile,
                                               @RequestParam(value = "artistName") String artistName,
                                               @RequestParam(value = "description") String description,
                                               @RequestParam(value = "liked", defaultValue = "0") int liked,
                                               @RequestParam(value = "creatorArtCategory") String creatorArtCategory,
                                               @RequestParam(value = "interestCategory", required = false) String interestCategory,
                                               @RequestParam(value = "snsLink", required = false) String snsLink,
                                               @RequestParam(value = "birthdate", required = false) String birthdate,
                                               Authentication authentication) {

        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userService.getUser(principal.getUsername());

        try {
            Artist artist = new Artist();
            artist.setMainImage(imageFile.getBytes());
            artist.setArtistName(artistName);
            artist.setDescription(description);
            artist.setLiked(liked);
            artist.setCreatorArtCategory(creatorArtCategory);
            artist.setSnsLink(snsLink);

            if (interestCategory != null) {
                artist.setInterestCategory(interestCategory);
            }

            if (birthdate != null) {
                LocalDate birthdateObj = LocalDate.parse(birthdate);
                artist.setBirthdate(birthdateObj);
            }

            artist.setUser(user);
            artistRepository.save(artist);
            return ResponseEntity.ok("Artist created successfully.");

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create artist.");
        }
    }
    @PutMapping("/{id}")
    public Artist updateArtist(@PathVariable Long id, @RequestBody Artist updateArtist) {
        Artist existingArtist = artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ID not found: " + id));

        existingArtist.setCreatorArtCategory(updateArtist.getCreatorArtCategory());
        existingArtist.setArtistName(updateArtist.getArtistName());
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
