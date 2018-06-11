package com.astontech;

import java.util.List;

public interface DirectoryDAO {
    Directory getDirectoryById(int dirId);

    List<Directory> getDirectoryList();

    int insertDirectory(Directory directory);

    boolean updateDirectory(Directory directory);

    boolean deleteDirectory(int dirId);

    String getMaxNumOfFiles();

    String getMaxDirSize();
}
