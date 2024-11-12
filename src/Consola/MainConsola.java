package Consola;

import java.util.Scanner;

public class MainConsola {
	
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int userType = selectUserType();

        switch (userType) {
            case 1 -> launchEstudianteConsole();
            case 2 -> launchProfesorConsole();
            default -> System.out.println("Opción inválida. Intente nuevamente.");
        }
    }

    private static int selectUserType() {
        System.out.println("Bienvenido al sistema");
        System.out.println("Seleccione su tipo de usuario:");
        System.out.println("1. Estudiante");
        System.out.println("2. Profesor");
        System.out.print("Ingrese el número de la opción: ");
        
        return scanner.nextInt();
    }

    private static void launchEstudianteConsole() {
        try {
            ConsolaEstudiante.main(null); 
        } catch (Exception e) {
            System.out.println("Error en la consola de estudiante: " + e.getMessage());
        }
    }

    private static void launchProfesorConsole() {
        try {
            ConsolaProfesor.main(null); 
        } catch (Exception e) {
            System.out.println("Error en la consola de profesor: " + e.getMessage());
        }
    }

}