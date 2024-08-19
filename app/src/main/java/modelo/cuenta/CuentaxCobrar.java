
package modelo.cuenta;
import modelo.persona.Persona;

import java.io.Serializable;
import java.time.LocalDate;
/**
 * <h3>Clase CuentaxCobrar</h3>
 * <p>La clase CuentaxCobrar extiende de CuentaFinanciera y representa una cuenta por cobrar,
 * donde se define el deudor y los detalles del préstamo.</p>
 * 
 * @author Grupo1
 */
public class CuentaxCobrar extends CuentaFinanciera implements Serializable {
    /**
     * La persona que debe el pago.
     */
    private Persona deudor;
    
    /**
     * Código único de la cuenta por cobrar, se incrementa con cada instancia.
     */
    public static int codigoCuentaxCobrar = 1;
    
    /**
     * <h3>Constructor de la clase CuentaxCobrar</h3>
     * <p>Inicializa una cuenta por cobrar con detalles específicos.</p>
     * 
     * @param valor el valor de la deuda.
     * @param descripcion la descripción de la deuda.
     * @param fechaPrestamo la fecha en la que se hizo el préstamo.
     * @param cuota la cuota de pago.
     * @param fechaInicio la fecha de inicio del pago.
     * @param fechaFin la fecha final del pago.
     * @param deudor la persona que debe el pago {@link Persona}.
     */
    public CuentaxCobrar(double valor, String descripcion, LocalDate fechaPrestamo, double cuota, LocalDate fechaInicio, LocalDate fechaFin, Persona deudor){
        super(codigoCuentaxCobrar, valor, descripcion, fechaPrestamo, cuota, fechaInicio, fechaFin);
        codigoCuentaxCobrar += 1;
        this.deudor = deudor;
    }
    
    /**
     * Obtiene el deudor de la cuenta por cobrar.
     * 
     * @return el deudor {@link Persona}.
     */
    public Persona getDeudor() {
        return this.deudor;
    }
    
    /**
     * Devuelve una representación en cadena de la cuenta por cobrar.
     * 
     * @return una cadena con detalles de la cuenta por cobrar.
     */
    @Override
    public String toString() {
        return String.format("%-9d %-20s %-15.2f %-25s %-19s %-15.2f", codigo, deudor.getNombre(), valor, descripcion, fechaPrestamo, cuota);
    }
}
