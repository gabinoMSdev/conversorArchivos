import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.print.DocFlavor.STRING;

public class Json {

    public static List<Map<String, String>> extractContent(File file) throws IOException {
        String line = "";
        List<Map<String, String>> content = new ArrayList<>();
        content.add(new HashMap<String, String>());
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            int counter = 0;
            while ((line = br.readLine()) != null) {
                String[] cleanLine = line.split("(?=})|[^\\w.]+");
                System.out.println(Arrays.toString(cleanLine));
                if (cleanLine != null) {
                    if (cleanLine[0].equals("}")) {
                        counter++;
                        content.add(new HashMap<String, String>());
                    } else if (cleanLine.length == 2) {
                        content.get(counter).put(cleanLine[0], cleanLine[1]);
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("ERROR.");
        }
        return content;
    }

    /*
     * public boolean writeWithFormat(List<Map<String, String>> content, File
     * conversion) throws IOException {
     * 
     * }
     */

}

public static void main(String[] args) {
    String frase1 = "[";
    String frase2 = " {";
    String frase3 = "\"Marca\": \"Toyota\",";
    String[] palabras1 = frase3.split("(?=})|[^\\w.]+");
    System.out.println(Arrays.toString(palabras1));
    System.out.println(palabras1.length);

    System.out.println("1");
    try {
        List<Map<String, String>> content = Json.extractContent(new File("Conversor\\src\\coches.json"));
        for (int i = 0; i < content.size(); i++) {
            for (Entry<String, String> entry : content.get(i).entrySet()) {
                System.out.println(entry.getKey() + "\t" + entry.getValue());
                System.out.println(i);
            }
        }
    } catch (Exception e) {
        System.err.println("ERROR.");
    }
}
