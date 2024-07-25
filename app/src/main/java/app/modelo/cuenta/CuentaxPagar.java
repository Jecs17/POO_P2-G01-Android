
package app.modelo.cuenta;
import app.modelo.Banco.Banco;
import app.modelo.persona.Persona;
import java.time.LocalDate;

/**
 * <h3>Clase CuentaxPagar</h3>
 * <p>La clase CuentaxPagar extiende de CuentaFinanciera y representa una cuenta por pagar,
 * que puede ser a una persona o a un banco.</p>
 * @author Grupo1
 */
public class CuentaxPagar extends CuentaFinanciera{
    /**
     * El acreedor al que se le debe el pago.
     */
    private Persona acreedor;
    /**
     * La tasa de interés asociada a la cuenta por pagar.
     */
    private double interes;
    /**
     * El banco al que se le debe el pago, si aplica.
     */
    private Banco banco;
    /**
     * Código único de la cuenta por pagar, se incrementa con cada instancia.
     */
    public static int codigoCuentaxPagar = 1;
    
    /**
     * <h3>Constructor de la clase CuentaxPagar</h3>
     * <p>Inicializa una cuenta por pagar con detalles específicos cuando el acreedor es una persona.</p>
     * 
     * @param valor el valor de la deuda.
     * @param descripcion la descripción de la deuda.
     * @param fechaPrestamo la fecha en la que se hizo el préstamo.
     * @param cuota la cuota de pago.
     * @param fechaInicio la fecha de inicio del pago.
     * @param fechaFin la fecha final del pago.
     * @param interes la tasa de interés.
     * @param acreedor la persona a la que se debe el pago {@link Persona}.
     */
    public CuentaxPagar(double valor, String descripcion, LocalDate fechaPrestamo, double cuota, LocalDate fechaInicio, LocalDate fechaFin, double interes, Persona acreedor){
        super(codigoCuentaxPagar, valor, descripcion, fechaPrestamo, cuota, fechaInicio, fechaFin);
        codigoCuentaxPagar += 1;
        this.acreedor = acreedor;
        this.interes = interes;
    }
    
    /**
     * <h3>Constructor de la clase CuentaxPagar</h3>
     * <p>Inicializa una cuenta por pagar con detalles específicos cuando el acreedor es un banco.</p>
     * 
     * @param valor el valor de la deuda.
     * @param descripcion la descripción de la deuda.
     * @param fechaPrestamo la fecha en la que se hizo el préstamo.
     * @param cuota la cuota de pago.
     * @param fechaInicio la fecha de inicio del pago.
     * @param fechaFin la fecha final del pago.
     * @param interes la tasa de interés.
     * @param banco el banco al que se debe el pago {@link Banco}.
     */
    public CuentaxPagar(double valor, String descripcion, LocalDate fechaPrestamo, double cuota, LocalDate fechaInicio, LocalDate fechaFin, double interes, Banco banco) {
        super(codigoCuentaxPagar, valor, descripcion, fechaPrestamo, cuota, fechaInicio, fechaFin);
        codigoCuentaxPagar += 1;
        this.interes = interes;
        this.banco = banco;
    }
    
    /**
     * Obtiene el acreedor de la cuenta por pagar.
     * 
     * @return el acreedor {@link Persona}.
     */
    public Persona getAcreedor() {
        return acreedor;
    }
    
    /**
     * Obtiene el banco de la cuenta por pagar.
     * 
     * @return el banco {@link Banco}.
     */
    public Banco getBanco() {
        return banco;
    }
    
    /**
     * Obtiene la tasa de interés de la cuenta por pagar.
     * 
     * @return la tasa de interés.
     */
    public double getInteres() {
        return interes;
    }
    
    /**
     * Establece la tasa de interés de la cuenta por pagar.
     * 
     * @param interes la tasa de interés a establecer.
     */
    public void setInteres(double interes) {
        this.interes = interes;
    }
    
    /**
     * Devuelve una representación en cadena de la cuenta por pagar.
     * 
     * @return una cadena con detalles de la cuenta por pagar.
     */
    @Override
    public String toString() {
        if (acreedor != null) {
            return String.format("%-9d %-23s %-15.2f %-33s %-19s %-15.2f", codigo, acreedor.getNombre(), valor, descripcion, fechaPrestamo, cuota);
        } else{
            return String.format("%-9d %-23s %-15.2f %-33s %-19s %-15.2f", codigo, banco.getEntidadBancaria(), valor, descripcion, fechaPrestamo, cuota);
        }
    }
}
