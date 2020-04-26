package cn.edu.cup.commondata;

import java.io.*;

public class Tools {

    public static String readFromFile(File file) {
        InputStreamReader inputStreamReader;
        BufferedReader reader;
        String laststr = "";
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            String tempString;
            while ((tempString = reader.readLine()) != null) {
                laststr += tempString;
            }
            reader.close();
        } catch (Exception e) {
        }
        return laststr;
    }

    public static void writeToFile(File file, String s) {
        try {
            file.createNewFile();
            try (PrintWriter printWriter = new PrintWriter(file, "utf-8")) {
                printWriter.println(s);
            } catch (Exception e) {
            }
        } catch (IOException e) {
        }
    }

}
