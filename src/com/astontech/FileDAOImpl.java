package com.astontech;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FileDAOImpl extends MySQL implements FileDAO {
    @Override
    public File getFileById(int fileId) {
        Connect();
        File file = null;
        try {
            String sp = "{call GetFile(?, ?)}";
            CallableStatement cStmt = connection.prepareCall(sp);

            cStmt.setInt(1, GET_BY_ID);
            cStmt.setInt(2, fileId);
            ResultSet rs = cStmt.executeQuery();

            if (rs.next()) {
                file = HydrateObject(rs);
            }

        } catch (SQLException sqlEx) {
            logger.error(sqlEx);
        }
        return file;
    }

    @Override
    public List<File> getFileList() {
        return getFiles(GET_COLLECTION);
    }

    @Override
    public int insertFile(File file) {
        Connect();
        int id = 0;
        try {
            String sp = "{call ExecuteFile(?, ?, ?, ?, ?, ?, ?)}";
            CallableStatement cStmt = connection.prepareCall(sp);

            cStmt.setInt(1, INSERT);
            cStmt.setInt(2, 0);
            cStmt.setString(3, file.getFileName());
            cStmt.setString(4, file.getFileType());
            cStmt.setString(5, file.getFileSize());
            cStmt.setString(6, file.getPath());
            cStmt.setInt(7, file.getDirectoryId());
            ResultSet rs = cStmt.executeQuery();

            if (rs.next())
                id = rs.getInt(1);

        } catch (SQLException sqlEx) {
            logger.error(sqlEx);
        }
        return id;
    }

    @Override
    public boolean updateFile(File file) {
        Connect();
        int id = 0;
        try {
            String sp = "{call ExecuteFile(?, ?, ?, ?, ?, ?, ?)}";
            CallableStatement cStmt = connection.prepareCall(sp);

            cStmt.setInt(1, UPDATE);
            cStmt.setInt(2, file.getFileId());
            cStmt.setString(3, file.getFileName());
            cStmt.setString(4, file.getFileType());
            cStmt.setString(5, file.getFileSize());
            cStmt.setString(6, file.getPath());
            cStmt.setInt(7, file.getDirectoryId());

        } catch (SQLException sqlEx) {
            logger.error(sqlEx);
        }
        return id > 0;
    }

    @Override
    public boolean deleteFile(int fileId) {
        Connect();
        int id = 0;
        try {
            String sp = "{call ExecuteFile(?, ?, ?, ?, ?, ?, ?)}";
            CallableStatement cStmt = connection.prepareCall(sp);

            cStmt.setInt(1, DELETE);
            cStmt.setInt(2, 0);
            cStmt.setString(3, "");
            cStmt.setString(4, "");
            cStmt.setInt(5, 0);
            cStmt.setString(6, "");
            cStmt.setInt(7, 0);

        } catch (SQLException sqlEx) {
            logger.error(sqlEx);
        }
        return id > 0;
    }

    @Override
    public List<File> getFiveLargestFiles() {
        return getFiles(GET_FIVE_LARGEST);
    }

    @Override
    public List<File> getFilesByTypeText() {
        return getFiles(GET_FILE_TYPE_TEXT);
    }

    @Override
    public List<File> getFilesByTypeApp() {
        return getFiles(GET_FILE_TYPE_APP);
    }

    @Override
    public List<File> getFilesByTypeVideo() {
        return getFiles(GET_FILE_TYPE_VIDEO);
    }

    @Override
    public List<File> getFilesByTypeImage() {
        return getFiles(GET_FILE_TYPE_IMAGE);
    }

    private static File HydrateObject(ResultSet rs) throws SQLException {
        File file = new File();

        file.setFileId(rs.getInt(1));
        file.setFileName(rs.getString(2));
        file.setFileType(rs.getString(3));
        file.setFileSize(rs.getString(4));
        file.setPath(rs.getString(5));

        return file;
    }

    private static List<File> getFiles(int queryId) {
        Connect();
        List<File> fileList = new ArrayList<>();
        try {
            String sp = "{call GetFile(?, ?)}";
            CallableStatement cStmt = connection.prepareCall(sp);

            cStmt.setInt(1, queryId);
            cStmt.setInt(2, 0);
            ResultSet rs = cStmt.executeQuery();

            while (rs.next()) {
                fileList.add(HydrateObject(rs));
                System.out.println("File Name: " + rs.getString("fileName") + " || " + "File Size: " + rs.getString("fileSize") + " || " + "File Type: " + rs.getString("fileType"));
            }

        } catch (SQLException sqlEx) {
            logger.error(sqlEx);
        }
        return fileList;
    }
}
