
package app.modelo.cuenta;

import java.time.LocalDate;

/**
 * <h3>Clase CuentaFinanciera</h3>
 * <p>Representa una cuenta financiera con atributos comunes a cuentas por pagar y por cobrar.</p>
 * 
 * @autor Grupo1
 */
public class CuentaFinanciera {
    /**
     * Código único de la cuenta.
     */
    protected int codigo;
    /**
     * Valor de la cuenta.
     */
    protected double valor;
    /**
     * Descripción de la cuenta.
     */
    protected String descripcion;
    /**
     * Fecha en la que se realizó el préstamo.
     */
    protected LocalDate fechaPrestamo;
    /**
     * Cuota de pago de la cuenta.
     */
    protected double cuota;
    /**
     * Fecha de inicio del pago de la cuenta.
     */
    protected LocalDate fechaInicio;
    /**
     * Fecha de finalización del pago de la cuenta.
     */
    protected LocalDate fechaFin;
    
    /**
     * <h3>Constructor de la clase CuentaFinanciera</h3>
     * <p>Inicializa una cuenta financiera con los detalles específicos.</p>
     * 
     * @param codigo el código de la cuenta.
     * @param valor el valor de la cuenta.
     * @param descripcion la descripción de la cuenta.
     * @param fechaPrestamo la fecha en la que se realizó el préstamo.
     * @param cuota la cuota de pago.
     * @param fechaInicio la fecha de inicio del pago.
     * @param fechaFin la fecha de finalización del pago.
     */
    public CuentaFinanciera(int codigo, double valor, String descripcion, LocalDate fechaPrestamo, double cuota, LocalDate fechaInicio, LocalDate fechaFin) {
        this.codigo = codigo;
        this.valor = valor;
        this.descripcion = descripcion;
        this.fechaPrestamo = fechaPrestamo;
        this.cuota = cuota;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }
    
    /**
     * Obtiene el código de la cuenta.
     * 
     * @return el código de la cuenta.
     */
    public int getCodigo() {
        return codigo;
    }
    
    /**
     * Establece el código de la cuenta.
     * 
     * @param codigo el nuevo código de la cuenta.
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    
    /**
     * Obtiene el valor de la cuenta.
     * 
     * @return el valor de la cuenta.
     */
    public double getValor() {
        return valor;
    }
    
    /**
     * Establece el valor de la cuenta.
     * 
     * @param valor el nuevo valor de la cuenta.
     */
    public void setValor(double valor) {
        this.valor = valor;
    }
    
    /**
     * Obtiene la descripción de la cuenta.
     * 
     * @return la descripción de la cuenta.
     */
    public String getDescripcion() {
        return descripcion;
    }
    
    /**
     * Establece la descripción de la cuenta.
     * 
     * @param descripcion la nueva descripción de la cuenta.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    /**
     * Obtiene la fecha en la que se realizó el préstamo.
     * 
     * @return la fecha del préstamo.
     */
    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }
    
    /**
     * Establece la fecha en la que se realizó el préstamo.
     * 
     * @param fechaPrestamo la nueva fecha del préstamo.
     */
    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }
    
    /**
     * Obtiene la cuota de pago de la cuenta.
     * 
     * @return la cuota de la cuenta.
     */
    public double getCuota() {
        return cuota;
    }
    
    /**
     * Establece la cuota de pago de la cuenta.
     * 
     * @param cuota la nueva cuota de la cuenta.
     */
    public void setCuota(double cuota) {
        this.cuota = cuota;
    }
    
     /**
     * Obtiene la fecha de inicio del pago de la cuenta.
     * 
     * @return la fecha de inicio del pago.
     */
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }
    
    /**
     * Establece la fecha de inicio del pago de la cuenta.
     * 
     * @param fechaInicio la nueva fecha de inicio del pago.
     */
    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    
    /**
     * Obtiene la fecha de finalización del pago de la cuenta.
     * 
     * @return la fecha de finalización del pago.
     */
    public LocalDate getFechaFin() {
        return fechaFin;
    }
    
    /**
     * Establece la fecha de finalización del pago de la cuenta.
     * 
     * @param fechaFin la nueva fecha de finalización del pago.
     */
    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }
    
}
