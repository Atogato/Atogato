package portfolio.backend.api.artist;

import com.amazonaws.services.s3.AmazonS3;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import portfolio.backend.authentication.api.entity.user.User;

//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import portfolio.backend.api.artist.controller.ArtistController;
import portfolio.backend.api.artist.entity.Artist;
import portfolio.backend.api.artist.repository.ArtistRepository;
import portfolio.backend.api.imageupload.service.S3Service;
import portfolio.backend.api.project.exception.ResourceNotFoundException;
import portfolio.backend.authentication.api.repository.user.UserRepository;
import portfolio.backend.authentication.api.service.UserService;
import portfolio.backend.authentication.config.properties.AppProperties;

import java.net.URL;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class ArtistControllerTest {

    @InjectMocks
    ArtistController artistController;

    @Mock
    private ArtistRepository artistRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @Mock
    private AmazonS3 s3Client;

    @Mock
    private S3Service s3Service;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(artistController).build();
    }

    @Test
    public void testGetAllArtists() throws Exception {
        Artist artist1 = new Artist();
        artist1.setArtistName("Artist1");

        Artist artist2 = new Artist();
        artist2.setArtistName("Artist2");

        when(artistRepository.findAll()).thenReturn(Arrays.asList(artist1, artist2));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/artists")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].artistName").value("Artist1"))
                .andExpect(jsonPath("$[1].artistName").value("Artist2"));

        verify(artistRepository, times(1)).findAll();
    }

    @Test
    public void testGetArtistByIdFound() throws Exception {
        Artist artist = new Artist();
        artist.setArtistId(1L);
        artist.setArtistName("John Doe");

        when(artistRepository.findById(1L)).thenReturn(Optional.of(artist));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/artists/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.artistId").value(1L))
                .andExpect(jsonPath("$.artistName").value("John Doe"));

        verify(artistRepository, times(1)).findById(1L);
    }
    @Test
    public void testGetArtistByIdNotFound() throws Exception {
        when(artistRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/artists/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(artistRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetArtistByUserIdFound() throws Exception {
        Artist artist = new Artist();
        artist.setUserId("user123");
        artist.setArtistName("John Doe");

        when(artistRepository.findByUserId("user123")).thenReturn(Optional.of(artist));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/artists/users/user123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value("user123"))
                .andExpect(jsonPath("$.artistName").value("John Doe"));

        verify(artistRepository, times(1)).findByUserId("user123");
    }

    @Test
    public void testGetArtistByUserIdNotFound() throws Exception {
        when(artistRepository.findByUserId("user123")).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/artists/users/user123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertEquals("user does not have an artist: ", Objects.requireNonNull(result.getResolvedException()).getMessage()));

        verify(artistRepository, times(1)).findByUserId("user123");
    }


//    @Test
//    public void testCreateArtist() throws Exception {
//        MockMultipartHttpServletRequest mockRequest = new MockMultipartHttpServletRequest();
//        mockRequest.setMethod("POST");
//
//        MockMultipartFile imageFile = new MockMultipartFile("mainImage", "image.jpg", "image/jpeg", "image".getBytes());
//        mockRequest.addFile(imageFile);
//
//        MockMultipartFile portfolioFile = new MockMultipartFile("portfolio", "portfolio.pdf", "application/pdf", "portfolio".getBytes());
//        mockRequest.addFile(portfolioFile);
//
//        MockMultipartFile extraImageFile = new MockMultipartFile("extraImage", "extra-image.jpg", "image/jpeg", "extra-image".getBytes());
//        mockRequest.addFile(extraImageFile);
//
//        mockRequest.setParameter("artistName", "Test Artist");
//        mockRequest.setParameter("description", "Test description");
//        mockRequest.setParameter("selfIntroduction", "Hello, I'm a test artist.");
//        mockRequest.setParameter("creatorArtCategory", "Painting");
//        mockRequest.setParameter("interestCategories", "Category 1,Category 2");
//        mockRequest.setParameter("snsLink", "https://example.com/artist");
//        mockRequest.setParameter("birthdate", "1990-01-01");
//
//        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart("/api/artists")
//                .file(imageFile)
//                .file(portfolioFile)
//                .file(extraImageFile)
//                .param("artistName", "Test Artist")
//                .param("description", "Test description")
//                .param("selfIntroduction", "Hello, I'm a test artist.")
//                .param("creatorArtCategory", "Painting")
//                .param("interestCategories", "Category 1,Category 2")
//                .param("snsLink", "https://example.com/artist")
//                .param("birthdate", "1990-01-01")
//                .contentType(MediaType.MULTIPART_FORM_DATA);
//
//        mockMvc.perform(requestBuilder)
//                .andExpect(status().isOk())
//                .andDo(result -> {
//                    System.out.println(result.getResponse().getContentAsString()); // Print response content for debugging
//                });
//    }
}

