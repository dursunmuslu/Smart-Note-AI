package com.example.demo.controller;

import com.example.demo.dto.NoteDto;
import com.example.demo.entity.Note;
import com.example.demo.entity.User;
import com.example.demo.service.NoteService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;
    private final UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<NoteDto> createNote(@RequestBody String rawText, Authentication auth) {
        User user = userService.getUserByEmail(auth.getName());
        Note note = noteService.createNote(rawText, user);
        return ResponseEntity.ok(mapToDto(note));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteDto> getNote(@PathVariable Long id, Authentication auth) {
        User user = userService.getUserByEmail(auth.getName());
        Note note = noteService.getNote(id, user);
        return ResponseEntity.ok(mapToDto(note));
    }

    @GetMapping
    public ResponseEntity<List<NoteDto>> getMyNotes(Authentication auth) {
        User user = userService.getUserByEmail(auth.getName());
        List<Note> notes = noteService.getNotesByUser(user);
        List<NoteDto> dtoList = notes.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    public NoteDto mapToDto(Note note) {
        NoteDto dto = new NoteDto();
        dto.setId(note.getId());
        dto.setRawText(note.getRawText());
        dto.setSummary(note.getSummary());
        dto.setStatus(note.getStatus());
        dto.setUserId(note.getUser().getId());
        dto.setCreatedAt(note.getCreatedAt());
        dto.setUpdatedAt(note.getUpdatedAt());
        return dto;
    }

}
