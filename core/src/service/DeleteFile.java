package service;

import java.io.File;  // Import the File class

public class DeleteFile {
    public static void DeleteInPath(String pathname) {
        File myObj = new File(pathname);
        if (myObj.delete()) {
            System.out.println("Deleted the file: " + myObj.getName());
        } else {
            System.out.println("Failed to delete the file.");
        }
    }
}