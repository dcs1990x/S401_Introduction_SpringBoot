package cat.itacademy.s04.t01.level3.repository;

import cat.itacademy.s04.t01.level3.entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class InMemoryUserRepositoryTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InMemoryUserRepository inMemoryUserRepository;

    User usern1;
    User usern2;
    User usern3;

    @BeforeEach
    void setup(){
        inMemoryUserRepository.clearRepository();

        usern1 = new User();
        usern1.setName("Dani");
        usern1.setEmail("dani@example.com");

        usern2 = new User();
        usern2.setName("Judit");
        usern2.setEmail("judit@example.com");

        usern3 = new User();
        usern3.setName("Pepito");
        usern3.setEmail("pepito@example.com");
    }

    @AfterEach
    void clearCreatedUsers(){
        inMemoryUserRepository.clearRepository();
    }

    @Test
    void getUsers_returnsEmptyListInitially() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void givenAnEmptyList_WhenCreatingUser_ThenUserSaved() throws Exception {
        String requestBody = objectMapper.writeValueAsString(usern1);
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Dani"))
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    void givenAUser_WhenSaving_ThenUserIsAddedToList(){
        User savedUser = userRepository.save(usern1);
        assertEquals(1, userRepository.findAll().size());
        assertEquals("Dani", savedUser.getName());
    }

    @Test
    void given3SavedUsers_WhenFinding_ThenRetrieveAll(){
        userRepository.save(usern1);
        userRepository.save(usern2);
        userRepository.save(usern3);
        assertEquals(3, userRepository.findAll().size());
    }

    @Test
    void givenASavedUser_WhenFindingById_ThenRetrieveUserById(){
        User savedUser = userRepository.save(usern2);
        Optional<User> foundUser = userRepository.findById(savedUser.getId());
        assertTrue(foundUser.isPresent());
        assertEquals(usern2, foundUser.get());
    }

    @Test
    void givenASavedUser_WhenFindingByName_ThenRetrieveUsersByName(){
        userRepository.save(usern3);
        List<User> foundUsers = userRepository.searchByName("pe");
        assertFalse(foundUsers.isEmpty());
        assertEquals(1, foundUsers.size());
        assertEquals("Pepito", foundUsers.get(0).getName());
    }


}