package com.ai.socialmedia.service;

import com.ai.socialmedia.entity.User;
import com.ai.socialmedia.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createUser_NormalUsage() {
        // Given
        User newUser = new User();
        Mockito.when(userRepository.save(newUser)).thenReturn(newUser);

        // When
        User result = userService.createUser(newUser);

        // Then
        assertNotNull(result);
        assertEquals(newUser, result);
    }

    @Test
    public void getUserById_ExistingUser() {
        // Given
        Long userId = 1L;
        User existingUser = new User();
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        // When
        User result = userService.getUserById(userId);

        // Then
        assertNotNull(result);
        assertEquals(existingUser, result);
    }

    @Test
    public void getUserById_NonExistentUser() {
        // Given
        Long userId = 1L;
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When
        User result = userService.getUserById(userId);

        // Then
        assertNull(result);
    }

    @Test
    public void updateUser_ExistingUser() {
        // Given
        Long userId = 1L;
        User existingUser = new User();
        User updatedUser = new User();
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        Mockito.when(userRepository.save(existingUser)).thenReturn(updatedUser);

        // When
        User result = userService.updateUser(userId, updatedUser);

        // Then
        assertNotNull(result);
        assertEquals(updatedUser, result);
    }

    @Test
    public void updateUser_NonExistentUser() {
        // Given
        Long userId = 1L;
        User updatedUser = new User();
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When
        User result = userService.updateUser(userId, updatedUser);

        // Then
        assertNull(result);
    }

    @Test
    public void deleteUser_ExistingUser() {
        // Given
        Long userId = 1L;
        User existingUser = new User();
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        Mockito.when(userRepository.existsById(userId)).thenReturn(true);

        // When
        userService.deleteUser(userId);

        // Then
        Mockito.verify(userRepository, Mockito.times(1)).deleteById(userId);
    }

    @Test
    public void deleteUser_NonExistentUser() {
        // Given
        Long userId = 1L;
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());
        Mockito.when(userRepository.existsById(userId)).thenReturn(false);

        // When
        userService.deleteUser(userId);

        // Then
        Mockito.verify(userRepository, Mockito.times(0)).deleteById(userId);
    }

    @Test
    public void followUser_NormalUsage() {
        // Given
        Long followerId = 1L;
        Long followingId = 2L;
        User follower = new User();
        User following = new User();

        // Ensure that relationships are properly initialized
        follower.setFollowing(new HashSet<>());
        following.setFollowers(new HashSet<>());

        Mockito.when(userRepository.findById(followerId)).thenReturn(Optional.of(follower));
        Mockito.when(userRepository.findById(followingId)).thenReturn(Optional.of(following));
        Mockito.when(userRepository.save(follower)).thenReturn(follower);
        Mockito.when(userRepository.save(following)).thenReturn(following);

        // When
        User result = userService.followUser(followerId, followingId);

        // Then
        assertNotNull(result);
        Mockito.verify(userRepository, Mockito.times(1)).save(follower);
        assertTrue(following.getFollowers().contains(follower));
    }

    @Test
    public void followUser_FollowerNotFound() {
        // Given
        Long followerId = 1L;
        Long followingId = 2L;
        Mockito.when(userRepository.findById(followerId)).thenReturn(Optional.empty());
        User following = new User();

        // Ensure that relationships are properly initialized
        following.setFollowers(new HashSet<>());

        Mockito.when(userRepository.findById(followingId)).thenReturn(Optional.of(following));

        // When
        User result = userService.followUser(followerId, followingId);

        // Then
        assertNull(result);
        assertFalse(following.getFollowers().contains(new User()));
    }

    @Test
    public void followUser_FollowingNotFound() {
        // Given
        Long followerId = 1L;
        Long followingId = 2L;
        User follower = new User();

        // Ensure that relationships are properly initialized
        follower.setFollowing(new HashSet<>());

        Mockito.when(userRepository.findById(followerId)).thenReturn(Optional.of(follower));
        Mockito.when(userRepository.findById(followingId)).thenReturn(Optional.empty());

        // When
        User result = userService.followUser(followerId, followingId);

        // Then
        assertNull(result);
        assertFalse(follower.getFollowing().contains(new User()));
    }
}