package com.ai.socialmedia.controller;

import com.ai.socialmedia.entity.Post;
import com.ai.socialmedia.service.PostService;
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

public class PostControllerTest {

    @InjectMocks
    private PostController postController;

    @Mock
    private PostService postService;

    private MockMvc mockMvc;
    private final Gson gson = new Gson();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(postController).build();
    }

    @Test
    public void createPost_NormalUsage() throws Exception {
        // Given
        Post newPost = new Post();
        newPost.setTitle("Title");
        newPost.setBody("Body");
        when(postService.createPost(newPost)).thenReturn(newPost);

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(newPost)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(newPost.getTitle()))
                .andExpect(jsonPath("$.body").value(newPost.getBody()));
    }

    @Test
    public void getPostById_ExistingPost() throws Exception {
        // Given
        Long postId = 1L;
        Post existingPost = new Post();
        when(postService.getPostById(postId)).thenReturn(existingPost);

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/posts/{postId}", postId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(existingPost.getTitle()))
                .andExpect(jsonPath("$.body").value(existingPost.getBody()));
    }

    @Test
    public void getPostById_NonExistentPost() throws Exception {
        // Given
        Long postId = 1L;
        when(postService.getPostById(postId)).thenReturn(null);

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/posts/{postId}", postId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updatePost_ExistingPost() throws Exception {
        // Given
        Long postId = 1L;
        Post updatedPost = new Post();
        updatedPost.setTitle("Updated Title");
        updatedPost.setBody("Updated Body");
        when(postService.updatePost(postId, updatedPost)).thenReturn(updatedPost);

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/posts/{postId}", postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(updatedPost)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(updatedPost.getTitle()))
                .andExpect(jsonPath("$.body").value(updatedPost.getBody()));
    }

    @Test
    public void updatePost_NonExistentPost() throws Exception {
        // Given
        Long postId = 1L;
        when(postService.updatePost(postId, new Post())).thenReturn(null);

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/posts/{postId}", postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(new Post())))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deletePost_ExistingPost() throws Exception {
        // Given
        Long postId = 1L;

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/posts/{postId}", postId))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deletePost_NonExistentPost() throws Exception {
        // Given
        Long postId = 1L;

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/posts/{postId}", postId))
                .andExpect(status().isNoContent());  // We are not distinguishing between non-existent and deleted for simplicity
    }

    @Test
    public void likePost_NormalUsage() throws Exception {
        // Given
        Long postId = 1L;
        Long userId = 2L;
        Post likedPost = new Post();
        when(postService.likePost(postId, userId)).thenReturn(likedPost);

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/posts/{postId}/like/{userId}", postId, userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(likedPost)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(likedPost.getTitle()))
                .andExpect(jsonPath("$.body").value(likedPost.getBody()));
    }

    @Test
    public void likePost_PostOrUserNotFound() throws Exception {
        // Given
        Long postId = 1L;
        Long userId = 2L;
        when(postService.likePost(postId, userId)).thenReturn(null);

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/posts/{postId}/like/{userId}", postId, userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNotFound());
    }
}