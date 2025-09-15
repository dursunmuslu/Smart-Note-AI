package com.example.demo.controller;

import com.example.demo.entity.Note;
import com.example.demo.entity.User;
import com.example.demo.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;  // final, çünkü constructor ile atanacak

    // Manuel constructor - Spring bu constructor'ı kullanarak injection yapar
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping
    public ResponseEntity<Note> createNote(@RequestBody String rawText, Authentication auth) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user == null) {
            throw new RuntimeException("User not authenticated");
        }
        Note note = noteService.createNote(rawText, user);
        return ResponseEntity.ok(note);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Note> getNote(@PathVariable Long id, Authentication auth) {
        User user = (User) auth.getPrincipal();
        Note note = noteService.getNote(id, user);
        return ResponseEntity.ok(note);
    }

    @GetMapping
    public ResponseEntity<List<Note>> getMyNotes(Authentication auth) {
        User user = (User) auth.getPrincipal();
        List<Note> notes = noteService.getNotesByUser(user);
        return ResponseEntity.ok(notes);
    }
}