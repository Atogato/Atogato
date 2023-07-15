package portfolio.backend.api.artist.services;

import org.springframework.stereotype.Service;
import portfolio.backend.api.artist.entity.Artist;
import portfolio.backend.api.artist.repository.ArtistRepository;

import java.time.LocalDate;

@Service
public class ArtistService {
    private final ArtistRepository artistRepository;

    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public Artist createArtist(Artist artist){
        return artistRepository.save(artist);
    }
}