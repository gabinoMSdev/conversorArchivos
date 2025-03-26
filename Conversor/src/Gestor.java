
import java.io.File;
import java.util.Scanner;

public class Gestor {
    private static final Scanner sc = new Scanner(System.in);
    private static Conversor conversor;

    public Gestor() {
        this.conversor = new Conversor();
    }

    public static void menu() {
        String menu = "\n=== CONVERSOR ==="+
            "\n1. Seleccionar carpeta."+
            "\n2. Seleccionar archivo."+
            "\n3. Convertir archivo."+
            "\n0. Salir.";
        int opcion;
        boolean flag = true;
        while (flag) {
            System.out.println(menu);
            System.out.print("Seleccione opción: ");
            opcion = Integer.parseInt(sc.nextLine());
            switch (opcion) {
                case 1 -> ;
                case 2 -> ;
                case 3 -> ;
                case 0 -> {System.out.println("Saliendo"); flag = false;}
                default -> System.out.println("Opción no válida");
            }
        }
    }

    public static void menuConversion() {
        String menu = "\n=== FORMATO ===" +
                "\n1. CSV" +
                "\n2. JSON" +
                "\n3. XML";
        System.out.println(menu);
        System.out.print("Seleccione opción: ");
        int opcion = Integer.parseInt(sc.nextLine());
        boolean flag = true;
        switch (opcion) {
            case 1 -> conversor.setconversionExtension(FileExtension.CSV);
            case 2 -> conversor.setconversionExtension(FileExtension.CSV);
            case 3 -> conversor.setconversionExtension(FileExtension.CSV);
            default -> {
                System.out.println("Opción no válida");
                flag = false;
            }
        }
        if (flag) {
            System.out.print("Nombre del archivo destino: ");
            String name = sc.nextLine();
            String path = conversor.getDirectory().getPath()+name+conversor.getConversionExtension();
            try {
                conversor.setConversion(new File(path));
                
            } catch (Exception e) {
                System.err.println("Error: "+e.getMessage());
            }
        }
    }

    public static void conversorName(){

    }

    public static void conversion(){
        
    }

    public static String info(){
        return conversor
    }
}
