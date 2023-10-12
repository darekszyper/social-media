package com.ai.socialmedia.service;

import com.ai.socialmedia.entity.Post;
import com.ai.socialmedia.entity.User;
import com.ai.socialmedia.repository.PostRepository;
import com.ai.socialmedia.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Optional;

public class PostServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createPost_NormalUsage() {
        // Given
        Post newPost = new Post();
        Mockito.when(postRepository.save(newPost)).thenReturn(newPost);

        // When
        Post result = postService.createPost(newPost);

        // Then
        assertNotNull(result);
        assertEquals(newPost, result);
    }

    @Test
    public void getPostById_ExistingPost() {
        // Given
        Long postId = 1L;
        Post existingPost = new Post();
        Mockito.when(postRepository.findById(postId)).thenReturn(Optional.of(existingPost));

        // When
        Post result = postService.getPostById(postId);

        // Then
        assertNotNull(result);
        assertEquals(existingPost, result);
    }

    @Test
    public void getPostById_NonExistentPost() {
        // Given
        Long postId = 1L;
        Mockito.when(postRepository.findById(postId)).thenReturn(Optional.empty());

        // When
        Post result = postService.getPostById(postId);

        // Then
        assertNull(result);
    }

    @Test
    public void updatePost_ExistingPost() {
        // Given
        Long postId = 1L;
        Post existingPost = new Post();
        Post updatedPost = new Post();
        Mockito.when(postRepository.findById(postId)).thenReturn(Optional.of(existingPost));
        Mockito.when(postRepository.save(existingPost)).thenReturn(updatedPost);

        // When
        Post result = postService.updatePost(postId, updatedPost);

        // Then
        assertNotNull(result);
        assertEquals(updatedPost, result);
    }

    @Test
    public void updatePost_NonExistentPost() {
        // Given
        Long postId = 1L;
        Post updatedPost = new Post();
        Mockito.when(postRepository.findById(postId)).thenReturn(Optional.empty());

        // When
        Post result = postService.updatePost(postId, updatedPost);

        // Then
        assertNull(result);
    }

    @Test
    public void likePost_NormalUsage() {
        // Given
        Long postId = 1L;
        Long userId = 2L;
        Post post = new Post();
        User user = new User();
        post.setLikedBy(new HashSet<>());
        Mockito.when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(postRepository.save(post)).thenReturn(post);

        // When
        Post result = postService.likePost(postId, userId);

        // Then
        assertNotNull(result);
        assertTrue(result.getLikedBy().contains(user));
    }

    @Test
    public void likePost_PostOrUserNotFound() {
        // Given
        Long postId = 1L;
        Long userId = 2L;
        Mockito.when(postRepository.findById(postId)).thenReturn(Optional.empty());
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When
        Post result = postService.likePost(postId, userId);

        // Then
        assertNull(result);
    }

    @Test
    public void deletePost_ExistingPost() {
        // Given
        Long postId = 1L;
        Post existingPost = new Post();
        Mockito.when(postRepository.findById(postId)).thenReturn(Optional.of(existingPost));
        Mockito.when(postRepository.existsById(postId)).thenReturn(true);

        // When
        postService.deletePost(postId);

        // Then
        Mockito.verify(postRepository, Mockito.times(1)).deleteById(postId);
    }

    @Test
    public void deletePost_NonExistentPost() {
        // Given
        Long postId = 1L;
        Mockito.when(postRepository.findById(postId)).thenReturn(Optional.empty());
        Mockito.when(postRepository.existsById(postId)).thenReturn(false);

        // When
        postService.deletePost(postId);

        // Then
        Mockito.verify(postRepository, Mockito.times(0)).deleteById(postId);
    }
}