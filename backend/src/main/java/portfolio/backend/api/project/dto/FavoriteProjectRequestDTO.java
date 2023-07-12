package portfolio.backend.api.project.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FavoriteProjectRequestDTO {

    private Long userId;
    private Long projectId;

    public FavoriteProjectRequestDTO(Long userId, Long projectId){
        this.userId = userId;
        this.projectId = projectId;
    }

}