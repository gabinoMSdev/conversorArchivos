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
                String[] cleanLine = line.trim().replaceAll("\"", "").split("[,:]");
                System.out.println(Arrays.toString(cleanLine));/////
                if (cleanLine != null) {
                    if (cleanLine[0].equals("}")) {
                        counter++;
                        content.add(new HashMap<String, String>());
                    } else {
                        content.get(counter).put(cleanLine[0].trim(), cleanLine[1].trim());
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("ERROR.");
        }
        return content;
    }

    public static boolean writeWithFormat(List<Map<String, String>> content, File conversion) throws IOException {
    }

}

public static void main(String[] args) {
    String frase1 = "\"juan\\\"juan";
    String frase2 = " {";
    String[] palabras1 = frase1.split("[^\\w.}]|(?<=\\D)\\.(?=\\D)");
    System.out.println(Arrays.toString(palabras1));
    System.out.println(palabras1.length);

    System.out.println("1");
    try {
        List<Map<String, String>> content = Json.extractContent(new File("Conversor\\src\\coches.json"));
        for (int i = 0; i < content.size(); i++) {
            for (Entry<String, String> entry : content.get(i).entrySet()) {
                System.out.println(entry.getKey() + "\t" + entry.getValue());
            }
        }
    } catch (Exception e) {
        System.err.println("ERROR.");
    }
}
