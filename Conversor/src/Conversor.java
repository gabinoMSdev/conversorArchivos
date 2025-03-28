import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * *
 * <h1>Clase Conversor</h1>
 * 
 * <p>
 * Gestiona la conversión entre diferentes formatos de archivos (CSV, JSON,
 * XML).
 * </p>
 * 
 * <h2>Responsabilidades principales</h2>
 * <ul>
 * <li>Gestión de directorios y archivos</li>
 * <li>Detección de formatos</li>
 * <li>Conversión entre formatos soportados</li>
 * </ul>
 * 
 * @see FileExtension
 * @see Csv
 * @see Json
 * @see Xml
 * @author Gabino
 */
public class Conversor {
    private File directory;
    private File[] files;
    private File original;
    private File conversion;
    private FileExtension originalExtension;
    private FileExtension conversionExtension;
    private List<Map<String, String>> content;

    /**
     * <h3>Constructor</h3>
     * <p>
     * Inicializa un nuevo conversor con todos los valores en estado nulo
     * </p>
     */
    public Conversor() {
        directory = null;
        files = null;
        original = null;
        conversion = null;
        originalExtension = FileExtension.OTRO;
        conversionExtension = FileExtension.OTRO;

    }

    /**
     * <h3>Obtiene el directorio actual</h3>
     * 
     * @return
     */
    public File getDirectory() {
        return this.directory;
    }

    /**
     * <h3>Establece el directorio de trabajo</h3>
     * <p>
     * Configura el directorio para las operaciones de conversión.
     * </p>
     * 
     * @param folder
     * @throws Exception
     */
    public void setDirectory(File folder) throws Exception {
        if (folder.exists()) {
            if (folder.isDirectory()) {
                this.directory = folder;
                setFiles();
                original = null;
                content = null;
            } else {
                throw new Exception("No es un directorio.");
            }
        } else {
            throw new Exception("Directorio no existe.");
        }

    }

    /**
     * <h3>Obtiene la lista de archivos</h3>
     * 
     * @return
     */
    public File[] getFiles() {
        return this.files;
    }

    /**
     * <h3>Actualiza la lista de archivos del directorio actual</h3>
     */
    private void setFiles() {
        this.files = directory.listFiles();
    }

    /**
     * <h3>Obtiene el archivo original</h3>
     * 
     * @return
     */
    public File getOriginal() {
        return this.original;
    }

    /**
     * <h3>Establece el archivo original</h3>
     * <p>
     * Configura el archivo fuente para la conversión y realiza validaciones
     * </p>
     * <ul>
     * <li>Verifica que sea un archivo valido</li>
     * <li>Comprueba que exista en el directorio actual</li>
     * <li>Detecta su extensión</li>
     * </ul>
     * 
     * @param original
     * @throws Exception
     */
    public void setOriginal(File original) throws Exception {
        if (original.isFile()) {
            if (Arrays.asList(files).contains(original)) {
                this.original = original;
                setOriginalExtension();
                setContent();
            } else {
                throw new Exception("Archivo no encontrado en directorio de búsqueda.");
            }
        } else {
            throw new Exception("No es un archivo.");
        }

    }

    /**
     * <h3>Obtiene el archivo de conversión</h3>
     * 
     * @return
     */
    public File getConversion() {
        return this.conversion;
    }

    /**
     * *
     * <h3>Establece el archivo de conversión</h3>
     * <p>
     * Configura el archivo destino para la conversión con validaciones
     * </p>
     * <ul>
     * <li>Verifica que no exista previamente</li>
     * <li>Comprueba que esté en el mismo directorio que el original</li>
     * </ul>
     * 
     * @param conversion
     * @throws Exception
     */
    public void setConversion(File conversion) throws Exception {
        if (!conversion.exists()) {
            if (conversion.getParent().equals(original.getParent())) {
                this.conversion = conversion;
            } else {
                throw new Exception("El archivo tiene que estar en la misma carpeta que el original.");
            }
        } else {
            throw new Exception("El archivo ya existe.");
        }

    }

    /**
     * <h3>Obtiene la extensión del archivo original</h3>
     * 
     * @return
     */
    public FileExtension getOriginalExtension() {
        return this.originalExtension;
    }

    /**
     * <h3>Detecta la extensión del archivo original</h3>
     * <p>
     * Determina el formato del archivo
     * </p>
     * <ul>
     * <li>.csv → CSV</li>
     * <li>.json → JSON</li>
     * <li>.xml → XML</li>
     * </ul>
     * 
     * @throws Exception
     */
    private void setOriginalExtension() throws Exception {
        if (original.getPath().endsWith(".csv")) {
            this.originalExtension = FileExtension.CSV;
        } else if (original.getPath().endsWith(".json")) {
            this.originalExtension = FileExtension.JSON;
        } else if (original.getPath().endsWith(".xml")) {
            this.originalExtension = FileExtension.XML;
        } else {
            throw new Exception("Extensión no reconocida.");
        }
    }

    /**
     * <h3>Obtiene la extensión</h3>
     * 
     * @return
     */
    public FileExtension getConversionExtension() {
        return this.conversionExtension;
    }

    /**
     * <h3>Establece la extensión de conversión</h3>
     * <p>
     * Configura el formato de destino.
     * </p>
     * 
     * @param conversionExtension
     */
    public void setconversionExtension(FileExtension conversionExtension) {
        this.conversionExtension = conversionExtension;
    }

    /**
     * <h3>Obtiene el contenido procesado</h3>
     * 
     * @return
     */
    public List<Map<String, String>> getContent() {
        return this.content;
    }

    /**
     * <h3>Carga el contenido del archivo original</h3>
     * <p>
     * Extrae los datos del archivo según su formato usando las clases
     * correspondientes
     * </p>
     * <ul>
     * <li>CSV</li>
     * <li>JSON</li>
     * <li>XML</li>
     * </ul>
     * 
     * @throws Exception
     */
    private void setContent() throws Exception {
        switch (originalExtension) {
            case FileExtension.CSV -> this.content = Csv.extractContent(original);
            case FileExtension.JSON -> this.content = Json.extractContent(original);
            case FileExtension.XML -> this.content = Xml.extractContent(original);
            default -> throw new Exception("Extensión no reconocida.");
        }
    }

}
