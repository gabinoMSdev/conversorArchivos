
import java.util.Scanner;

public class Gestor {
    private final Scanner sc = new Scanner(System.in);
    private boolean flag = true;
    private Conversor conversor;

    public Gestor(){
        this.conversor = new Conversor();
    }

    public void menu() {
        while (flag) {
            System.out.println("\n=== MENÚ PRINCIPAL ===");
            System.out.println("1. Seleccionar carpeta");
            System.out.println("2. Seleccionar archivo");
            System.out.println("3. Convertir archivo");
            System.out.println("0. Salir");
            System.out.print("Seleccione opción: ");

            switch (sc.nextLine()) {

                /*
                 * case "1" -> ;
                 * case "2" -> ;
                 */
                case "3" -> submenu();
                case "0" -> flag = false;
                default -> System.out.println("Opción no válida");
            }
        }
    }

    private void menuConversion() {
        System.out.println("\n=== SUBMENÚ CONVERSIÓN ===");
        System.out.println("1. CSV");
        System.out.println("2. JSON");
        System.out.println("3. XML");
        System.out.println("4. Volver al menú principal");
        System.out.print("\nSeleccione formato de conversión: ");

        switch (sc.nextLine()) {
            /*
             * case "1" -> ;
             * case "2" -> ;
             * case "3" -> ;
             */
            case "4" -> menu();
            default -> System.out.println("Opción no válida");
        }
    }

    public void update(){
        
    }
}
