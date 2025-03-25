import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface FileFormat {
    List<Map<String,String>> extractContent(File file) throws IOException;
    boolean writeWithFormat(List<Map<String,String>>content, File conversion, String name) throws IOException;
    
}
