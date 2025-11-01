package com.puzzlertracker.model;

import java.time.LocalDateTime;

/**
 * Simple Puzzle model - no database dependencies
 * Used for mock data when database is not available
 */
public class Puzzle {

    private Long id;
    private String title;
    private String description;
    private String difficulty;
    private LocalDateTime createdAt;

    // Constructors
    public Puzzle() {
        this.createdAt = LocalDateTime.now();
    }

    public Puzzle(String title, String description, String difficulty) {
        this();
        this.title = title;
        this.description = description;
        this.difficulty = difficulty;
    }

    // Getters and Setters
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
