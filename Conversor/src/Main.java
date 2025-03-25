import java.io.File;

public class Main {
    public static void main(String[] args) throws Exception {
        Conversor conversor = new Conversor();
        try {
            conversor.setFolder(new File("Conversor\\src"));
            conversor.setOriginal(new File("Conversor\\src\\coches.csv"));
            conversor.setFiles();
        } catch (Exception e) {
            
        }
        

    }
}
