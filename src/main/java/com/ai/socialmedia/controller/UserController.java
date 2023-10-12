package com.ai.socialmedia.controller;

import com.ai.socialmedia.entity.User;
import com.ai.socialmedia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Create a new user
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userService.createUser(user);
        return ResponseEntity.ok(savedUser);
    }

    // Get a user by ID
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        User user = userService.getUserById(userId);

        if (user == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(user);
    }

    // Update user details
    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User updatedUser) {
        User user = userService.updateUser(userId, updatedUser);

        if (user == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(user);
    }

    // Delete a user by ID
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    // Follow another user
    @PostMapping("/{followerId}/follow/{followingId}")
    public ResponseEntity<User> followUser(@PathVariable Long followerId, @PathVariable Long followingId) {
        User user = userService.followUser(followerId, followingId);

        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

