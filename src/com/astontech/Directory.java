package com.astontech;

import java.util.ArrayList;
import java.util.List;


public class Directory {

    //region PROPERTIES
    private int dirId;
    private String dirName;
    private String dirSize;
    private int numberOfFiles;
    private String path;
    private List<File> files;
    //endregion

    //region CONSTRUCTORS
    public Directory() {
        this.setFiles(new ArrayList<>());
    }
    //endregion

    //region GETTERS / SETTERS
    public int getDirId() {
        return dirId;
    }

    public void setDirId(int dirId) {
        this.dirId = dirId;
    }

    public String getDirName() {
        return dirName;
    }

    public void setDirName(String dirName) {
        this.dirName = dirName;
    }

    public String getDirSize() {
        return dirSize;
    }

    public void setDirSize(String dirSize) {
        this.dirSize = dirSize;

    }

    public int getNumberOfFiles() {
        return numberOfFiles;
    }

    public void setNumberOfFiles(int numberOfFiles) {
        this.numberOfFiles = numberOfFiles;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }
    //endregion

    //region CUSTOM METHODS
    //endregion




}
