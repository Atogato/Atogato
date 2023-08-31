package portfolio.backend.api.artist.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FavoriteRequestDTO {
    private Long artistId;
    public FavoriteRequestDTO( Long artistId){
        this.artistId = artistId;
    }

}