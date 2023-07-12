package portfolio.backend.api.artist.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FavoriteRequestDTO {

    private Long userId;
    private Long artistId;

    public FavoriteRequestDTO(Long userId, Long artistId){
        this.userId = userId;
        this.artistId = artistId;
    }

<<<<<<< HEAD
}
=======
}
>>>>>>> 875fa295654b6b6edf6aeea850c13e61153075fe
