import java.io.File;
import java.io.IOException;
import java.util.List;

public interface FileFormat {

    /**
     * 
     * @param file
     * @return
     * @throws IOException
     */
    List<List<String>> extractContent(File file) throws IOException;
    boolean writeWithFormat(List<List<String>> content, File conversion, String name) throws IOException;
    
}
