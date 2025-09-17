package com.example.demo.service;

import com.example.demo.dto.NoteDto;
import com.example.demo.entity.Note;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.NoteRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final GeminiService geminiService;

    @Value("${GEMINI_ACCESS_TOKEN}")
    private String geminiToken;

    public NoteService(NoteRepository noteRepository, UserRepository userRepository, GeminiService geminiService) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
        this.geminiService = geminiService;
    }

    public Note createNote(String rawText, User user) {
        Note note = new Note();
        note.setRawText(rawText);
        note.setStatus("PENDING");
        note.setUser(user);
        note.setCreatedAt(LocalDateTime.now());
        noteRepository.save(note);

        try {
            String summary = geminiService.summarize(rawText, geminiToken);
            note.setSummary(summary);
            note.setStatus("DONE");
        } catch (Exception e) {
            note.setSummary("Özet oluşturulamadı: " + e.getMessage());
            note.setStatus("FAILED");
        }

        note.setUpdatedAt(LocalDateTime.now());
        noteRepository.save(note);

        return note;
    }

    @Async("noteExecutor")
    public void summarizeNoteAsync(Note note) {
        if ("DONE".equals(note.getStatus())) return;

        try {
            note.setStatus("PROCESSING");
            noteRepository.save(note);

            String summary = geminiService.summarize(note.getRawText(), geminiToken);
            note.setSummary(summary);
            note.setStatus("DONE");

        } catch (Exception e) {
            note.setStatus("FAILED");
        }

        noteRepository.save(note);
    }

    public Note getNote(Long id, User currentUser) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found"));

        if (!currentUser.getRole().equals(Role.ADMIN) &&
                !note.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Unauthorized access");
        }

        return note;
    }

    public List<Note> getNotesByUser(User user) {
        return noteRepository.findByUserId(user.getId());
    }
}
