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

    public Integer addFile(File file){
        return fileMapper.insert(file);
    }

    public void deleteFile(Integer fileid) {
        fileMapper.deleteFile(fileid);
    }

    public File getFile(String filename) {
        return fileMapper.getFile(filename);
    }

    public List<File> getFiles(Integer userid) {
        return fileMapper.getAllFiles(userid);
    }

    public boolean fileNameExist(String fileName, Integer userId) {
        return (this.fileMapper.getFileNameFoUser(fileName, userId) != null);
    }
}
