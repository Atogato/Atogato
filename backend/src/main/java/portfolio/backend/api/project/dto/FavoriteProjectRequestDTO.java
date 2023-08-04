package portfolio.backend.api.project.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FavoriteProjectRequestDTO {

    private Long projectId;

    public FavoriteProjectRequestDTO(Long projectId){
        this.projectId = projectId;
    }

}