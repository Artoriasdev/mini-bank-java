package com.minibanco.dominio;

import java.math.BigDecimal;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Repositorio<Cuenta, String> repo = new Repositorio<>(Cuenta::getId);

        Cuenta corriente = new CuentaCorriente("100", "Franco", new BigDecimal("500"));
        repo.guardar(corriente);

        Scanner sc = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("""
            === MINI-BANCO ===
            1. Listar cuentas
            2. Depositar
            3. Retirar
            4. Crear cuenta
            5. Ver movimientos
            0. Salir
            Elige una opción:\s""");
            String opcion = sc.nextLine().trim();

            switch (opcion) {
                case "1" -> {
                    for (Cuenta c: repo.todos()) {
                        System.out.println(c);
                    }
                }
                case "2" -> {
                    Cuenta cuenta = pedirCuenta(sc, repo);
                    if (cuenta == null) {
                        System.out.println("Cuenta no encontrada");
                    }else {
                        System.out.println("Monto a depositar");
                        try{
                            BigDecimal monto = new BigDecimal(sc.nextLine().trim());
                            cuenta.depositar(monto);
                            System.out.println("Deposito realizado. Saldo: " + cuenta.getSaldo());
                        }
                        catch (NumberFormatException e) {
                            System.out.println("Error: debes ingresar un monto valido.");
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
                case "3" -> {
                    Cuenta cuenta = pedirCuenta(sc, repo);
                    if (cuenta == null) {
                        System.out.println("Cuenta no encontrada.");
                    }else {
                        System.out.println("Monto a retirar");
                        try {
                            BigDecimal monto = new BigDecimal(sc.nextLine().trim());
                            cuenta.retirar(monto);
                            System.out.println("Monto retirado. Saldo: " + cuenta.getSaldo());
                        } catch (NumberFormatException e) {
                            System.out.println("Error: debes ingresar un monto valido.");
                        } catch (IllegalArgumentException | SaldoInsuficienteException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
                case "4" -> {
                    System.out.println("Ingrese id de la cuenta");
                    String id = sc.nextLine().trim();

                    if (repo.buscarPorId(id) !=null) {
                        System.out.println("La cuenta ingresada ya existe");
                    } else {
                        System.out.println("Ingrese titular");
                        String titular = sc.nextLine().trim();

                        System.out.println("Ingrese saldo");
                        try{
                            BigDecimal saldo = new BigDecimal(sc.nextLine().trim());
                            System.out.println("""
                            === Tipo de Cuenta ===
                            1. Cuenta Corriente
                            2. Cuenta Ahorros
                            Elige una opción:\s""");
                            String opc = sc.nextLine().trim();
                            switch (opc) {
                                case "1" -> {
                                    Cuenta co = new CuentaCorriente(id, titular, saldo);
                                    repo.guardar(co);
                                    System.out.println("Cuenta creada con exito");
                                }
                                case "2" -> {
                                    Cuenta ca = new CuentaAhorros(id, titular, saldo);
                                    repo.guardar(ca);
                                    System.out.println("Cuenta creada con exito");
                                }
                                default -> System.out.println("Opcion no valida");
                            }
                        }catch(NumberFormatException e) {
                            System.out.println("Error: debe ingresar un monto valido");
                        }catch(IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
                case "5" -> {
                    Cuenta cuenta = pedirCuenta(sc, repo);
                    if (cuenta != null) {
                        if (cuenta.getMovimientos().isEmpty()) {
                            System.out.println("No hay movimientos para esta cuenta");
                        } else {
                            for (Movimiento m : cuenta.getMovimientos()) {
                                System.out.println(m);
                            }
                        }
                    } else {
                        System.out.println("Cuenta no encontrada.");
                    }
                }
                case "0" -> salir = true;
                default -> System.out.println("Opcion no valida");
            }
        }

        sc.close();
    }

    private static Cuenta pedirCuenta(Scanner sc, Repositorio<Cuenta, String> repo) {
        System.out.println("Id de la cuenta");
        String id = sc.nextLine().trim();
        return repo.buscarPorId(id);
    }
}
