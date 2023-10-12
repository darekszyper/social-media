package com.ai.socialmedia.controller;

import com.ai.socialmedia.entity.User;
import com.ai.socialmedia.service.UserService;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private MockMvc mockMvc;
    private final Gson gson = new Gson();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void createUser_NormalUsage() throws Exception {
        // Given
        User newUser = new User();
        newUser.setUsername("JohnDoe");
        newUser.setEmail("john.doe@example.com");
        when(userService.createUser(newUser)).thenReturn(newUser);

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(newUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(newUser.getUsername()))
                .andExpect(jsonPath("$.email").value(newUser.getEmail()));
    }

    @Test
    public void getUserById_ExistingUser() throws Exception {
        // Given
        Long userId = 1L;
        User existingUser = new User();
        existingUser.setUsername("ExistingUser");
        existingUser.setEmail("existing.user@example.com");
        when(userService.getUserById(userId)).thenReturn(existingUser);

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/{userId}", userId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(existingUser.getUsername()))
                .andExpect(jsonPath("$.email").value(existingUser.getEmail()));
    }

    @Test
    public void getUserById_NonExistentUser() throws Exception {
        // Given
        Long userId = 1L;
        when(userService.getUserById(userId)).thenReturn(null);

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/{userId}", userId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateUser_ExistingUser() throws Exception {
        // Given
        Long userId = 1L;
        User updatedUser = new User();
        updatedUser.setUsername("UpdatedUser");
        updatedUser.setEmail("updated.user@example.com");
        when(userService.updateUser(userId, updatedUser)).thenReturn(updatedUser);

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(updatedUser.getUsername()))
                .andExpect(jsonPath("$.email").value(updatedUser.getEmail()));
    }

    @Test
    public void updateUser_NonExistentUser() throws Exception {
        // Given
        Long userId = 1L;
        when(userService.updateUser(userId, new User())).thenReturn(null);

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(new User())))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteUser_ExistingUser() throws Exception {
        // Given
        Long userId = 1L;

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/{userId}", userId))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteUser_NonExistentUser() throws Exception {
        // Given
        Long userId = 1L;

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/{userId}", userId))
                .andExpect(status().isNoContent());  // We are not distinguishing between non-existent and deleted for simplicity
    }

    @Test
    public void followUser_NormalUsage() throws Exception {
        // Given
        Long followerId = 1L;
        Long followingId = 2L;
        User user = new User();
        when(userService.followUser(followerId, followingId)).thenReturn(user);

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/{followerId}/follow/{followingId}", followerId, followingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.email").value(user.getEmail()));
    }

    @Test
    public void followUser_UserNotFound() throws Exception {
        // Given
        Long followerId = 1L;
        Long followingId = 2L;
        when(userService.followUser(followerId, followingId)).thenReturn(null);

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/{followerId}/follow/{followingId}", followerId, followingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNotFound());
    }
}