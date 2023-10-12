package com.ai.socialmedia.entity;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String body;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToMany(mappedBy = "likedPosts")
    private Set<User> likedBy;

    // Constructors, getters, and setters

    public Post() {
    }

    public Post(String title, String body, User author) {
        this.title = title;
        this.body = body;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Set<User> getLikedBy() {
        return likedBy;
    }

    public void setLikedBy(Set<User> likedBy) {
        this.likedBy = likedBy;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", author=" + author +
                ", likedBy=" + likedBy +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(id, post.id) && Objects.equals(title, post.title) && Objects.equals(body, post.body) && Objects.equals(author, post.author) && Objects.equals(likedBy, post.likedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, body, author, likedBy);
    }
}

