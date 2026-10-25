package com.minibanco.dominio;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        Cuenta cto = new CuentaCorriente("100", "Franco", new BigDecimal("500"));
        cto.retirar(new BigDecimal("100"));
        System.out.println(cto);
        cto.depositar(new BigDecimal("45"));
        System.out.println(cto);

        Cuenta cta = new CuentaAhorros("101", "Piero", new BigDecimal("500"));
        cta.retirar(new BigDecimal("100"));
        System.out.println(cta);
        cta.depositar(new BigDecimal("25"));
        System.out.println(cta);

        try{
            cto.retirar(new BigDecimal("500"));
        }catch (SaldoInsuficienteException e) {
            System.out.println(e.getMessage());
        }

        Cuenta cta2 = new CuentaAhorros("102", "Rosa", new BigDecimal("455"));

        try{
            cta2.retirar(new BigDecimal("455"));
        }catch (SaldoInsuficienteException e) {
            System.out.println(e.getMessage());
        }


    }
}
