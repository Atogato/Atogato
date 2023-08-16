package portfolio.backend.api.project.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class FavoriteProjectResponseDTO {
    private Long projectId;
    private String description;
    private String projectName;
    private LocalDate projectDeadline;
    private LocalDate applicationDeadline;
}
