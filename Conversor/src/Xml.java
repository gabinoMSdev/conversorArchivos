import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Xml {

    public static List<Map<String, String>> extractContent(File original) throws IOException {
        List<Map<String, String>> contenido = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(original))) {
            Map<String, String> coche = null;
            String etiqueta = null;
            StringBuilder valor = new StringBuilder();
            boolean interior = false;

            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                
                if (linea.startsWith("<coche>")) {
                    coche = new LinkedHashMap<>();
                    contenido.add(coche);
                } 
                else if (linea.startsWith("</coche>")) {
                    coche = null;
                }
                else if (linea.startsWith("<") && !linea.startsWith("</") 
                        && !linea.startsWith("<coche") && !linea.startsWith("<coches")) {
                    etiqueta = linea.substring(1, linea.indexOf(">"));
                    int inicio = linea.indexOf(">") + 1;
                    int finalv = linea.lastIndexOf("<");
                    if (inicio < finalv) {
                        String value = linea.substring(inicio, finalv);
                        coche.put(etiqueta, value.trim());
                    }
                    etiqueta = null;
                }
                else if (etiqueta != null && coche != null) {
                    valor.append(linea);
                }
            }
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
        }
        return contenido;
    }

    public static boolean writeWithFormat(List<Map<String, String>> contenido, File conversion) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(conversion))) {
            bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            bw.write("<coches>\n");
            
            for (Map<String, String> coche : contenido) {
                bw.write("  <coche>\n");
                for (Map.Entry<String, String> entrada : coche.entrySet()) {
                    bw.write("    <" + entrada.getKey() + ">" + entrada.getValue() + "</" + entrada.getKey() + ">\n");
                }
                bw.write("  </coche>\n");
            }
            
            bw.write("</coches>\n");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        File original = new File("Docs/coches.xml");
        File conversion = new File("Docs/cochesCopy.xml");
        
        try {
            List<Map<String, String>> contenido = extractContent(original);
            writeWithFormat(contenido, conversion);
            System.out.println("Contenido extraído:");
            for (int i = 0; i < contenido.size(); i++) {
                for (Map.Entry<String, String> entrada : contenido.get(i).entrySet()) {
                    System.out.println("[" + entrada.getKey() + ", " + entrada.getValue() + "]");
                }
                System.out.println("Size map:" + contenido.get(i).size());
            }
            System.out.println("Size List: " + contenido.size());
            System.out.println("\nContenido XML completo:");
            try (BufferedReader br = new BufferedReader(new FileReader(conversion))) {
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
            }
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
