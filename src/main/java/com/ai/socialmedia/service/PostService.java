package com.ai.socialmedia.service;

import com.ai.socialmedia.entity.Post;
import com.ai.socialmedia.entity.User;
import com.ai.socialmedia.repository.PostRepository;
import com.ai.socialmedia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    // Create a new post
    public Post createPost(Post post) {
        // Implement any validation or business logic before saving
        return postRepository.save(post);
    }

    // Get a post by ID
    public Post getPostById(Long postId) {
        // Implement error handling for non-existent post
        return postRepository.findById(postId).orElse(null);
    }

    // Update a post
    public Post updatePost(Long postId, Post updatedPost) {
        // Implement error handling for non-existent post
        Post existingPost = postRepository.findById(postId).orElse(null);

        if (existingPost != null) {
            // Update the existing post with the new data
            existingPost.setTitle(updatedPost.getTitle());
            existingPost.setBody(updatedPost.getBody());
            // Update other fields as needed

            return postRepository.save(existingPost);
        }

        return null;
    }

    // Delete a post by ID
    public void deletePost(Long postId) {
        if (postRepository.existsById(postId))
            postRepository.deleteById(postId);
    }

    // Like a post by a user
    public Post likePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);

        if (post != null && user != null) {
            post.getLikedBy().add(user);
            return postRepository.save(post);
        }

        return null;
    }
}


