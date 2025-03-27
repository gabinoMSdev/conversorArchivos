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

public class Csv {
    private static String[] separador(String linea) {
        return linea.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
    }

    public static List<Map<String, String>> extractContent(File original) throws IOException {

        List<Map<String, String>> contenido = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(original))) {
            String linea;
            String[] cabecera = null;

            while ((linea = br.readLine()) != null) {
                if (cabecera == null) {

                    cabecera = linea.trim().split(",");

                    for (int i = 0; i < cabecera.length; i++) {
                        cabecera[i] = cabecera[i].trim();
                    }
                } else {

                    String[] values = separador(linea);
                    if (values.length != cabecera.length) {
                        continue;
                    }

                    Map<String, String> filas = new LinkedHashMap<>();
                    for (int i = 0; i < cabecera.length; i++) {
                        filas.put(cabecera[i], values[i].trim());
                    }
                    contenido.add(filas);
                }
            }
        }
        return contenido;
    }

    public static void writeWithFormat(List<Map<String, String>> contenido, File conversion) throws IOException {
        if (contenido == null || contenido.isEmpty()) {
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(conversion))) {

            String[] cabecera = contenido.get(0).keySet().toArray(new String[0]);
            bw.write(String.join(",", cabecera) + "\n");

            for (Map<String, String> fila : contenido) {
                String[] valores = new String[cabecera.length];
                for (int i = 0; i < cabecera.length; i++) {
                    valores[i] = fila.get(cabecera[i]);
                }
                bw.write(String.join(",", valores) + "\n");
            }
        }
    }

    public static void printResults(List<Map<String, String>> contenido, File convertido) {
        System.out.println("Contenido extraído:");
        for (int i = 0; i < contenido.size(); i++) {
            for (Map.Entry<String, String> entrada : contenido.get(i).entrySet()) {
                System.out.println("[" + entrada.getKey() + ", " + entrada.getValue() + "]");
            }
            System.out.println("Size map:" + contenido.get(i).size());
        }
        System.out.println("Size List: " + contenido.size());

        System.out.println("\nContenido CSV generado:");
        try (BufferedReader br = new BufferedReader(new FileReader(convertido))) {
            br.lines().limit(20).forEach(System.out::println);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        File original = new File("Docs/coches.csv");
        File conversion = new File("Docs/cochesCopy.csv");
        
        try {
            List<Map<String, String>> contenido = extractContent(original);
            writeWithFormat(contenido, conversion);
            System.out.println("Contenido extraído:");
            for (int i = 0; i < contenido.size(); i++) {
                for (Map.Entry<String, String> entry : contenido.get(i).entrySet()) {
                    System.out.println("[" + entry.getKey() + ", " + entry.getValue() + "]");
                }
                System.out.println("Size map:" + contenido.get(i).size());
            }
            System.out.println("Size List: " + contenido.size());
            System.out.println("\nContenido CSV completo:");
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
