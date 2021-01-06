package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/creds")
public class CredentialController {
    private CredentialService credentialService;
    private UserService userService;
    public CredentialController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }
    @PostMapping("add")
    public String addNote(Authentication authentication, @ModelAttribute Credential cred, Model model) {
        User user = this.userService.getUser(authentication.getName());
        cred.setUserId(user.getUserId());
        if (this.credentialService.credsForUrlExist(cred.getUrl(), user.getUserId())) {
            model.addAttribute("creds", this.credentialService.getAllCreds(user.getUserId()));
            model.addAttribute("uploadError", "Credentials with the same url already exists!");
            return "result";
        }
        if(cred.getCredentialId() == null) {
            try {
                this.credentialService.addCreds(cred);
                model.addAttribute("creds", this.credentialService.getAllCreds(user.getUserId()));
                model.addAttribute("success", true);
                model.addAttribute("message", "Credential was successfully added");
            } catch (Exception e) {
                model.addAttribute("error", true);
                model.addAttribute("message", "Credential could not be added " + e.getMessage());
            }
        } else {
            try {
                this.credentialService.updateCred(cred);
                model.addAttribute("creds", this.credentialService.getAllCreds(user.getUserId()));
                model.addAttribute("success", true);
                model.addAttribute("message", "Credential was successfully updated");
            } catch (Exception e) {
                model.addAttribute("error", true);
                model.addAttribute("message", "Credential could not be updated " + e.getMessage());
            }
        }
        return "result";
    }

    @GetMapping ("delete/{credentialid}")
    public String deleteNote(Authentication authentication, @ModelAttribute Credential credential, Model model, @PathVariable(value = "credentialid") Integer credentialid) {
        User user = this.userService.getUser(authentication.getName());
        try {
            this.credentialService.deleteCred(credentialid);
            model.addAttribute("creds", this.credentialService.getAllCreds(user.getUserId()));
            model.addAttribute("success", true);
            model.addAttribute("message", "Credential was deleted");
        } catch (Exception e) {
            model.addAttribute("error", true);
            model.addAttribute("message", "Was not able to delete credential " + e.getMessage());
        }
        return "result";
    }
}
