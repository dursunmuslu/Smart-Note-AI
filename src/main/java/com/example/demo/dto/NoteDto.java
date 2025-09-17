package com.example.demo.dto;

import java.time.LocalDateTime;

public class NoteDto {
    private Long id;
    private String rawText;
    private String summary;
    private String status;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Getter & Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getRawText() { return rawText; }
    public void setRawText(String rawText) { this.rawText = rawText; }
    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    // Builder
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private final NoteDto dto = new NoteDto();

        public Builder id(Long id) { dto.setId(id); return this; }
        public Builder rawText(String rawText) { dto.setRawText(rawText); return this; }
        public Builder summary(String summary) { dto.setSummary(summary); return this; }
        public Builder status(String status) { dto.setStatus(status); return this; }
        public Builder userId(Long userId) { dto.setUserId(userId); return this; }
        public Builder createdAt(LocalDateTime createdAt) { dto.setCreatedAt(createdAt); return this; }
        public Builder updatedAt(LocalDateTime updatedAt) { dto.setUpdatedAt(updatedAt); return this; }

        public NoteDto build() { return dto; }
    }
}
