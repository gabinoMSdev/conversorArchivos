import java.io.EOFException;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class Conversor {
    private File directory;
    private File[] files;
    private File original;
    private File conversion;
    private FileExtension originalExtension;
    private FileExtension conversionExtension;
    private List<Map<String,String>> content;

    public Conversor() {
    }

    public File getDirectory() {
        return this.directory;
    }

    public void setDirectory(File folder) throws Exception {
        if (folder.exists()) {
            if (folder.isDirectory()) {
                this.directory = folder;
                setFiles();
            } else {
                throw new Exception("No es un directorio.");
            }
        } else {
            throw new Exception("Directorio no existe.");
        }

    }

    public File[] getFiles() {
        return this.files;
    }

    private void setFiles() {
        this.files = directory.listFiles();
    }

    public File getOriginal() {
        return this.original;
    }

    public void setOriginal(File original) throws Exception {
        if (original.isFile()) {
            if (Arrays.asList(files).contains(original)) {
                this.original = original;
                setOriginalExtension();
            } else {
                throw new Exception("Archivo no encontrado en directorio de búsqueda.");
            }
        } else {
            throw new Exception("El archivo no existe.");
        }

    }

    public File getConversion() {
        return this.conversion;
    }

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

    public FileExtension getOriginalExtension() {
        return this.originalExtension;
    }

    private void setOriginalExtension() throws Exception{
        if (original.getPath().endsWith(".csv")) {
            this.originalExtension = FileExtension.CSV;
        }else if (original.getPath().endsWith(".json")) {
            this.originalExtension = FileExtension.JSON;
        }else if (original.getPath().endsWith(".xml")) {
            this.originalExtension = FileExtension.JSON;
        }else{
            throw new Exception("Extensión no reconocida.");
        }
    }

    public FileExtension getConversionExtension() {
        return this.conversionExtension;
    }

    public void setconversionExtension(FileExtension conversionExtension) {
        this.conversionExtension = conversionExtension;
    }

    public List<Map<String,String>> getContent() {
        return this.content;
    }

    private void setContent() throws Exception{
        switch (originalExtension) {
            case FileExtension.CSV -> this.content = Csv.extractContent(original);
            case FileExtension.JSON -> this.content = Json.extractContent(original);
            case FileExtension.XML -> this.content = Xml.extractContent(original);
            default -> throw new Exception("Extensión no reconocida.");
        }
    }

}
