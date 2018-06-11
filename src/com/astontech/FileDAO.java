package com.astontech;

import java.util.List;

public interface FileDAO {
    public File getFileById(int fileId);

    public List<File> getFileList();

    public int insertFile(File file);

    public boolean updateFile(File file);

    public boolean deleteFile(int fileId);

    public List<File> getFiveLargestFiles();

    public List<File> getFilesByTypeText();

    public List<File> getFilesByTypeApp();

    public List<File> getFilesByTypeVideo();

    public List<File> getFilesByTypeImage();

}
