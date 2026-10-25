package com.minibanco.dominio;

import java.math.BigDecimal;

public class CuentaCorriente extends Cuenta {
    public CuentaCorriente(String id, String titular, BigDecimal saldo) {
        super(id, titular, saldo);
    }

    @Override
    protected BigDecimal comision() {
        return new BigDecimal("2.00");
    }
}
