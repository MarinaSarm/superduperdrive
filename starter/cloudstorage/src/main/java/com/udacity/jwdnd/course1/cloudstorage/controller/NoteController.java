package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("add")
    public String addNote(Authentication authentication, @ModelAttribute Note note, Model model) {
        note.setUserId(this.userService.getUser(authentication.getName()).getUserId());
        if(note.getNoteId() == null) {
            try {
                this.noteService.addNote(note);
                model.addAttribute("notes", this.noteService.getNotes(this.userService.getUser(authentication.getName()).getUserId()));
                model.addAttribute("success", true);
                model.addAttribute("message", "Note was successfully added");
            } catch (Exception e) {
                model.addAttribute("error", true);
                model.addAttribute("message", "Note could not be added" + e.getMessage());
            }
        } else {
            try {
                this.noteService.updateNote(note);
                model.addAttribute("notes", this.noteService.getNotes(this.userService.getUser(authentication.getName()).getUserId()));
                model.addAttribute("success", true);
                model.addAttribute("message", "Note was successfully updated");
            } catch (Exception e) {
                model.addAttribute("error", true);
                model.addAttribute("message", "Note could not be updated" + e.getMessage());
            }
        }
        return "result";
    }

    @GetMapping ("delete/{noteid}")
    public String deleteNote(Authentication authentication, @ModelAttribute Note note, Model model, @PathVariable(value = "noteid") Integer noteid) {
        User user = this.userService.getUser(authentication.getName());
        try {
            noteService.deleteNote(noteid);
            model.addAttribute("notes", this.noteService.getNotes(user.getUserId()));
            model.addAttribute("success", true);
            model.addAttribute("message", "Note was deleted");
        } catch (Exception e) {
            model.addAttribute("error", true);
            model.addAttribute("message", "Was not able to delete note" + e.getMessage());
        }
        return "result";
    }
}
