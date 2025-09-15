package com.example.demo.service;

import com.example.demo.entity.Note;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.NoteRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;  // final, çünkü constructor ile atanacak

    // Manuel constructor - Spring bu constructor'ı kullanarak injection yapar
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Async
    public void summarizeNote(Note note) {
        note.setStatus("PROCESSING");
        noteRepository.save(note);

        try {
            Thread.sleep(3000); // 3 sn simülasyon
            String summary = note.getRawText().length() > 50
                    ? note.getRawText().substring(0, 50) + "..."
                    : note.getRawText();
            note.setSummary(summary);
            note.setStatus("DONE");
        } catch (InterruptedException e) {
            note.setStatus("FAILED");
        }
        noteRepository.save(note);
    }

    public Note createNote(String rawText, User user) {
        Note note = Note.builder()
                .rawText(rawText)
                .status("QUEUED")
                .user(user)
                .build();
        note = noteRepository.save(note);
        summarizeNote(note); // Async queue
        return note;
    }

    public Note getNote(Long id, User currentUser) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found"));
        if (!currentUser.getRole().equals(Role.ADMIN) && !note.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Unauthorized access");
        }
        return note;
    }

    public List<Note> getNotesByUser(User user) {
        return noteRepository.findByUserId(user.getId());
    }
}