package com.minibanco.dominio;

import java.math.BigDecimal;

public class CuentaAhorros extends Cuenta {
    public CuentaAhorros(String id, String titular, BigDecimal saldo) {
        super(id, titular, saldo);
    }

    @Override
    protected BigDecimal comision() {
        return BigDecimal.ZERO;
    }
}
