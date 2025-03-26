import java.io.EOFException;
import java.io.File;
import java.util.Arrays;

import javax.swing.text.html.CSS;

public class Conversor {
    private File directory;
    private File[] files;
    private File original;
    private File conversion;
    private FileExtension originalExtension;
    private FileExtension conversionExtension;
    private List<List<String>> content;

    public Conversor() {
    }

    /**
     * 
     * @return
     */
    public File getFolder() {
        return this.directory;
    }

    /**
     * 
     * @param folder
     * @throws Exception
     */
    public void setFolder(File folder) throws Exception {
        if (folder.exists()) {
            if (folder.isDirectory()) {
                this.directory = folder;
            } else {
                throw new Exception("No es un directorio.");
            }
        } else {
            throw new Exception("Directorio no existe.");
        }

    }

    /**
     * 
     * @return
     */
    public File[] getFiles() {
        return this.files;
    }

    private void setFiles() {
        this.files = directory.listFiles();
    }

    /**
     * 
     * @return
     */
    public File getOriginal() {
        return this.original;
    }

    /**
     * 
     * @param original
     * @throws Exception
     */
    public void setOriginal(File original) throws Exception {
        if (original.isFile()) {
            if (Arrays.asList(files).contains(original)) {
                this.original = original;
            } else {
                throw new Exception("Archivo no encontrado en directorio de búsqueda.");
            }
        } else {
            throw new Exception("El archivo no existe.");
        }

    }

    /**
     * 
     * @return
     */
    public File getConversion() {
        return this.conversion;
    }

    /**
     * 
     * @param conversion
     * @throws Exception
     */
    public void setConversionName(File conversion) throws Exception {
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
     * 
     * @return
     */
    public FileExtension getOriginalExtension() {
        return this.originalExtension;
    }

    /**
     * 
     * @throws Exception
     */
    public void setOriginalExtension() throws Exception {
        if (original.getPath().endsWith(".csv")) {
            this.originalExtension = FileExtension.CSV;
        } else if (original.getPath().endsWith(".json")) {
            this.originalExtension = FileExtension.JSON;
        } else if (original.getPath().endsWith(".xml")) {
            this.originalExtension = FileExtension.JSON;
        } else {
            throw new Exception("Extensión no reconocida.");
        }
    }

    /**
     * 
     * @return
     */
    public FileExtension getConversionExtension() {
        return this.conversionExtension;
    }

    /**
     * 
     * @param conversionExtension
     */
    public void setconversionExtension(FileExtension conversionExtension) {
        this.conversionExtension = conversionExtension;
    }

    /**
     * 
     * @return
     */
    public List<List<String>> getContent() {
        switch (originalExtension) {
            case FileExtension.CSV -> return Csv.extractContent(original);
            case FileExtension.JSON -> return Json.extractContent(original);
            case FileExtension.XML -> return Xml.extractContent(original);
                
                
        
            default -> ;
        }
        
        
        return this.content;
    }

    /**
     * 
     * @param content
     */
    public void setContent(List<List<String>> content) {
        this.content = content;
    }

}
