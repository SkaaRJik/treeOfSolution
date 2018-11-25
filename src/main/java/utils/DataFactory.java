package utils;

import treeOfSolution.Node;

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

    public static Node[][] convertDataToNode(String[][] data){
        Node[][] nodes = new Node[data.length-1][data[0].length];

        for (int i = 0; i < data.length-1; i++) {
            for (int j = 0; j < data[i].length; j++) {
                nodes[i][j] = new Node(data[0][j], data[i+1][j], i+1, (short) data[0].length);
            }
        }


        return nodes;
    }

}
