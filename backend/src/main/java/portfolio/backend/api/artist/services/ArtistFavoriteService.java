package portfolio.backend.api.artist.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import portfolio.backend.api.artist.dto.FavoriteRequestDTO;
import portfolio.backend.api.artist.entity.Artist;
import portfolio.backend.api.artist.entity.ArtistFavorite;
import portfolio.backend.api.artist.exception.DuplicateResourceException;
import portfolio.backend.api.artist.exception.NotFoundException;
import portfolio.backend.api.artist.repository.ArtistFavoriteRepository;
import portfolio.backend.api.artist.repository.ArtistRepository;
import portfolio.backend.authentication.api.entity.user.User;
import portfolio.backend.authentication.api.repository.user.UserRepository;
import portfolio.backend.authentication.api.service.UserService;

@Service
@RequiredArgsConstructor
public class ArtistFavoriteService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final ArtistFavoriteRepository artistFavoriteRepository;
    private final ArtistRepository artistRepository;

    @Transactional
    public void insert(FavoriteRequestDTO favoriteRequestDTO) throws Exception{
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userService.getUser(principal.getUsername());
        Artist artist = artistRepository.findById(favoriteRequestDTO.getArtistId())
                .orElseThrow(() -> new NotFoundException("아티스트 찾을 수 없음" + favoriteRequestDTO.getArtistId()));

        if (artistFavoriteRepository.findByUserAndArtist(user, artist).isPresent()){

            throw new DuplicateResourceException("이미 좋아요를 한 아티스트입니다" );
        }
        ArtistFavorite artistFavorite = ArtistFavorite.builder()
                .user(user)
                .artist(artist)
                .build();
        artistFavoriteRepository.save(artistFavorite);
    }

    @Transactional
    public void delete(FavoriteRequestDTO favoriteRequestDTO){
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userService.getUser(principal.getUsername());

        Artist artist = artistRepository.findById(favoriteRequestDTO.getArtistId())
                .orElseThrow(() -> new NotFoundException("아티스트 찾을 수 없음" + favoriteRequestDTO.getArtistId()));

        ArtistFavorite artistFavorite = artistFavoriteRepository.findByUserAndArtist(user, artist)
                .orElseThrow(() -> new NotFoundException("즐겨찾기 찾을 수 없음"));

        artistFavoriteRepository.delete(artistFavorite);
    }



}