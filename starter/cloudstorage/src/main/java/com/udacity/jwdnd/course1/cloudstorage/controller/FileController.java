package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/files")
public class FileController {
    private FileService fileService;
    private UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping("upload")
    public String addFile(@RequestParam("fileUpload") MultipartFile fileNew, Authentication authentication, @ModelAttribute File file, Model model) throws IOException {
        User user = this.userService.getUser(authentication.getName());
        if (fileNew.isEmpty()) {
            model.addAttribute("files", this.fileService.getFiles(user.getUserId()));
            model.addAttribute("success", false);
            model.addAttribute("message", "No file selected to upload!");
            return "result";
        }
        if (this.fileService.fileNameExist(fileNew.getOriginalFilename(), user.getUserId())) {
            model.addAttribute("files", this.fileService.getFiles(user.getUserId()));
            model.addAttribute("uploadError", "File with the same filename already exists!");
            return "result";
        }
        try {
            File uploadFile = new File();
            uploadFile.setFileName(fileNew.getOriginalFilename());
            uploadFile.setContentType(fileNew.getContentType());
            uploadFile.setFileData(fileNew.getBytes());
            uploadFile.setFileSize(fileNew.getSize());
            uploadFile.setUserId(user.getUserId());
            this.fileService.addFile(uploadFile);
            model.addAttribute("files", this.fileService.getFiles(user.getUserId()));
            model.addAttribute("success", true);
            model.addAttribute("message", "New File added successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("uploadError", e.getMessage());
        }
        return "result";
    }

    @GetMapping("delete/{fileid}")
    public String deleteFile(Authentication authentication, @ModelAttribute File file, Model model, @PathVariable(value = "fileid") Integer fileid) {
        User user = this.userService.getUser(authentication.getName());
        try {
            fileService.deleteFile(fileid);
            model.addAttribute("files", this.fileService.getFiles(user.getUserId()));
            model.addAttribute("success", true);
            model.addAttribute("message", "file was deleted");
        } catch (Exception e) {
            model.addAttribute("error", true);
            model.addAttribute("message", "Was not able to delete file" + e.getMessage());
        }
        return "result";
    }

    @GetMapping("view/{filename}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable(value = "filename") String filename) {
        File newFile = this.fileService.getFile(filename);
        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(newFile.getContentType()))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + newFile.getFileName() + "\"")
            .body(new ByteArrayResource(newFile.getFileData()));
    }
}
