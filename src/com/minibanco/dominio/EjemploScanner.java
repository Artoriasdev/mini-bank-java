package com.minibanco.dominio;

import java.math.BigDecimal;
import java.util.Scanner;

public class EjemploScanner {
    public static void main(String[] args) {
        String menu = """
                === MENÚ ===
                1. Saludar
                2. Sumar un monto
                0. Salir
                Elige una opción:\s""";

        BigDecimal total = BigDecimal.ZERO;

        try (Scanner sc = new Scanner(System.in)) {
            boolean salir = false;
            while (!salir) {
                System.out.print(menu);
                String opcion = sc.nextLine().trim();

                switch (opcion) {
                    case "1" -> {
                        System.out.print("Tu nombre: ");
                        String nombre = sc.nextLine();
                        System.out.println("Hola, " + nombre);
                    }
                    case "2" -> {
                        System.out.print("Monto: ");
                        try {
                            BigDecimal monto = new BigDecimal(sc.nextLine().trim());
                            total = total.add(monto);
                            System.out.println("Total acumulado: " + total);
                        } catch (NumberFormatException e) {
                            System.out.println("Eso no es un número válido.");
                        }
                    }
                    case "0" -> salir = true;
                    default -> System.out.println("Opción no válida.");
                }
            }
        }
    }
}