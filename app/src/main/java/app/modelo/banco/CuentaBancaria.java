package app.modelo.banco;

import app.enums.TipoCuenta;
import java.time.LocalDate;

/**
 * Representa una cuenta bancaria.
 * Incluye detalles de la entidad bancaria, tipo de cuenta, número, saldo y fechas de apertura y cierre.
 * 
 * @author Grupo1
 */
public class CuentaBancaria {
    /**
     * El código único de la cuenta bancaria.
     */
    private static int codigo = 0;
    
    /**
     * La entidad bancaria a la que pertenece la cuenta.
     */
    private Banco entidadBancaria;
    
    /**
     * El tipo de cuenta (ahorro o corriente).
     */
    private TipoCuenta tipo;
    
    /**
     * El número de la cuenta bancaria.
     */
    private String numero;
    
    /**
     * El saldo de la cuenta bancaria.
     */
    private double saldo;
    
    /**
     * La fecha de apertura de la cuenta bancaria.
     */
    private LocalDate fechaApertura;
    
    /**
     * La fecha de cierre de la cuenta bancaria.
     */
    private LocalDate fechaCierre;
    
    /**
     * El valor de interés asociado a la cuenta bancaria.
     */
    private double valorInteres;

    /**
     * Constructor de la clase CuentaBancaria.
     *
     * @param entidadBancaria La entidad bancaria a la que pertenece la cuenta.
     * @param tipo El tipo de cuenta (ahorro o corriente).
     * @param numero El número de la cuenta.
     * @param saldo El saldo inicial de la cuenta.
     * @param fechaApertura La fecha de apertura de la cuenta.
     * @param fechaCierre La fecha de cierre de la cuenta.
     * @param valorInteres El valor de interés asociado a la cuenta.
     */
    public CuentaBancaria(Banco entidadBancaria, TipoCuenta tipo, String numero, double saldo, LocalDate fechaApertura, LocalDate fechaCierre, double valorInteres) {
        this.entidadBancaria = entidadBancaria;
        this.tipo = tipo;
        this.numero = numero;
        this.saldo = saldo;
        this.fechaApertura = fechaApertura;
        this.fechaCierre = fechaCierre;
        this.valorInteres = valorInteres;
        codigo += 1;
    }

    /**
     * Obtiene el código de la cuenta bancaria.
     *
     * @return El código de la cuenta.
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Obtiene la entidad bancaria asociada a la cuenta.
     *
     * @return La entidad bancaria.
     */
    public Banco getEntidadBancaria() {
        return entidadBancaria;
    }

    /**
     * Obtiene el tipo de cuenta como una cadena.
     *
     * @return El tipo de cuenta ("Ahorros" o "Corriente").
     */
    public String getTipo() {
        if(this.tipo == TipoCuenta.AHORRO){
            return "Ahorros";
        } else {
            return "Corriente";
        }
    }

    /**
     * Establece el tipo de cuenta.
     *
     * @param tipo El tipo de cuenta (ahorro o corriente).
     */
    public void setTipo(TipoCuenta tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene el número de la cuenta bancaria.
     *
     * @return El número de la cuenta.
     */
    public String getNumero() {
        return numero;
    }

    /**
     * Establece el número de la cuenta bancaria.
     *
     * @param numero El número de la cuenta.
     */
    public void setNumero(String numero) {
        this.numero = numero;
    }

    /**
     * Obtiene el saldo de la cuenta bancaria.
     *
     * @return El saldo de la cuenta.
     */
    public double getSaldo() {
        return saldo;
    }

    /**
     * Establece el saldo de la cuenta bancaria.
     *
     * @param saldo El saldo de la cuenta.
     */
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    /**
     * Obtiene la fecha de apertura de la cuenta bancaria.
     *
     * @return La fecha de apertura de la cuenta.
     */
    public LocalDate getFechaApertura() {
        return fechaApertura;
    }

    /**
     * Establece la fecha de apertura de la cuenta bancaria.
     *
     * @param fechaApertura La fecha de apertura de la cuenta.
     */
    public void setFechaApertura(LocalDate fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    /**
     * Obtiene la fecha de cierre de la cuenta bancaria.
     *
     * @return La fecha de cierre de la cuenta.
     */
    public LocalDate getFechaCierre() {
        return fechaCierre;
    }

    /**
     * Establece la fecha de cierre de la cuenta bancaria.
     *
     * @param fechaCierre La fecha de cierre de la cuenta.
     */
    public void setFechaCierre(LocalDate fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    /**
     * Obtiene el valor de interés asociado a la cuenta bancaria.
     *
     * @return El valor de interés.
     */
    public double getValorInteres() {
        return valorInteres;
    }

    /**
     * Establece el valor de interés asociado a la cuenta bancaria.
     *
     * @param valorInteres El valor de interés.
     */
    public void setValorInteres(double valorInteres) {
        this.valorInteres = valorInteres;
    }

    /**
     * Devuelve una representación en cadena de la cuenta bancaria.
     *
     * @return Una cadena que representa la cuenta bancaria.
     */
    @Override
    public String toString() {
        return String.format("%-13s %-23s %-10s %-16s %-13s", codigo, entidadBancaria.getEntidadBancaria(), tipo, numero, saldo);
    }
}
