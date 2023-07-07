package portfolio.backend.api.auth.controller;



import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import portfolio.backend.api.artist.entity.Artist;
import portfolio.backend.api.artist.repository.ArtistRepostiroy;

import java.util.List;


@Controller
@Slf4j //롬복으로 Logger log = LoggerFactory.getLogger(getClass());와 같음 - 로그 뽑는 코드
public class HomeController {
    private final ArtistRepostiroy artistRepository;


    public HomeController(ArtistRepostiroy artistRepository) {
        this.artistRepository = artistRepository;
    }

    @RequestMapping("/") //홈페이지, 메인화면
    public String home() {
        log.info("home controller");
        return "home";
    }

    // create-project 프로젝트 생성 엔드포인트
    @RequestMapping("/create-project")
    public String createProjectForm() {
        return "project";
    }

    @RequestMapping("/create-artist")
    public String createArtistForm() {
        return "artist";
    }

    @GetMapping("/artists")
    public String getArtists(Model model) {
        List<Artist> artists = artistRepository.findAll();
        model.addAttribute("artists", artists);
        return "view-artist";
    }

}
