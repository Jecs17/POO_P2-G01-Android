
package modelo.movimiento;

import enums.TipoRepeticion;
import java.time.LocalDate;

/**
 * <h3>Clase Gasto</h3>
 * <p>La clase Gasto es una subclase de Movimiento que representa un gasto financiero
 * hereda atributos y métodos de la clase Movimiento, añadiendo un código único para
 * cada instancia de Gasto y métodos adicionales para obtener información específica
 * sobre el gasto.<br>
 * <br>Cada instancia tiene un codigo único {@link Gasto#codigoUnicoGasto} <code>int</code></p>
 * 
 * @author Grupo1
 */
public class Gasto extends Movimiento{
    /**
    * Código tipo <code>int</code> único asignado a cada instancia de Gasto, que se va utilizar para asignarle un código único a cada objeto Gasto..
    */
    private final int codigoUnicoGasto;
    
    /**
     * <h3>Constructor de la clase Gasto</h3> 
     * <p>Inicializa una nueva instancia de Gasto con
     * los valores especificados para categoría, valor neto, descripción, fechas de
     * inicio y fin, y tipo de repetición.</p>
     * 
     * @param categoria la categoría de gasto {@link Categoria}.
     * @param valorNeto el valor neto de gasto de tipo <code>double</code>.
     * @param descripcion una breve descripción de gasto de la clase {@link String}.
     * @param fechaInicio la fecha de inicio de gasto de La clase {@link LocalDate}.
     * @param fechaFin la fecha de finalización de gasto de La clase {@link LocalDate}.
     * @param repeticion el tipo de repetición de gasto {@link TipoRepeticion}.
     */
    public Gasto(Categoria categoria, double valorNeto, String descripcion, LocalDate fechaInicio, LocalDate fechaFin, TipoRepeticion repeticion){
      super(categoria, valorNeto, descripcion, fechaInicio, fechaFin, repeticion);
      //se suma 1 de la variable heredada de movimiento la cual es estática, y realiza que cada Gasto tenga código único.
      this.codigoUnicoGasto = codigo++;
    }
    
    /**
     * Obtiene el código único asignado a este gasto.
     * 
     * @return el código único del gasto de tipo <code>int</code>.
     */
    @Override
    public int getCodigoUnico(){ 
        return this.codigoUnicoGasto;
    }
    
    /**
     * <p>Obtiene la representación en cadena de la fecha de finalización del gasto.
     * Si la fecha de finalización es nula, devuelve "No definida".</p>
     * 
     * @return una cadena que representa la fecha de finalización del gasto de la clase {@link String}. 
     */
    @Override
    public String getFechaFin() {
        if(fechaFin == null){
            return "No definida";
        }
        return this.fechaFin.toString();
    }
    
    /**
     * Devuelve una representación en forma de cadena de la instancia de Gasto.
     * 
     * @return una cadena formateada con los detalles del gasto.
     */
    @Override
    public String toString(){
        return String.format("%-13s %-16s %-16s %-13.2f %-23s %-16s %-16s",   this.getCodigoUnico(), this.getFechaInicio(), this.getCategoria().getNombre(),this.getValorNeto(), this.getDescripcion(), this.getFechaFin(), this.getRepeticion());
    }
}
