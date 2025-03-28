
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * <h1>Clase Gestor</h1>
 * 
 * <p>
 * Interfaz de usuario para el sistema de conversión de archivos.
 * </p>
 * <p>
 * Proporciona un menú interactivo para seleccionar archivos y formatos de
 * conversión.
 * </p>
 * 
 * <h2>Responsabilidades principales</h2>
 * <ul>
 * <li>Interacción con el usuario</li>
 * <li>Proceso de conversión</li>
 * <li>Visualización de información del estado</li>
 * <li>Manejo de errores y validaciones</li>
 * </ul>
 * @author Gabino y Manu
 */
public class Gestor {
    private static final Scanner sc = new Scanner(System.in);
    private Conversor conversor;

    /**
     * <h3>Constructor</h3>
     * <p>
     * Inicializa un nuevo Gestor con una instancia de Conversor.
     * </p>
     */
    public Gestor() {
        this.conversor = new Conversor();
    }

    /**
     * <h3>Menú principal</h3>
     * <p>
     * Muestra el menú interactivo y gestiona las opciones seleccionadas por el
     * usuario.
     * </p>
     * <p>
     * Opciones disponibles:
     * </p>
     * <ol>
     * <li>Seleccionar carpeta</li>
     * <li>Seleccionar archivo</li>
     * <li>Convertir archivo</li>
     * <li>Salir</li>
     * </ol>
     */
    public void menu() {
        String menu = "\n********** CONVERSOR **********" +
                "\n1. Seleccionar carpeta." +
                "\n2. Seleccionar archivo." +
                "\n3. Convertir archivo." +
                "\n0. Salir.";
        int opcion;
        boolean flag = true;
        while (flag) {
            System.out.println("\n\n\n\n\n********** INFORMACION **********\n" + info());
            System.out.println(menu);
            System.out.print("\nSeleccione opción: ");
            try {
                opcion = Integer.parseInt(sc.nextLine());
                switch (opcion) {
                    case 1 -> directorio();
                    case 2 -> {
                        try {
                            original();
                        } catch (Exception e) {
                            System.err.println("Error: " + e.getMessage());
                        }
                    }
                    case 3 -> {
                        try {
                            menuConversion();
                        } catch (Exception e) {
                            System.err.println("Error: " + e.getMessage());
                        }
                    }
                    case 0 -> {
                        System.out.println("Saliendo");
                        flag = false;
                    }
                    default -> System.out.println("Opción no válida");
                }
            } catch (NumberFormatException e) {
                System.err.println("Error: no se ha introducido un número entero.");
            }

        }
    }

    /**
     * <h3>Menú de conversión</h3>
     * <p>
     * Muestra las opciones de formato de conversión y gestiona el proceso.
     * </p>
     * 
     * @throws Exception
     */
    public void menuConversion() throws Exception {
        if (conversor.getOriginal() != null) {
            String menu = "\n********** FORMATO **********" +
                    "\n1. CSV" +
                    "\n2. JSON" +
                    "\n3. XML";
            System.out.println(menu);
            try {
                System.out.print("\nSeleccione opción: ");
                int opcion = Integer.parseInt(sc.nextLine());
                boolean flag = true;
                switch (opcion) {
                    case 1 -> conversor.setconversionExtension(FileExtension.CSV);
                    case 2 -> conversor.setconversionExtension(FileExtension.JSON);
                    case 3 -> conversor.setconversionExtension(FileExtension.XML);
                    default -> {
                        System.out.println("Opción no válida");
                        conversor.setconversionExtension(FileExtension.OTRO);
                        flag = false;
                    }
                }
                if (flag) {
                    System.out.print("Nombre del archivo destino: ");
                    String name = sc.nextLine();
                    String path = conversor.getDirectory().getPath() + "/" + name + fileExtensionToText();
                    try {
                        conversor.setConversion(new File(path));
                        convertir();
                    } catch (Exception e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                }
            } catch (NumberFormatException e) {
                System.err.println("Error: no se ha introducido un número entero.");
            }

        } else {
            throw new Exception("Archivo a convertir no seleccionado.");
        }

    }

    /**
     * <h3>Obtiene la extensión del archivo de conversión</h3>
     * 
     * @return
     */
    public String fileExtensionToText() {
        if (conversor.getConversionExtension() == FileExtension.CSV) {
            return ".csv";
        } else if (conversor.getConversionExtension() == FileExtension.JSON) {
            return ".json";
        } else if (conversor.getConversionExtension() == FileExtension.XML) {
            return ".xml";
        } else {
            return ".error";
        }
    }

    /**
     * <h3>Seleccion de directorio</h3>
     * <p>
     * Permite al usuario especificar el directorio de trabajo.
     * </p>
     */
    public void directorio() {
        System.out.print("\nDirectorio: ");
        String directorioPath = sc.nextLine();
        try {
            conversor.setDirectory(new File(directorioPath));
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * <h3>Seleccion de archivo original</h3>
     * <p>
     * Permite al usuario seleccionar un archivo del directorio actual para
     * conversión.
     * </p>
     * 
     * @throws Exception
     */
    public void original() throws Exception {
        if (conversor.getDirectory() != null) {
            System.out.print("\nSelecciona el archivo a convertir: ");
            try {
                int numArchivo = Integer.parseInt(sc.nextLine());
                conversor.setOriginal(conversor.getFiles()[numArchivo - 1]);
            } catch (NumberFormatException e) {
                System.err.println("Error: no se ha introducido un número entero.");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.err.println("Error: elije un archivo de la lista.");
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        } else {
            throw new Exception("Directorio no seleccionado.");
        }

    }

    /**
     * <h3>Información del estado</h3>
     * <p>
     * Genera un resumen del estado actual del conversor.
     * </p>
     * 
     * @return
     */
    public String info() {
        String info = "";
        if (conversor.getDirectory() != null) {
            info += "Directorio: " + conversor.getDirectory().getPath() + "\n";
            info += "\nFicheros en directorio:" + listarDirectorios() + "\n";
            if (conversor.getOriginal() != null) {
                info += "\nFichero para convertir: " + conversor.getOriginal().getPath();
            } else {
                info += "\nfichero para convertir: fichero no seleccionado.\n";
            }
        } else {
            info += "Directorio: directorio no seleccionado.\n";
            info += "Ficheros en el directorio: directorio no seleccionado.\n";
            info += "fichero para convertir: directorio no seleccionado.\n";
        }
        return info;
    }

    /**
     * <h3>Listado de directorios</h3>
     * <p>
     * Genera una lista numerada de los archivos en el directorio actual.
     * </p>
     * 
     * @return
     */
    public String listarDirectorios() {
        String directorios = "";
        for (int i = 0; i < conversor.getFiles().length; i++) {
            directorios += "\n" + (i + 1) + "- " + conversor.getFiles()[i];
        }
        return directorios;
    }

    /**
     * <h3>Ejecución de la conversión</h3>
     * <p>
     * Realiza la conversión del archivo
     * </p>
     * 
     * @throws IOException
     */
    public void convertir() throws IOException {
        try {
            if (conversor.getDirectory() != null && conversor.getOriginal() != null
                    && conversor.getConversionExtension() != FileExtension.OTRO) {
                switch (conversor.getConversionExtension()) {
                    case FileExtension.CSV -> Csv.writeWithFormat(conversor.getContent(), conversor.getConversion());
                    case FileExtension.JSON -> Json.writeWithFormat(conversor.getContent(), conversor.getConversion());
                    case FileExtension.XML -> Xml.writeWithFormat(conversor.getContent(), conversor.getConversion());
                    default -> throw new IOException("Extensión no reconocida.");
                }
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
