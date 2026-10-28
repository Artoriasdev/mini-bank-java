package com.minibanco.dominio;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class PruebaEquals {
    public static void main(String[] args) {
        Cuenta a = new CuentaCorriente("200", "Luisa", new BigDecimal("200"));
        Cuenta b = new CuentaCorriente("200", "Luisa", new BigDecimal("200"));

        //Prueba 1
        System.out.println("1. a.equals(b): " + a.equals(b));

        //Prueba 2
        Set<Cuenta> set = new HashSet<>();
        set.add(a);
        set.add(b);
        System.out.println("2. Tamaño del set: " + set.size());

        Cuenta d = new CuentaCorriente("300", "Franco", new BigDecimal("100"));
        Cuenta e = new CuentaAhorros("300", "Franco", new BigDecimal("100"));

        //Prueba 3
        Set<Cuenta> set2 = new HashSet<>();
        set2.add(d);
        set2.add(e);
        System.out.println("3. Tamaño del set2: " + set2.size());

        //Prueba 4
        System.out.println("4. x.hashCode() == y.hashCode(): " + (d.hashCode() == e.hashCode()));
    }
}
