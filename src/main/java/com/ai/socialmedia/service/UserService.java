package com.ai.socialmedia.service;

import com.ai.socialmedia.entity.User;
import com.ai.socialmedia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Create a new user
    public User createUser(User user) {
        // Implement any validation or business logic before saving
        return userRepository.save(user);
    }

    // Get a user by ID
    public User getUserById(Long userId) {
        // Implement error handling for non-existent user
        return userRepository.findById(userId).orElse(null);
    }

    // Update user details
    public User updateUser(Long userId, User updatedUser) {
        // Implement error handling for non-existent user
        User existingUser = userRepository.findById(userId).orElse(null);

        if (existingUser != null) {
            // Update the existing user with the new data
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setEmail(updatedUser.getEmail());
            // Update other fields as needed

            return userRepository.save(existingUser);
        }

        return null;
    }

    // Delete a user by ID
    public void deleteUser(Long userId) {
        if (userRepository.existsById(userId))
            userRepository.deleteById(userId);
    }

    // Follow another user
    public User followUser(Long followerId, Long followingId) {
        User follower = userRepository.findById(followerId).orElse(null);
        User following = userRepository.findById(followingId).orElse(null);

        if (follower != null && following != null) {
            // Initialize the relationships if not already initialized
            if (follower.getFollowing() == null) {
                follower.setFollowing(new HashSet<>());
            }
            if (following.getFollowers() == null) {
                following.setFollowers(new HashSet<>());
            }

            // Update relationships
            follower.getFollowing().add(following);
            following.getFollowers().add(follower);

            // Save both users to persist the relationship changes
            userRepository.save(follower);
            return userRepository.save(following);
        }

        return null;
    }
}


