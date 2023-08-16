package portfolio.backend.api.artist.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FavoriteResponseDTO {
    private Long artistId;
    private String selfIntroduction;
    private String artistName;
}
