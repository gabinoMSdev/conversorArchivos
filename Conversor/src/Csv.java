<<<<<<< Updated upstream
public class Csv {
=======
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Csv implements FileFormat {

    /**
     * 
     * @param file
     * @return
     * @throws IOException
     */
    @Override
    public static List<List<String>> extractContent(File file) throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'extractContent'");
    }

    
    @Override
    public boolean writeWithFormat(List<List<String>> content, File conversion, String name) throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'writeWithFormat'");
    }

  
>>>>>>> Stashed changes

}
