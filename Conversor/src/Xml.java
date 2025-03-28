import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <h1>Procesador de Archivos XML</h1>
 * 
 * <p>
 * Esta clase proporciona métodos para extraer y escribir datos en formato XML.
 * </p>
 *
 * <h2>Métodos Principales</h2>
 * <ul>
 * <li>extractContent(File) - Extrae datos de un archivo XML</li>
 * <li>writeWithFormat(List, File) - Escribe datos en formato XML</li>
 * </ul>
 * 
 * @author Manu
 * @version 1.0
 */
public class Xml {
    /**
     * <h3>Extrae contenido de un archivo XML</h3>
     * 
     * <p>
     * Lee un archivo XML y extrae los datos, organizándolos en una
     * estructura de Lista de Mapas donde cada mapa representa las propiedades.
     * </p>
     * 
     * @see LinkedHashMap
     * @see ArrayList
     * @see BufferedReader
     * @author Manu
     * @param original
     * @return
     * @throws IOException
     */
    public static List<Map<String, String>> extractContent(File original) throws IOException {
        List<Map<String, String>> contenido = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(original))) {
            Map<String, String> coche = null;
            String etiqueta = null;
            String valor = "";
            boolean interior = false;

            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();

                if (linea.startsWith("<coche>")) {
                    coche = new LinkedHashMap<>();
                    contenido.add(coche);
                } else if (linea.startsWith("</coche>")) {
                    coche = null;
                } else if (linea.startsWith("<") && !linea.startsWith("</")
                        && !linea.startsWith("<coche") && !linea.startsWith("<coches")) {
                    etiqueta = linea.substring(1, linea.indexOf(">"));
                    int inicio = linea.indexOf(">") + 1;
                    int finalv = linea.lastIndexOf("<");
                    if (inicio < finalv) {
                        String value = linea.substring(inicio, finalv);
                        coche.put(etiqueta, value.trim());
                    }
                    etiqueta = null;
                } else if (etiqueta != null && coche != null) {
                    valor += linea;
                }
            }
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
        }
        return contenido;
    }

    /**
     * <h3>Escribe datos en formato XML</h3>
     * 
     * <p>
     * Genera un archivo XML bien formado a partir de los datos proporcionados.
     * El nombre del elemento raíz sale del nombre del archivo de destino.
     * </p>
     *
     * @param contenido  Lista de mapas con los datos a escribir
     * @param conversion Archivo de destino donde se guardará el XML
     * @throws IOException Si ocurre un error al escribir el archivo
     * 
     * @see BufferedWriter
     * @see Map
     * @see List
     */
    public static void writeWithFormat(List<Map<String, String>> contenido, File conversion) throws IOException {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(conversion))) {
            bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            // introducir titulo
            String[] directorioPartes = conversion.getPath().split("\\\\");
            System.out.println(Arrays.toString(directorioPartes));
            String[] nombrePartes = directorioPartes[directorioPartes.length - 1].split("\\.");
            System.out.println(Arrays.toString(nombrePartes));
            String titulo = nombrePartes[0];
            bw.write("<" + titulo + ">\n");
            for (Map<String, String> coche : contenido) {
                bw.write("  <coche>\n");
                for (Map.Entry<String, String> entrada : coche.entrySet()) {
                    bw.write("    <" + entrada.getKey() + ">" + entrada.getValue() + "</" + entrada.getKey() + ">\n");
                }
                bw.write("  </coche>\n");
            }

            bw.write("</" + titulo + ">\n");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * <h2>Main de pruebas para la clase Xml</h2>
     * 
     * @param args
     */
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
