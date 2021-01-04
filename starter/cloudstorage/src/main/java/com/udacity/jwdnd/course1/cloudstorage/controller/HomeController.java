package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {
    private NoteService noteService;
    private UserService userService;
    private FileService fileService;
    public HomeController(NoteService noteService, UserService userService, FileService fileService) {
        this.noteService = noteService;
        this.userService = userService;
        this.fileService = fileService;
    }

    @GetMapping
    public String homePage(Authentication authentication, @ModelAttribute Note note, Model model) {
        User user = this.userService.getUser(authentication.getName());
        model.addAttribute("notes", this.noteService.getNotes(user.getUserId()));
        model.addAttribute("files", this.fileService.getFiles(user.getUserId()));
        return "home";
    }

    /*@PostMapping
    public String postChatMessage(Authentication authentication, ChatForm chatForm, Model model) {
        chatForm.setUsername(authentication.getName());
        this.messageService.addMessage(chatForm);
        chatForm.setMessageText("");
        model.addAttribute("chatMessages", this.messageService.getChatMessages());
        return "chat";
    } */

    /*@ModelAttribute("allMessageTypes")
    public String[] allMessageTypes() {
        return new String[] {"Say", "Shout", "Whisper"};
    } */
}
