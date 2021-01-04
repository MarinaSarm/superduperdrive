package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {
    private FileMapper fileMapper;
    private UserService userService;

    public FileService(FileMapper fileMapper, UserService userService) {
        this.fileMapper = fileMapper;
        this.userService = userService;
    }

    public Integer addFile(Authentication authentication, MultipartFile file) throws IOException {
        File newFile = new File();
        newFile.setFileData(file.getBytes());
        newFile.setUserId(this.userService.getUser(authentication.getName()).getUserId());
        newFile.setFileSize(file.getSize());
        newFile.setFileName(file.getOriginalFilename());
        newFile.setContentType(file.getContentType());
        return fileMapper.insert(newFile);
    }

    public void deletefile(Integer fileid) {
        fileMapper.deleteFile(fileid);
    }

    public List<File> getFiles(Integer userid) {
        return fileMapper.getAllFiles(userid);
    }
}
