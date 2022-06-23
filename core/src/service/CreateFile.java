package service;

import java.io.File;  // Import the File class
import java.io.IOException;  // Import the IOException class to handle errors

public class CreateFile {
    public static void CreateInPath(String pathname) {
        try {
            File myObj = new File(pathname);
            myObj.getParentFile().mkdirs();

            if(myObj.exists()){
                DeleteFile.DeleteInPath(pathname);
            }
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
                System.out.println("File created: " + myObj.getParentFile());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}