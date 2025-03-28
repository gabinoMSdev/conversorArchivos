import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
/**
 * <h1>Procesador de Archivos JSON</h1>
 * 
 * <p>
 * Esta clase proporciona métodos para extraer y escribir datos en formato JSON.
 * </p>
 *
 * <h2>Métodos Principales</h2>
 * <ul>
 * <li>extractContent(File) - Extrae datos de un archivo JSON</li>
 * <li>writeWithFormat(List, File) - Escribe datos en formato JSON</li>
 * </ul>
 * 
 * @author gabino
 * @version 1.0
 */
public class Json {
    /**
     * <h3>Extrae contenido de un archivo JSON</h3>
     * 
     * <p>
     * Lee un archivo JSON y extrae los datos, organizándolos en una
     * estructura de Lista de Mapas donde cada mapa representa las propiedades.
     * </p>
     * @see LinkedHashMap
     * @see ArrayList
     * @see BufferedReader
     * @param original
     * @return
     * @throws IOException
     */
    public static List<Map<String, String>> extractContent(File original) throws IOException {
        String line = "";
        List<Map<String, String>> content = new ArrayList<>();
        // content.add(new HashMap<String, String>());
        try (BufferedReader br = new BufferedReader(new FileReader(original))) {
            int counter = -1;
            while ((line = br.readLine()) != null) {
                String[] cleanLine = line.trim().replaceAll("\"", "").split("[,:]");
                if (cleanLine != null) {
                    if (cleanLine[0].equals("{")) {
                        counter++;
                        content.add(new HashMap<String, String>());
                    } else if (cleanLine.length == 2) {
                        content.get(counter).put(cleanLine[0].trim(), cleanLine[1].trim());
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
        }
        return content;
    }
    /**
     * <h3>Escribe datos en formato JSON</h3>
     * 
     * <p>
     * Genera un archivo JSON bien formado a partir de los datos proporcionados.
     * El nombre del elemento raíz sale del nombre del archivo de destino.
     * </p>
     *
     * @param contenido  Lista de mapas con los datos a escribir
     * @param conversion Archivo de destino donde se guardará el JSON.
     * @throws IOException Si ocurre un error al escribir el archivo
     * 
     * @see BufferedWriter
     * @see Map
     * @see List
     */
    public static void writeWithFormat(List<Map<String, String>> content, File conversion) throws IOException {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(conversion))) {
            bw.write("[\n");
            int counterMap;
            for (int i = 0; i < content.size(); i++) {
                bw.write("\t{\n");
                counterMap = 0;
                for (Map.Entry<String, String> entry : content.get(i).entrySet()) {
                    if (entry.getKey().equals("Año") || entry.getKey().equals("Precio")) {
                        bw.write("\t\"" + entry.getKey() + "\":  " + entry.getValue());
                    } else {
                        bw.write("\t\"" + entry.getKey() + "\":  \"" + entry.getValue() + "\"");
                    }
                    if (counterMap < content.get(i).size() - 1) {
                        bw.write(",\n");
                    } else {
                        bw.write("\n");
                    }
                    counterMap++;
                }
                if (i < content.size() - 1) {
                    bw.write("\t},\n");
                } else {
                    bw.write("\t}\n");
                }

            }
            bw.write("]\n");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    /**
     * <h2>Main de pruebas para la clase JSON</h2>
     * @param args
     */
    public static void main(String[] args) {
        File original = new File("Docs/coches.json");
        File conversion = new File("Docs/cochesCopy.json");
        try {
            List<Map<String, String>> content = Json.extractContent(original);
            Json.writeWithFormat(content, conversion);
            for (int i = 0; i < content.size(); i++) {
                for (Map.Entry<String, String> entry : content.get(i).entrySet()) {
                    System.out.println("[" + entry.getKey() + ", " + entry.getValue() + "]");
                }
                System.out.println("Size map:" + content.get(i).size());
            }
            System.out.println("Size List: " + content.size());

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}