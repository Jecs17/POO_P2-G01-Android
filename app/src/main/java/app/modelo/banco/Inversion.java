package app.modelo.banco;

import java.time.LocalDate;

/**
 * Representa una inversión.
 * Incluye detalles de la cuenta bancaria asociada, fechas de apertura y cierre, cantidad invertida y el interés mensual.
 * 
 * @autor Grupo1
 */
public class Inversion {

    /**
     * El código único de la inversión.
     */
    private static int codigo = 0;

    /**
     * La cuenta bancaria asociada a la inversión.
     */
    private CuentaBancaria cuentaBancaria;

    /**
     * La fecha de apertura de la inversión.
     */
    private LocalDate fechaApertura;

    /**
     * La cantidad invertida.
     */
    private double cantidad;

    /**
     * El interés mensual de la inversión.
     */
    private double interesMensual;

    /**
     * La fecha de cierre de la inversión.
     */
    private LocalDate fechaCierre;

    /**
     * Constructor de la clase Inversion.
     *
     * @param cuentaBancaria La cuenta bancaria asociada a la inversión.
     * @param fechaApertura La fecha de apertura de la inversión.
     * @param cantidad La cantidad invertida.
     * @param interesMensual El interés mensual de la inversión.
     * @param fechaCierre La fecha de cierre de la inversión.
     */
    public Inversion(CuentaBancaria cuentaBancaria, LocalDate fechaApertura, double cantidad, double interesMensual, LocalDate fechaCierre) {
        this.cuentaBancaria = cuentaBancaria;
        this.fechaApertura = fechaApertura;
        this.cantidad = cantidad;
        this.interesMensual = interesMensual;
        this.fechaCierre = fechaCierre;
        codigo += 1;
    }

    /**
     * Obtiene el código de la inversión.
     *
     * @return El código de la inversión.
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Obtiene la cuenta bancaria asociada a la inversión.
     *
     * @return La cuenta bancaria asociada a la inversión.
     */
    public CuentaBancaria getCuentaBancaria() {
        return cuentaBancaria;
    }

    /**
     * Obtiene la fecha de apertura de la inversión.
     *
     * @return La fecha de apertura de la inversión.
     */
    public LocalDate getFechaApertura() {
        return fechaApertura;
    }

    /**
     * Establece la fecha de apertura de la inversión.
     *
     * @param fechaApertura La fecha de apertura de la inversión.
     */
    public void setFechaApertura(LocalDate fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    /**
     * Obtiene la cantidad invertida.
     *
     * @return La cantidad invertida.
     */
    public double getCantidad() {
        return cantidad;
    }

    /**
     * Establece la cantidad invertida.
     *
     * @param cantidad La cantidad invertida.
     */
    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Obtiene el interés mensual de la inversión.
     *
     * @return El interés mensual de la inversión.
     */
    public double getInteresMensual() {
        return interesMensual;
    }

    /**
     * Establece el interés mensual de la inversión.
     *
     * @param interesMensual El interés mensual de la inversión.
     */
    public void setInteresMensual(double interesMensual) {
        this.interesMensual = interesMensual;
    }

    /**
     * Obtiene la fecha de cierre de la inversión.
     *
     * @return La fecha de cierre de la inversión.
     */
    public LocalDate getFechaCierre() {
        return fechaCierre;
    }

    /**
     * Establece la fecha de cierre de la inversión.
     *
     * @param fechaCierre La fecha de cierre de la inversión.
     */
    public void setFechaCierre(LocalDate fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    /**
     * Devuelve una representación en cadena de la inversión.
     *
     * @return Una cadena que representa la inversión.
     */
    @Override
    public String toString() {
        return String.format("%-13s %-23s %-23s %-16s %-13s", codigo, cuentaBancaria.getEntidadBancaria().getEntidadBancaria(), fechaApertura, cantidad, interesMensual, fechaCierre);
    }
}