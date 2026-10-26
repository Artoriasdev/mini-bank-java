package com.minibanco.dominio;

import java.math.BigDecimal;

public record Movimiento(TipoMovimiento tipo, BigDecimal monto) {
    public Movimiento {
        if (tipo == null) throw new IllegalArgumentException("El tipo es obligatorio");
        if (monto == null || monto.signum() <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a 0");
        }
    }
}
