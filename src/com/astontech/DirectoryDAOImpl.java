package com.astontech;

import java.io.File;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DirectoryDAOImpl extends MySQL implements DirectoryDAO {
    @Override
    public Directory getDirectoryById(int dirId) {
        Connect();
        Directory directory = null;
        try {
            String sp = "{call GetDirectory(?, ?)}";
            CallableStatement cStmt = connection.prepareCall(sp);

            cStmt.setInt(1, GET_BY_ID);
            cStmt.setInt(2, dirId);
            ResultSet rs = cStmt.executeQuery();

            if (rs.next()) {
                directory = HydrateObject(rs);
            }

        } catch (SQLException sqlEx) {
            logger.error(sqlEx);
        }
        return directory;
    }

    @Override
    public List<Directory> getDirectoryList() {
        Connect();
        List<Directory> directoryList = new ArrayList<>();
        try {
            String sp = "{call GetDirectory(?, ?)}";
            CallableStatement cStmt = connection.prepareCall(sp);

            cStmt.setInt(1, GET_COLLECTION);
            cStmt.setInt(2, 0);
            ResultSet rs = cStmt.executeQuery();

            while (rs.next()) {
                directoryList.add(HydrateObject(rs));
            }

        } catch (SQLException sqlEx) {
            logger.error(sqlEx);
        }
        return directoryList;
    }

    @Override
    public int insertDirectory(Directory directory) {
        Connect();
        int id = 0;
        try {
            String sp = "{call ExecuteDirectory(?, ?, ?, ?, ?, ?)}";
            CallableStatement cStmt = connection.prepareCall(sp);

            cStmt.setInt(1, INSERT);
            cStmt.setInt(2, 0);
            cStmt.setString(3, directory.getDirName());
            cStmt.setString(4, directory.getDirSize());
            cStmt.setInt(5, directory.getNumberOfFiles());
            cStmt.setString(6, directory.getPath());
            ResultSet rs = cStmt.executeQuery();

            if (rs.next())
                id = rs.getInt(1);

        } catch (SQLException sqlEx) {
            logger.error(sqlEx);
        }
        return id;
    }

    @Override
    public boolean updateDirectory(Directory directory) {
        Connect();
        int id = 0;
        try {
            String sp = "{call ExecuteDirectory(?, ?, ?, ?, ?, ?)}";
            CallableStatement cStmt = connection.prepareCall(sp);

            cStmt.setInt(1, UPDATE);
            cStmt.setInt(2, directory.getDirId());
            cStmt.setString(3, directory.getDirName());
            cStmt.setString(4, directory.getDirSize());
            cStmt.setInt(5, directory.getNumberOfFiles());
            cStmt.setString(6, directory.getPath());
            ResultSet rs = cStmt.executeQuery();

            if (rs.next())
                id = rs.getInt(1);

        } catch (SQLException sqlEx) {
            logger.error(sqlEx);
        }
        return id > 0;
    }

    @Override
    public boolean deleteDirectory(int dirId) {
        Connect();
        int id = 0;
        try {
            String sp = "{call ExecuteDirectory(?, ?, ?, ?, ?, ?)}";
            CallableStatement cStmt = connection.prepareCall(sp);

            cStmt.setInt(1, DELETE);
            cStmt.setInt(2, dirId);
            cStmt.setString(3, "");
            cStmt.setInt(4, 0);
            cStmt.setInt(5, 0);
            cStmt.setString(6, "");
            ResultSet rs = cStmt.executeQuery();

            if (rs.next()) {
                id = rs.getInt(1);
            }

        } catch (SQLException sqlEx) {
            logger.error(sqlEx);
        }
        return id > 0;
    }

    private static Directory HydrateObject(ResultSet rs) throws SQLException{

        Directory directory = new Directory();
        directory.setDirId(rs.getInt(1));
        directory.setDirName(rs.getString(2));
        directory.setDirSize(rs.getString(3));
        directory.setNumberOfFiles(rs.getInt(4));
        directory.setPath(rs.getString(5));

        return directory;

    }

    @Override
    public String getMaxNumOfFiles() {
        Connect();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery("SELECT * FROM Directory ORDER BY numberOfFiles DESC LIMIT 1");
            while (rs.next()) {
                System.out.println("Directory: " + rs.getString("dirName"));
                System.out.println("Number of Files: " + rs.getInt("numberOfFiles"));
            }
        } catch (SQLException sqlEx) {
            logger.error(sqlEx);
        }
        return "";
    }

    @Override
    public String getMaxDirSize() {
        Connect();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery("SELECT * FROM Directory ORDER BY dirSize DESC LIMIT 1");
            while (rs.next()) {
                System.out.println("Directory: " + rs.getString("dirName"));
                System.out.println("Size: " + rs.getString("dirSize"));
            }
        } catch (SQLException sqlEx) {
            logger.error(sqlEx);
        }
        return "";
    }

}
