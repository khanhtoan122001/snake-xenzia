package service;

import com.badlogic.gdx.math.Vector2;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner; // Import the Scanner class to read text files

public class ReadFile {
    public static List<Vector2> ReadMap(String pathname) {
        List<Vector2> walls = new ArrayList<>();
        int maxWidth = 20;
        int maxHeight = 15;
        try {
            File myObj = new File(pathname);
            int line = 0;
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                for (int i = 0; i < data.length(); i++){
                     char m = data.charAt(i);
                     if(m == '1'){
                         walls.add(new Vector2(maxWidth - i,maxHeight - line));
                     }
                }
                line++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return walls;
    }
}