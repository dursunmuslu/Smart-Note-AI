package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notes")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "raw_text", columnDefinition = "TEXT") // artık TEXT
    private String rawText;

    @Column(columnDefinition = "TEXT") // artık TEXT
    private String summary;

    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Default Constructor
    public Note() {}

    // Parametreli Constructor
    public Note(Long id, String rawText, String summary, String status, User user, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.rawText = rawText;
        this.summary = summary;
        this.status = status;
        this.user = user;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getter ve Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRawText() { return rawText; }
    public void setRawText(String rawText) { this.rawText = rawText; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    // Builder
    public static NoteBuilder builder() { return new NoteBuilder(); }

    public static class NoteBuilder {
        private Long id;
        private String rawText;
        private String summary;
        private String status;
        private User user;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public NoteBuilder id(Long id) { this.id = id; return this; }
        public NoteBuilder rawText(String rawText) { this.rawText = rawText; return this; }
        public NoteBuilder summary(String summary) { this.summary = summary; return this; }
        public NoteBuilder status(String status) { this.status = status; return this; }
        public NoteBuilder user(User user) { this.user = user; return this; }
        public NoteBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public NoteBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public Note build() {
            return new Note(id, rawText, summary, status, user, createdAt, updatedAt);
        }
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
