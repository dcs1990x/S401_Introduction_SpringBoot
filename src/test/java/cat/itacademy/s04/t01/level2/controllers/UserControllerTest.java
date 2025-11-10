package cat.itacademy.s04.t01.level2.controllers;

import cat.itacademy.s04.t01.level2.entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.UUID;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getUsers_returnsEmptyListInitially() throws Exception{
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void createUser_returnsUserWithId() throws Exception {
        User user = new User();
        user.setName("Pepito");
        user.setEmail("pepito@example.com");
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("Pepito"))
                .andExpect(jsonPath("$.email").value("pepito@example.com"))
                .andReturn();
    }

    @Test
    void getUserById_returnsCorrectUser() throws Exception {
        User user = new User();
        user.setName("Pepito");
        user.setEmail("pepito@example.com");
        MvcResult result = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("Pepito"))
                .andExpect(jsonPath("$.email").value("pepito@example.com"))
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();

        User returnedUser = objectMapper.readValue(jsonResponse, User.class);

        mockMvc.perform(get("/users/" + returnedUser.getId()))
                .andExpect(jsonPath("$.name").value("Pepito"))
                .andExpect(jsonPath("$.email").value("pepito@example.com"));
    }

    @Test
    void getUserById_returnsNotFoundIfMissing() throws Exception {
        UUID id = UUID.randomUUID();
        mockMvc.perform(get("/users/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void getUsers_withNameParam_returnsFilteredUsers() throws Exception {
        User user1 = new User();
        user1.setName("Judit");
        user1.setEmail("judit@example.com");
        User user2 = new User();
        user2.setName("Dani");
        user2.setEmail("dani@example.com");
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(user1)))
                .andExpect(status().isOk())
                .andReturn();

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(user2)))
                .andExpect(status().isOk())
                .andReturn();


        mockMvc.perform(get("/users?name=i"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Judit"))
                .andExpect(jsonPath("$[1].name").value("Dani"));
    }
}