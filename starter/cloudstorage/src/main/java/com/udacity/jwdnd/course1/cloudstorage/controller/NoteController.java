package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/notes")
public class NoteController {
    private NoteService noteService;
    private UserService userService;
    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }
    @PostMapping()
    public String addNote(Authentication authentication, @ModelAttribute Note note, Model model) {
        note.setUserId(this.userService.getUser(authentication.getName()).getUserId());
        this.noteService.addNote(note);
        System.out.println(this.noteService.getNotes());
        model.addAttribute("notes", this.noteService.getNotes());
        return "home";
    }
}
