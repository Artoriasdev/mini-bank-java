package com.minibanco.dominio;

import java.math.BigDecimal;

public class PruebaReportes {
    public static void main(String[] args) {
        Cuenta cx = new CuentaCorriente("1234", "Joseph", new BigDecimal("400"));
        cx.depositar(new BigDecimal("100"));
        cx.depositar(new BigDecimal("250"));
        cx.depositar(new BigDecimal("40"));

        cx.retirar(new BigDecimal("30"));
        cx.retirar(new BigDecimal("80"));

        cx.getMovimientos().stream()
                .filter(m -> m.tipo() == TipoMovimiento.DEPOSITO)
                .map(Movimiento::monto)
                .reduce(BigDecimal.ZERO, (acumulado, siguiente) -> acumulado.add(siguiente));
                //.forEach(monto -> System.out.println(monto));

        cx.getMovimientos().stream()
                .filter(m -> m.tipo() == TipoMovimiento.RETIRO)
                .map(Movimiento::monto)
                        .reduce(BigDecimal.ZERO, (acumulado, siguiente) -> acumulado.add(siguiente));
                //.forEach(monto -> System.out.println(monto));

        System.out.println("Saldo: " + cx.getSaldo());
        //System.out.println("Total depositado: " + totalDepositado(cx));
        System.out.println("Total depositad con Stream: " + totalDepositadoStream(cx));
    }

    static BigDecimal totalDepositado(Cuenta cuenta) {
        BigDecimal total = BigDecimal.ZERO;

        for (Movimiento m : cuenta.getMovimientos()) {
            if (m.tipo() == TipoMovimiento.DEPOSITO) {
                total = total.add(m.monto());
            }
        }

        return total;
    }

    static BigDecimal totalDepositadoStream(Cuenta cuenta) {
        BigDecimal total = cuenta.getMovimientos().stream()
                .filter(m -> m.tipo() == TipoMovimiento.DEPOSITO)
                .map(Movimiento::monto)
                .reduce(BigDecimal.ZERO, (acumulado, siguiente) -> acumulado.add(siguiente));

        return total;
    }
}
