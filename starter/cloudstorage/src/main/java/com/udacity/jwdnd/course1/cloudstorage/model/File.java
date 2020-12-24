package com.udacity.jwdnd.course1.cloudstorage.model;

public class File {
    private Integer fileId;
    private String fileName;
    private String contentType;
    private String fileSize;
    private Integer userId;
    private Byte[] fileData;

    public File(Integer fileId, String fileName, String contentType, String fileSize, Integer userId, Byte[] fileData) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.userId = userId;
        this.fileData = fileData;
    }

    public Integer getFileId() {
        return this.fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getfileName() {
        return this.fileName;
    }

    public void setfileName(String fileName) {
        this.fileName = fileName;
    }

    public String getcontentType() {
        return this.contentType;
    }

    public void setcontentType(String contentType) {
        this.contentType = contentType;
    }

    public String getfileSizee() {
        return this.fileSize;
    }

    public void setfileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
