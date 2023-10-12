package com.ai.socialmedia.controller;

import com.ai.socialmedia.entity.Post;
import com.ai.socialmedia.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    // Create a new post
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        Post savedPost = postService.createPost(post);
        return ResponseEntity.ok(savedPost);
    }

    // Get a post by ID
    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPostById(@PathVariable Long postId) {
        Post post = postService.getPostById(postId);

        if (post == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(post);
    }

    // Update a post
    @PutMapping("/{postId}")
    public ResponseEntity<Post> updatePost(@PathVariable Long postId, @RequestBody Post updatedPost) {
        Post post = postService.updatePost(postId, updatedPost);

        if (post == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(post);
    }

    // Delete a post by ID
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }

    // Like a post
    @PostMapping("/{postId}/like/{userId}")
    public ResponseEntity<Post> likePost(@PathVariable Long postId, @PathVariable Long userId) {
        Post post = postService.likePost(postId, userId);

        if (post != null) {
            return ResponseEntity.ok(post);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

