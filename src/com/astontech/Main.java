package com.astontech;

import java.io.IOException;
import java.nio.file.Files;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.Scanner;
import org.apache.commons.io.*;
import static com.astontech.MySQL.*;

public class Main {

    static DirectoryDAO directoryDAO = new DirectoryDAOImpl();
    static FileDAO fileDAO = new FileDAOImpl();

    public static void main(String[] args) {
        promptForFilePath();
        promptForOption();
    }

    private static void promptForFilePath() {
        System.out.println("Please enter a valid file pathway: ");
        Scanner reader = new Scanner(System.in);
        String path = reader.nextLine();
        insertInitialDirectory(new java.io.File(path));
    }

    private static void insertInitialDirectory(java.io.File startingPoint) {
        // CHECK TO MAKE SURE IT'S A DIRECTORY
        if (startingPoint.isDirectory()) {

            Directory createdDirectory = new Directory();

            // SETTING DIR PROPERTIES INSIDE OF DIR OBJECT
            createdDirectory.setDirName(startingPoint.getName());
            createdDirectory.setDirSize(getFolderOrFileSize(startingPoint));
            createdDirectory.setNumberOfFiles(startingPoint.listFiles().length);
            createdDirectory.setPath(startingPoint.getPath());

            System.out.println("Current Directory: " + createdDirectory.getDirName() + "// Size: " + createdDirectory.getDirSize());
            // INSERT DIRECTORY TO DB AND GET NEWLY CREATED RECORD ID
            int newDirId = directoryDAO.insertDirectory(createdDirectory);
            // NEED TO RECURSE BECAUSE THERE ARE MORE FILES / DIR IN A DIR
            Recursion(startingPoint, newDirId);

        } else {
            // IF NOT A DIRECTORY PROMPT AGAIN
            promptForFilePath();
        }
    }

    private static void Recursion(java.io.File startingPoint, int currentDirId) {
        try {
            // Creating a file from our current directory
            java.io.File[] startingDirectoryFiles = startingPoint.listFiles();
            for (java.io.File file : startingDirectoryFiles) {
                // IS A DIRECTORY
                if (file.isDirectory()) {
                    // Instantiating a directory object
                    Directory createdDirectory = new Directory();
                    // Setting properties inside of object
                    createdDirectory.setDirName(file.getName());
                    createdDirectory.setDirSize(getFolderOrFileSize(file));
                    createdDirectory.setNumberOfFiles(file.listFiles().length);
                    createdDirectory.setPath(file.getPath());

                    int newDirId = directoryDAO.insertDirectory(createdDirectory);
                    // NEED TO RECURSE BECAUSE THERE ARE MORE FILES / DIR IN A DIR
                    System.out.println("Directory //" + createdDirectory.getDirName() + "// data successfully stored in table DIRECTORY.");
                    Recursion(file, newDirId);

                } else { // NOT A DIRECTORY ERGO A FILE
                    File createdFile = new File();
                    createdFile.setFileName(file.getName());
                    createdFile.setFileSize(getFolderOrFileSize(file));
                    createdFile.setFileType(Files.probeContentType(file.toPath()));
                    createdFile.setPath(file.getPath());
                    // UNSURE WHY THIS DOES NOT POPULATE DIR ID COLUMN IN FILE TABLE IN DB
                    createdFile.setDirectoryId(currentDirId);

                    fileDAO.insertFile(createdFile);
                    System.out.println("File //" + createdFile.getFileName() + "// data successfully stored in table FILE.");
                }
            }
        } catch (NullPointerException npEx) {
            logger.error(npEx);
            System.out.println("That directory does not exist.");
            promptForFilePath();
        } catch (IOException ioEx) {
            logger.error(ioEx);
        }
    }

    private static void promptForOption() {
        System.out.println("");
        System.out.println("Please select an option from the list: ");
        System.out.println("1) Display directory with most files");
        System.out.println("2) Display directory largest in size");
        System.out.println("3) Display 5 largest files in size");
        System.out.println("4) Display all files of a certain type");
        System.out.println("5) Clear database and start over");
        System.out.println("6) Exit");
        Scanner reader = new Scanner(System.in);
        int optionId = reader.nextInt();
        switch (optionId) {
            case 1:
                System.out.println(directoryDAO.getMaxNumOfFiles());
                break;
            case 2:
                System.out.println(directoryDAO.getMaxDirSize());
                break;
            case 3:
                fileDAO.getFiveLargestFiles();
                break;
            case 4:
                System.out.println("Please select a file type: ");
                System.out.println("1) text");
                System.out.println("2) application");
                System.out.println("3) video");
                System.out.println("4) image");
                int fileType = reader.nextInt();
                if (fileType == 1) {
                    fileDAO.getFilesByTypeText();
                }
                if (fileType == 2) {
                    fileDAO.getFilesByTypeApp();
                }
                if (fileType == 3) {
                    fileDAO.getFilesByTypeVideo();
                }
                if (fileType == 4) {
                    fileDAO.getFilesByTypeImage();
                }
                break;

            case 5:
                deleteAll();
                System.out.println("Data from tables DIRECTORY, FILE erased.");
                break;
            case 6:
                System.out.println("You have chosen option 'Exit'. Goodbye.");
                System.exit(0);
                break;
        }
        returnToMenu();
    }

    private static void returnToMenu() {
        System.out.println("");
        System.out.println("Return to menu? (Y/N)");
        Scanner reader = new Scanner(System.in);
        String yesNo = reader.next();
        switch (yesNo) {
            case "Y":
            case "y":
                promptForOption();
                break;
            case "N":
            case "n":
                System.out.println("Goodbye.");
                break;
        }
    }

    private static String getFolderOrFileSize(java.io.File folder) {
        if (folder.isDirectory()) {
            java.io.File dir = new java.io.File(folder.getPath());
            long size = FileUtils.sizeOfDirectory(dir);
            System.out.println("Directory: " + folder.getName() + " // Size: " + FileUtils.byteCountToDisplaySize(size));
            return FileUtils.byteCountToDisplaySize(size);
        } else {
            long size = FileUtils.sizeOf(folder);
            return FileUtils.byteCountToDisplaySize(size);
        }
    }

    private static void deleteAll() {
        Connect();
        try {
            String sp = "{call DeleteAll(?)}";
            CallableStatement cStmt = connection.prepareCall(sp);
            cStmt.setInt(1, DELETE_ALL);
            cStmt.executeQuery();

    } catch (SQLException sqlEx) {
        logger.error(sqlEx);
        }
    }
}