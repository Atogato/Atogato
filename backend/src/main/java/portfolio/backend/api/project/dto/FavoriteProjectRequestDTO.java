package portfolio.backend.api.project.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FavoriteProjectRequestDTO {

    private String userId;
    private Long projectId;

    public FavoriteProjectRequestDTO(String userId, Long projectId){
        this.userId = userId;
        this.projectId = projectId;
    }

}