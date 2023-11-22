package slu.edu.ph.cs_212_9343_ramonsters;

import android.os.Environment;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileHandler {
    public Scanner fileReader;
    public PrintWriter printWriter;

    public void write(String email, String password, boolean tutor) {
        try {
            Environment Environment = null;
            String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/textfile.txt";
            FileWriter fileWriter = new FileWriter(filePath, true);

            PrintWriter printWriter = new PrintWriter(fileWriter);
            String message = "Hello, this is a message written to the file!";
            printWriter.println(message);
            printWriter.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String read() {
        fileReader = new Scanner("java/slu/edu/ph/cs_212_9343_ramonsters/accounts.txt");
        return fileReader.nextLine();
    }
}
