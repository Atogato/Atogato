package portfolio.backend.api.artist.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import portfolio.backend.api.artist.dto.FavoriteRequestDTO;
import portfolio.backend.api.artist.dto.FavoriteResponseDTO;
import portfolio.backend.api.artist.entity.Artist;
import portfolio.backend.api.artist.entity.ArtistFavorite;
import portfolio.backend.api.artist.exception.DuplicateResourceException;
import portfolio.backend.api.artist.exception.NotFoundException;
import portfolio.backend.api.artist.repository.ArtistFavoriteRepository;
import portfolio.backend.api.artist.repository.ArtistRepository;
import portfolio.backend.authentication.api.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArtistFavoriteService {
    private final UserService userService;
    private final ArtistFavoriteRepository artistFavoriteRepository;
    private final ArtistRepository artistRepository;


    public List<FavoriteResponseDTO> findAllByUserId(String userId) {
        List<ArtistFavorite> artistFavorites = artistFavoriteRepository.findAllByUserId(userId);
        return artistFavorites.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void insert(FavoriteRequestDTO favoriteRequestDTO) throws Exception{
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = principal.getUsername();

        Artist artist = artistRepository.findById(favoriteRequestDTO.getArtistId())
                .orElseThrow(() -> new NotFoundException("아티스트 찾을 수 없음" + favoriteRequestDTO.getArtistId()));

        if (artistFavoriteRepository.findByUserIdAndArtist(userId, artist).isPresent()){
            throw new DuplicateResourceException("이미 좋아요를 한 아티스트입니다" );
        }
        ArtistFavorite artistFavorite = ArtistFavorite.builder()
                .userId(userId)
                .artist(artist)
                .build();

        artist.setLiked(artist.getLiked() + 1);
        artistRepository.save(artist);

        artistFavoriteRepository.save(artistFavorite);
    }

    @Transactional
    public void delete(FavoriteRequestDTO favoriteRequestDTO){
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = principal.getUsername();

        Artist artist = artistRepository.findById(favoriteRequestDTO.getArtistId())
                .orElseThrow(() -> new NotFoundException("아티스트 찾을 수 없음" + favoriteRequestDTO.getArtistId()));

        ArtistFavorite artistFavorite = artistFavoriteRepository.findByUserIdAndArtist(userId, artist)
                .orElseThrow(() -> new NotFoundException("즐겨찾기 찾을 수 없음"));

        artist.setLiked(artist.getLiked() - 1);
        artistRepository.save(artist);

        artistFavoriteRepository.delete(artistFavorite);
    }

    private FavoriteResponseDTO convertToDTO(ArtistFavorite artistFavorite) {
        FavoriteResponseDTO dto = new FavoriteResponseDTO();
        dto.setArtistId(artistFavorite.getArtist().getArtistId());

        Artist artist = artistRepository.findById(dto.getArtistId())
                .orElseThrow(() -> new NotFoundException("아티스트 찾을 수 없음 " + dto.getArtistId()));

        dto.setSelfIntroduction(artist.getSelfIntroduction());
        dto.setArtistName(artist.getArtistName());

        return dto;
    }

}