package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("/files")
public class FileController {
    private FileService fileService;
    private UserService userService;
    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping()
    public String addFile(@RequestParam("fileUpload") MultipartFile fileNew, Authentication authentication, @ModelAttribute File file, Model model) throws IOException {
        if(fileNew.isEmpty()) {
            model.addAttribute("success",false);
            model.addAttribute("message","No file selected to upload!");
            return "home";
        }
        this.fileService.addFile(authentication, fileNew);
        model.addAttribute("files", this.fileService.getFiles(this.userService.getUser(authentication.getName()).getUserId()));
        model.addAttribute("success",true);
        model.addAttribute("message","New File added successfully!");
        return "home";
    }

    @GetMapping ("delete/{fileid}")
    public String deletefile(Authentication authentication, @ModelAttribute File file, Model model, @PathVariable(value = "fileid") Integer fileid) {
        User user = this.userService.getUser(authentication.getName());
        try {
            fileService.deletefile(fileid);
            model.addAttribute("files", this.fileService.getFiles(user.getUserId()));
            model.addAttribute("success", true);
            model.addAttribute("message", "file was deleted");
        } catch (Exception e) {
            model.addAttribute("error", true);
            model.addAttribute("message", "Was not able to delete file" + e.getMessage());
        }
        return "home";
    }
}
