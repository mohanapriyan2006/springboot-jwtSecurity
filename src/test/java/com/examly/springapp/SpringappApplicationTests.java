package com.examly.springapp;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.junit.jupiter.api.Test;
import java.nio.file.*;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

import com.examly.springapp.model.Artist;
import com.examly.springapp.model.Artwork;
import com.examly.springapp.model.Auction;
import com.examly.springapp.service.ArtistService;
import com.examly.springapp.service.ArtworkService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.examly.springapp.controller.ArtistController;
import com.examly.springapp.controller.ArtworkController;
import com.examly.springapp.controller.AuctionController;
import com.examly.springapp.model.Artist;
import com.examly.springapp.repository.ArtistRepository;

import com.examly.springapp.service.AuctionService;

import org.junit.jupiter.api.BeforeEach;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Order;

import java.util.ArrayList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class SpringappApplicationTests {

    @InjectMocks
    private ArtistController artistController;

    @InjectMocks
    private ArtworkController artworkController;

    @InjectMocks
    private AuctionController auctionController;

    @Mock
    private ArtistService artistService;

    @Mock
    private ArtworkService artworkService;

    @Mock
    private AuctionService auctionService;

    private MockMvc mockMvc;

    @Autowired
    private ArtistRepository artistRepository;
    private ObjectMapper objectMapper = new ObjectMapper();

    private static final String LOG_FOLDER_PATH = "logs";
    private static final String LOG_FILE_PATH = "logs/application.log";


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(artistController, artworkController, auctionController).build();

    }

   

    @Test
    public void Repository_TestArtistRepositoryFile() {
        String filePath = "src/main/java/com/examly/springapp/repository/ArtistRepository.java";
        // Replace with the path to your file
        File file = new File(filePath);
        assertTrue(file.exists() && file.isFile());
    }

    @Test
    public void Repository_TestArtworkRepositoryFile() {
        String filePath = "src/main/java/com/examly/springapp/repository/ArtworkRepository.java";
        // Replace with the path to your file
        File file = new File(filePath);
        assertTrue(file.exists() && file.isFile());
    }

    @Test
    public void Repository_TestAuctionRepositoryFile() {
        String filePath = "src/main/java/com/examly/springapp/repository/AuctionRepository.java";
        // Replace with the path to your file
        File file = new File(filePath);
        assertTrue(file.exists() && file.isFile());
    }

    @Test
    public void Annotation_testArtistConstructorAndGetters() {
        // Arrange
        Long id = 1L;
        String name = "John Doe";
        String bio = "An abstract artist";
        String email = "johndoe@example.com";
        List<Artwork> artworks = new ArrayList<>();

        // Act
        Artist artist = new Artist(id, name, bio, email);
        artist.setArtworks(artworks);

        // Assert
        assertEquals(id, artist.getId());
        assertEquals(name, artist.getName());
        assertEquals(bio, artist.getBio());
        assertEquals(email, artist.getEmail());
        assertEquals(artworks, artist.getArtworks());
    }

    @Test
    public void testCreateArtist() throws Exception {
        Artist artist = new Artist(1L, "John Doe", "An abstract artist", "john@example.com");

        when(artistService.saveArtist(any(Artist.class))).thenReturn(artist);

        mockMvc.perform(post("/api/artists")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(artist)))

                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.bio").value("An abstract artist"))
                .andExpect(jsonPath("$.email").value("john@example.com"));

        // verify(artistService, times(1)).saveArtist(any(Artist.class));
    }

    @Test
    public void testGetAllArtists() throws Exception {
        Artist artist1 = new Artist(1L, "John Doe", "An abstract artist", "john@example.com");
        Artist artist2 = new Artist(2L, "Jane Smith", "A modern artist", "jane@example.com");

        when(artistService.getAllArtists()).thenReturn(Arrays.asList(artist1, artist2));

        mockMvc.perform(get("/api/artists")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Jane Smith"));

        verify(artistService, times(1)).getAllArtists();
    }

    @Test
    public void testGetArtistById() throws Exception {
        Artist artist = new Artist(1L, "John Doe", "An abstract artist", "john@example.com");

        when(artistService.getArtistById(1L)).thenReturn(Optional.of(artist));

        mockMvc.perform(get("/api/artists/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("John Doe"));

        verify(artistService, times(1)).getArtistById(1L);
    }

    // Test for deleting an artist
    @Test
    public void testDeleteArtist() throws Exception {
        doNothing().when(artistService).deleteArtist(1L);

        mockMvc.perform(delete("/api/artists/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(artistService, times(1)).deleteArtist(1L);
    }



    @Test
    public void testDeleteArtwork() throws Exception {
        Mockito.doNothing().when(artworkService).deleteArtwork(anyLong());

        mockMvc.perform(delete("/api/artworks/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateAuction() throws Exception {
        // Create Auction object directly in the test method with a null Artist
        Auction auction = new Auction(1L, "Auction Title", new java.util.Date(), new java.util.Date(), 1000.00, null);

        // Mock the service call
        when(auctionService.saveAuction(auction)).thenReturn(auction);

        // Perform the POST request to create an Auction with null Artist
        mockMvc.perform(post("/api/auctions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(auction)))  // Send Auction object as JSON
                .andExpect(status().isOk()) ;// Expecting status OK (200)
               
    }

    @Test
    public void testGetAllAuctions() throws Exception {
        Auction auction = new Auction(1L, "Auction Title", new Date(), new Date(), 1000.00, null);
        when(auctionService.getAllAuctions()).thenReturn(Arrays.asList(auction));

        mockMvc.perform(get("/api/auctions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("Auction Title"))
                .andExpect(jsonPath("$[0].startingPrice").value(1000.00));
    }


    @Test

    public void testDeleteAuction() throws Exception {
        doNothing().when(auctionService).deleteAuction(anyLong());

        mockMvc.perform(delete("/api/auctions/{id}", 1L))
                .andExpect(status().isOk());
    }
// ______________________swagger_____________________

@Test
public void Swagger_testConfigurationFolder() {
    String directoryPath = "src/main/java/com/examly/springapp/config"; // Replace with the path to your directory
    File directory = new File(directoryPath);
    assertTrue(directory.exists() && directory.isDirectory());
}

@Test
public void Swagger_testConfigFile() {

    String filePath = "src/main/java/com/examly/springapp/config/SwaggerConfig.java";

    // Replace with the path to your file

    File file = new File(filePath);

    assertTrue(file.exists() && file.isFile());

}
//_________________AOP______________________________

@Test
    void AOP_testAOPConfigFile() {
        // Path to the LoggingAspect class file
        String filePath = "src/main/java/com/examly/springapp/aspect/LoggingAspect.java";

        // Create a File object
        File file = new File(filePath);

        // Assert that the file exists and is a valid file
        assertTrue(file.exists() && file.isFile(), "LoggingAspect.java file should exist at the specified path.");
    }

    @Test
    void AOP_testAOPConfigFileAspect() throws Exception {
        // Path to the LoggingAspect class file
        Path aspectFilePath = Paths.get("src/main/java/com/examly/springapp/aspect/LoggingAspect.java");

        // Read the content of the aspect file
        String aspectFileContent = Files.readString(aspectFilePath);

        // Check if the file contains @Aspect annotation to ensure it's an Aspect class
        assertTrue(aspectFileContent.contains("@Aspect"), "The LoggingAspect.java should be annotated with @Aspect.");

        // Check if the file contains the logger definition to ensure logging functionality is implemented
        assertTrue(aspectFileContent.contains("private static final Logger logger"),
                "The LoggingAspect.java should define a logger for logging.");
    }

    @Test
    void AOP_testAspectMethods() throws Exception {
        // Path to the LoggingAspect class file
        Path aspectFilePath = Paths.get("src/main/java/com/examly/springapp/aspect/LoggingAspect.java");

        // Read the content of the aspect file
        String aspectFileContent = Files.readString(aspectFilePath);

        // Check for @Before and @AfterReturning annotations to ensure aspect methods are properly defined
        assertTrue(aspectFileContent.contains("@Before"),
                "@Before annotation should be present in the LoggingAspect.java");
        assertTrue(aspectFileContent.contains("@AfterReturning"),
                "@AfterReturning annotation should be present in the LoggingAspect.java");
    }

    @Test
    void AOP_testLoggingStatements() throws Exception {
        // Path to the LoggingAspect class file
        Path aspectFilePath = Paths.get("src/main/java/com/examly/springapp/aspect/LoggingAspect.java");

        // Read the content of the aspect file
        String aspectFileContent = Files.readString(aspectFilePath);

        // Check if logging statements are present in the aspect methods
        assertTrue(aspectFileContent.contains("logger.info"),
                "The LoggingAspect.java should contain logger.info statements for logging.");
    }
//________________________PAGINATION___________________________

@Test
void PaginateSorting_testPaginateArtworksController() throws Exception {
    // Path to the ArtworkController file
    Path entityFilePath = Paths.get("src/main/java/com/examly/springapp/controller/ArtworkController.java");

    // Read the content of the entity file as a string
    String entityFileContent = Files.readString(entityFilePath);

    // Check if the controller method uses Pageable and contains sorting functionality
    assertTrue(entityFileContent.contains("Page<Artwork>"), "ArtworkController should handle pagination and sorting.");
    assertTrue(entityFileContent.contains("Pageable"), "ArtworkController should accept Pageable for pagination.");
}
@Test
void PaginateSorting_testPaginateArtworksService() throws Exception {
    // Path to the ArtworkService file
    Path entityFilePath = Paths.get("src/main/java/com/examly/springapp/service/ArtworkService.java");

    // Read the content of the entity file as a string
    String entityFileContent = Files.readString(entityFilePath);

    // Check if the service method uses Pageable
    assertTrue(entityFileContent.contains("Pageable"), "ArtworkService should accept Pageable for pagination.");
    assertTrue(entityFileContent.contains("Page<Artwork>"), "ArtworkService should return a Page<Artwork>.");
}
@Test
void PaginateSorting_testGetSortedArtworks() throws Exception {
    // Create and set properties for the artworks
    Artwork artwork1 = new Artwork();
    artwork1.setTitle("Artwork One");
    artwork1.setPrice(100.0);
    
    Artwork artwork2 = new Artwork();
    artwork2.setTitle("Artwork Two");
    artwork2.setPrice(150.0);
    
    Artwork artwork3 = new Artwork();
    artwork3.setTitle("A Beautiful Artwork");
    artwork3.setPrice(200.0);

    // Add artworks to the list
    List<Artwork> artworks = Arrays.asList(artwork1, artwork2, artwork3);

    // Sort the artworks by title
    artworks.sort(Comparator.comparing(Artwork::getTitle));

    // Mock the artworkService.getAllArtworks() method to return the sorted artworks list
    when(artworkService.getAllArtworks(any(Pageable.class))).thenReturn(new PageImpl<>(artworks));

    // Perform the GET request to /api/artworks?page=0&size=10&sortBy=title and verify the response
    mockMvc.perform(get("/api/artworks?page=0&size=10&sortBy=title&direction=asc"))
            .andExpect(status().isOk()) // Verify the HTTP status is OK
            .andExpect(jsonPath("$.content.length()").value(3)) // Verify the length of the array
            .andExpect(jsonPath("$.content[0].title").value("A Beautiful Artwork")) // Verify the title of the first artwork after sorting
            .andExpect(jsonPath("$.content[1].title").value("Artwork One")) // Verify the title of the second artwork
            .andExpect(jsonPath("$.content[2].title").value("Artwork Two")); // Verify the title of the third artwork

    // Verify that the getAllArtworks method was called with Pageable
    verify(artworkService, times(1)).getAllArtworks(any(Pageable.class));
}

    //_----------------JPQL_________________
    @Test
public void JPQL_testFindByEmail() {
    // Arrange: Create and save an artist
    Artist artist = new Artist();
    artist.setName("Leonardo da Vinci");
    artist.setBio("Famous Renaissance artist");
    artist.setEmail("leonardo@art.com");

    // Save the artist to the repository
    artist = artistRepository.save(artist);

    // Assert: Check if the artist is saved properly
    assertNotNull(artist.getId(), "Artist ID should not be null after save");

    // Act: Use the repository method to find the artist by email
    // Artist result = artistRepository.findByEmail("leonardo@art.com");

    // Optionally print the result for debugging
    // System.out.println("Artist found with email 'leonardo@art.com': " + result.getName());

    // // Assert: Verify the result contains the correct artist
    // assertNotNull(result);
    // assertEquals("Leonardo da Vinci", result.getName());
    // assertEquals("leonardo@art.com", result.getEmail());
}

    
//______________________LOG________________________

@Test
    public void LOG_testLogFolderAndFileCreation() {
        String LOG_FOLDER_PATH = "logs";
        String LOG_FILE_PATH = "logs/application.log";
        // Check if the "logs" folder exists
        File logFolder = new File(LOG_FOLDER_PATH);
        assertTrue(logFolder.exists(), "Log folder should be created");

        // Check if the "application.log" file exists inside the "logs" folder
        File logFile = new File(LOG_FILE_PATH);
        assertTrue(logFile.exists(), "Log file should be created inside 'logs' folder");
    }




//________________________MAPPING________________________

@Test
void Mapping_testArtistEntityContainsOneToManyAnnotation() throws Exception {
    // Path to the Artist entity file
    Path entityFilePath = Paths.get("src/main/java/com/examly/springapp/model/Artist.java");

    // Read the content of the entity file as a string
    String entityFileContent = Files.readString(entityFilePath);

    // Check if the @OneToMany annotation exists in the entity file
    assertTrue(entityFileContent.contains("@OneToMany"), "Artist entity should contain @OneToMany annotation");
}

@Test
void Mapping_testArtistEntityContainsJoinColumnAnnotation() throws Exception {
    // Path to the Artist entity file
    Path entityFilePath = Paths.get("src/main/java/com/examly/springapp/model/Artist.java");

    // Read the content of the entity file as a string
    String entityFileContent = Files.readString(entityFilePath);

    // Check if the @JoinColumn annotation exists in the entity file
    assertTrue(entityFileContent.contains("@JoinColumn"), "Artist entity should contain @JoinColumn annotation");
}

@Test
void Mapping_testArtworkEntityContainsManyToOneAnnotation() throws Exception {
    // Path to the Artwork entity file
    Path entityFilePath = Paths.get("src/main/java/com/examly/springapp/model/Artwork.java");

    // Read the content of the entity file as a string
    String entityFileContent = Files.readString(entityFilePath);

    // Check if the @ManyToOne annotation exists in the entity file
    assertTrue(entityFileContent.contains("@ManyToOne"), "Artwork entity should contain @ManyToOne annotation");
}

@Test
void Mapping_testArtworkEntityContainsJoinColumnAnnotation() throws Exception {
    // Path to the Artwork entity file
    Path entityFilePath = Paths.get("src/main/java/com/examly/springapp/model/Artwork.java");

    // Read the content of the entity file as a string
    String entityFileContent = Files.readString(entityFilePath);

    // Check if the @JoinColumn annotation exists in the entity file
    assertTrue(entityFileContent.contains("@JoinColumn"), "Artwork entity should contain @JoinColumn annotation");
}

@Test
void Mapping_testAuctionEntityDoesNotContainManyToOneAnnotation() throws Exception {
    // Path to the Auction entity file
    Path entityFilePath = Paths.get("src/main/java/com/examly/springapp/model/Auction.java");

    // Read the content of the entity file as a string
    String entityFileContent = Files.readString(entityFilePath);

    // Check if the @ManyToOne annotation is missing
    assertTrue(!entityFileContent.contains("@ManyToOne"), "Auction entity should not contain @ManyToOne annotation");
}

@Test
void Mapping_testAuctionEntityDoesNotContainOneToManyAnnotation() throws Exception {
    // Path to the Auction entity file
    Path entityFilePath = Paths.get("src/main/java/com/examly/springapp/model/Auction.java");

    // Read the content of the entity file as a string
    String entityFileContent = Files.readString(entityFilePath);

    // Check if the @OneToMany annotation is missing
    assertTrue(!entityFileContent.contains("@OneToMany"), "Auction entity should not contain @OneToMany annotation");
}

@Test
void Annotation_testEventHasJSONIgnoreAnnotations() throws Exception {
    // Path to the Event entity file
    Path entityFilePath = Paths.get("src/main/java/com/examly/springapp/model/Artwork.java");

    // Read the content of the entity file as a string
    String entityFileContent = Files.readString(entityFilePath);

    // Assert that the file contains @JsonIgnore annotation
    assertTrue(entityFileContent.contains("@JsonIgnore"), "Artwork  should contain @JsonIgnore annotation");
}

}