package com.minibanco.dominio;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Cuenta implements Auditable {
    private final String id;
    private final String titular;
    private BigDecimal saldo;
    private final List<Movimiento> movimientos = new ArrayList<>();

    protected Cuenta(String id, String titular, BigDecimal saldo) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("El id no puede ser nulo ni vacio");
        }

        if (titular == null || titular.isBlank()) {
            throw new IllegalArgumentException("El titular no puede ser nulo ni vacio");
        }

        if (saldo == null || saldo.signum() < 0) {
            throw new IllegalArgumentException("El saldo no puede ser nulo ni negativo");
        }

        this.id = id;
        this.titular = titular;
        this.saldo = saldo.setScale(2, RoundingMode.HALF_EVEN);
    }

    protected abstract BigDecimal comision();

    public void depositar(BigDecimal monto) {
        validarMonto(monto);
        this.saldo = this.saldo.add(monto);
        movimientos.add(new Movimiento(TipoMovimiento.DEPOSITO, monto));
    }

    public void retirar(BigDecimal monto) {
        validarMonto(monto);

        BigDecimal total = monto.add(comision());

        if (this.saldo.compareTo(total) < 0) {
            throw new SaldoInsuficienteException("Saldo insuficiente en la cuenta " + getId());
        }

        this.saldo = this.saldo.subtract(total);
        movimientos.add(new Movimiento(TipoMovimiento.RETIRO, monto));
    }

    private static void validarMonto(BigDecimal monto) {
        if (monto == null || monto.signum() <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a cero");
        }
    }

    public String getId() {
        return id;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public String getTitular() {
        return titular;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()+"[" + getId() + "] saldo=" + getSaldo() + " titular:" + getTitular();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cuenta cuenta = (Cuenta) o;
        return Objects.equals(id, cuenta.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public List<Movimiento> getMovimientos() {
        return List.copyOf(movimientos);
    }

    @Override
    public String resumenAuditoria() {
        return getId() + "|" + getTitular() + "|" + movimientos.size() + " movimientos";
    }
}
