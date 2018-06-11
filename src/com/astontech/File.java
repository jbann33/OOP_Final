package com.astontech;

public class File {

    //region PROPERTIES
    private int fileId;
    private String fileName;
    private String fileType;
    private String fileSize;
    private String path;
    private int DirectoryId;
    //endregion

    //region CONSTRUCTORS

    //endregion

    //region GETTERS / SETTERS
    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getDirectoryId() {
        return DirectoryId;
    }

    public void setDirectoryId(int directoryId) {
        this.DirectoryId = directoryId;
    }
    //endregion

    //region CUSTOM METHODS

    // TRYING TO GET USER-FRIENDLY OUTPUT INSTEAD OF FILE ADDRESSES
    public String fileToString() {
        return "File Name: " + fileName + "File Size: " + fileSize + "File Type: " + fileType;
    }
    //endregion



}
