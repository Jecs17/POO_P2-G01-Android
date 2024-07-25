
package app.modelo.movimiento;

import app.enums.TipoRepeticion;
import java.time.LocalDate;

/**
 * <h3>Clase Ingreso</h3>
 * <p>La clase Ingreso es una subclase de Movimiento que representa un ingreso financiero
 * hereda atributos y métodos de la clase Movimiento, añadiendo un código único para
 * cada instancia de Ingreso y métodos adicionales para obtener información específica
 * sobre el ingreso.<br>
 * <br>Cada instancia tiene un codigo único {@link Ingreso#codigoUnicoIngreso} <code>int</code></p>
 * 
 * @author Grupo1
 */
public class Ingreso extends Movimiento{
   /**
    * Código tipo <code>int</code> único asignado a cada instancia de Ingreso, que se va utilizar para asignarle un código único a cada objeto Ingreso..
    */
   private final int codigoUnicoIngreso;
   
    /**
     * <h3>Constructor de la clase Ingreso</h3> 
     * <p>Inicializa una nueva instancia de Ingreso con
     * los valores especificados para categoría, valor neto, descripción, fechas de
     * inicio y fin, y tipo de repetición.</p>
     * 
     * @param categoria la categoría de ingreso {@link Categoria}.
     * @param valorNeto el valor neto de ingreso de tipo <code>double</code>.
     * @param descripcion una breve descripción de ingreso de la clase {@link String}.
     * @param fechaInicio la fecha de inicio de ingreso de La clase {@link LocalDate}.
     * @param fechaFin la fecha de finalización de ingreso de La clase {@link LocalDate}.
     * @param repeticion el tipo de repetición de ingreso {@link TipoRepeticion}.
     */
    public Ingreso(Categoria categoria, double valorNeto, String descripcion, LocalDate fechaInicio, LocalDate fechaFin, TipoRepeticion repeticion){
      super(categoria, valorNeto, descripcion, fechaInicio, fechaFin, repeticion);
      //se suma 1 de la variable heredada de movimiento la cual es estática, y realiza que cada Ingreso tenga código único.
      this.codigoUnicoIngreso = codigo++;
    }
    
    /**
     * Obtiene el código único asignado a este ingreso.
     * 
     * @return el código único del ingreso de tipo <code>int</code>.
     */
   @Override
    public int getCodigoUnico(){
        return this.codigoUnicoIngreso;
    }
    
    /**
     * <p>Obtiene la representación en cadena de la fecha de finalización del ingreso.
     * Si la fecha de finalización es nula, devuelve "No definida".</p>
     * 
     * @return una cadena que representa la fecha de finalización del ingreso de la clase {@link String}. 
     */
   @Override
    public String getFechaFin() {
        if(fechaFin == null){
            return "No definida";
        }
        return this.fechaFin.toString();
    }
    
    /**
     * Devuelve una representación en forma de cadena de la instancia de Ingreso.
     * 
     * @return una cadena formateada con los detalles del ingreso.
     */
    @Override
    public String toString(){
        return String.format("%-13s %-16s %-16s %-13.2f %-23s %-16s %-16s",this.getCodigoUnico(), this.getFechaInicio(), this.getCategoria().getNombre(),this.getValorNeto(), this.getDescripcion(), this.getFechaFin(), this.getRepeticion());
    }
   
}
