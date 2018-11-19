package utils;

import java.io.*;
import java.nio.charset.Charset;

public class DataFactory {


    public static String[][] readUsingFileReader(File file, Charset cs) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis, cs);
        BufferedReader br = new BufferedReader(isr);


        /*br.lines().forEach(s -> {
            ArrayList<String> line = new ArrayList<>();
            Collections.addAll(line, s.split(";"));
            data.add(line);
        });*/

         String[][] data = br.lines().map(s -> s.split("[;]"))
                .toArray(String[][]::new);

        br.close();
        return data;
    }

}
