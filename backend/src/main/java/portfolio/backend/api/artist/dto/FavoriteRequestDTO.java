package portfolio.backend.api.artist.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FavoriteRequestDTO {

    private String userId;
    private Long artistId;

    public FavoriteRequestDTO(String userId, Long artistId){
        this.userId = userId;
        this.artistId = artistId;
    }

}