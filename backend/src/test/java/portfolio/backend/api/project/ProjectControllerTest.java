package portfolio.backend.api.project;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.amazonaws.services.s3.AmazonS3;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import portfolio.backend.api.project.controller.ProjectController;
import portfolio.backend.api.project.entity.Project;
import portfolio.backend.api.project.repository.ProjectRepository;
import portfolio.backend.api.project.exception.ResourceNotFoundException;
import portfolio.backend.authentication.api.service.UserService;
import portfolio.backend.authentication.config.properties.AppProperties;

import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProjectControllerTest {

    @InjectMocks
    private ProjectController projectController;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private AmazonS3 s3Client;

    @Mock
    private Authentication authentication;

    @Mock
    private UserService userService;

    // 테스트 할 두개의 프로젝트
    private Project project;
    private Project project2;


    @BeforeEach
    public void setUp() {
        project = new Project();

        project.setProjectId(1L);
        project.setProjectName("Test Project");

        project.setUserId("testUserId");
        project.setOngoingStatus(true);
        project.setRemoteStatus("both");
        project.setCreatedDate(LocalDate.now());
        project.setProjectArtCategory(Project.ProjectCategory.공연);
        project.setLocation("Test Location");
        project.setProjectDeadline(LocalDate.now().plusDays(10));
        project.setApplicationDeadline(LocalDate.now().plusDays(5));
        project.setRequiredPeople(5L);
        project.setRequiredCategory(Arrays.asList(Project.RequiredCategory.연기, Project.RequiredCategory.노래));
        project.setSwipeAlgorithm(true);

        project.setProjectImages(new HashSet<>());
        project.setDescription("This is a test project description.");

        project.setParticipantArtistIds(new HashSet<>());

        project2 = new Project();
        // Setting up basic fields for the second project
        project2.setProjectId(2L);
        project2.setProjectName("Test Project 2");

        // Setting up other fields for the second project
        project2.setUserId("testUserId2");
        project2.setOngoingStatus(false);
        project2.setRemoteStatus("remote");
        project2.setCreatedDate(LocalDate.now().minusDays(1));
        project2.setProjectArtCategory(Project.ProjectCategory.전시);
        project2.setLocation("Test Location 2");
        project2.setProjectDeadline(LocalDate.now().plusDays(20));
        project2.setApplicationDeadline(LocalDate.now().plusDays(15));
        project2.setRequiredPeople(10L);
        project2.setRequiredCategory(Arrays.asList(Project.RequiredCategory.노래, Project.RequiredCategory.작곡));
        project2.setSwipeAlgorithm(false);
        project2.setProjectImages(new HashSet<>());
        project2.setDescription("This is a test project description for the second project.");
        project2.setParticipantArtistIds(new HashSet<>());
    }

    @Test
    public void testGetProjectById() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        Project result = projectController.getProjectById(1L);
        assertNotNull(result);
        assertEquals("Test Project", result.getProjectName());

        when(projectRepository.findById(2L)).thenReturn(Optional.of(project2));

        Project result2 = projectController.getProjectById(2L);
        assertNotNull(result2);
        assertEquals("Test Project 2", result2.getProjectName());
    }

//    @Test
//    public void testGetProjectById_NotFound() {
//        when(projectRepository.findById(1L)).thenReturn(Optional.empty());
//
//        assertThrows(ResourceNotFoundException.class, () -> projectController.getProjectById(1L));
//    }

//    @Test
//    public void testCreateProject() throws Exception {
//        // Mock the Authentication object and set it in the SecurityContextHolder
//        Authentication mockAuthentication = mock(Authentication.class);
//        when(mockAuthentication.getName()).thenReturn("mockedUserId");
//        SecurityContextHolder.getContext().setAuthentication(mockAuthentication);
//
//
//        // Create MockMultipartFile for this test
//        MockMultipartFile file = new MockMultipartFile("image", "test.jpg", "image/jpeg", "test-image".getBytes());
//
//        // Mock the behavior of the repository save method
//        when(projectRepository.save(argumentCaptor.capture())).thenReturn(project);
//
//        when(s3Client.getUrl(anyString(), anyString())).thenReturn(new URL("http://mocked-url.com"));
//
//        when(projectRepository.save(any(Project.class))).thenReturn(project);
//
//// Call the method
//        SecurityContextHolder.getContext().setAuthentication(mockAuthentication); // Set the authentication
//        Project result = projectController.createProject(
//                "Test", Project.ProjectCategory.공연, "Location", null, null,
//                Arrays.asList(Project.RequiredCategory.연기), true,
//                Arrays.asList(file), "Description", true, "both", 0L, 0L, null);
//
//
//        assertNotNull(result);
//        assertEquals("Test", result.getProjectName());
//        assertEquals("Location", result.getLocation());
//        assertEquals(Project.ProjectCategory.공연, result.getProjectArtCategory());
//        assertEquals(true, result.getOngoingStatus());
//
//        // Verify the project was saved
//        Project savedProject = argumentCaptor.getValue();
//        assertEquals("Test", savedProject.getProjectName());
//        assertEquals("Location", savedProject.getLocation());
//        assertEquals(Project.ProjectCategory.공연, savedProject.getProjectArtCategory());
//
//        // Verify interactions
//        verify(projectRepository, times(1)).save(any(Project.class));
//        verify(s3Client, times(1)).getUrl(anyString(), anyString());
//        verify(mockAuthentication, times(1)).getName();
//    }

    @Captor
    private ArgumentCaptor<Project> argumentCaptor;


}
