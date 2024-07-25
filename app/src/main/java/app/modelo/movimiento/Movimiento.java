
package app.modelo.movimiento;
import app.enums.TipoRepeticion;
import java.time.LocalDate;
/**
 * <p>La clase Movimiento representa una transacción financiera con detalles
 * específicos como categoría, valor neto, descripción, fechas de inicio y fin,
 * y tipo de repetición. Esta clase proporciona métodos para obtener y establecer
 * estos valores, así como para manejar el tipo de repetición del movimiento.</p>
 * <p>
 * Cada instancia de Movimiento incluye:
 * <ul>
 *   <li>Una categoría: {@link Movimiento#categoria}</li>
 *   <li>Un valor neto: {@link Movimiento#valorNeto} <code>double</code></li>
 *   <li>Una descripción: {@link Movimiento#descripcion}.</li>
 *   <li>Fechas de inicio y fin: {@link Movimiento#fechaInicio} y {@link Movimiento#fechaFin}</li>
 *   <li>Un tipo de repetición: {@link Movimiento#repeticion}</li>
 * </ul>
 * </p>
 * 
 * @author Grupo1
 */
public abstract class Movimiento {
    /**
     * Código tipo <code>int</code> y <code>static</code> único para identificar el movimiento. Se utilizará para sumarle a las clases: {@link Ingreso} y {@link Gasto}.
     */
    protected static int codigo = 0;
    
    /**
     * Categoría del movimiento de la clase {@link Categoria}.
     */
    protected Categoria categoria;
    
    /**
     * Valor neto del movimiento de tipo <code>double</code>.
     */
    protected double valorNeto;
    
    /**
     * Descripción del movimiento de la clase {@link String}.
     */
    protected String descripcion;
    
    /**
     * Fecha de finalización del movimiento de La clase {@link LocalDate}.
     */
    protected LocalDate fechaFin;
    
    /**
     * Fecha de inicio del movimient de La clase {@link LocalDate}.
     */
    protected LocalDate fechaInicio;
    
    /**
     * Tipo de repetición del movimiento de la clase <code>enum</code>{@link TipoRepeticion}.
     */
    protected TipoRepeticion repeticion;
    
    /**
     * <h3>Constructor de la clase Movimiento.</h3>
     * <p>Inicializa todas las propiedades de movimiento, menos el codigo, al crear un objeto tipo movimiento.</p>
     * 
     * @param categoria la categoría del movimiento {@link Categoria}.
     * @param valorNeto el valor neto del movimiento de tipo <code>double</code>.
     * @param descripcion una breve descripción del movimiento de la clase {@link String}.
     * @param fechaInicio la fecha de inicio del movimiento de La clase {@link LocalDate}.
     * @param fechaFin la fecha de finalización del movimiento de La clase {@link LocalDate}.
     * @param repeticion el tipo de repetición del movimiento {@link TipoRepeticion}.
     */
    public Movimiento(Categoria categoria, double valorNeto, String descripcion, LocalDate fechaInicio, LocalDate fechaFin, TipoRepeticion repeticion){
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.fechaFin = fechaFin;
        this.fechaInicio = fechaInicio;
        this.repeticion = repeticion;
        this.valorNeto = valorNeto;
    }
    /**
     * Método abstracto que se va a sobreescribir en sus clases hijas para obtener codigo único.
     * @return el codigo unico de cada instancia de ingreso y gasto.
     */
    public abstract int getCodigoUnico(); 
    
    
    /**
     * Método abstracto que se va a sobreescribir en sus clases hijas para obtener la fecha de finalización del movimiento.
     * 
     * @return la fecha de finalización del movimiento de la clase {@link LocalDate}.
     */
    public abstract String getFechaFin();
    
    /**
     * Retorna la categoría del movimiento.
     * 
     * @return la categoría del movimiento de la clase {@link Categoria}.
     */ 
    public Categoria getCategoria() {
        return this.categoria;
    }
    
     /**
     * Establece la categoría del movimiento.
     * 
     * @param categoria la nueva categoría del movimiento de la clase {@link Categoria}.
     */
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    
    public LocalDate fechaFin(){
        return this.fechaFin;
    }
    /**
     * Retorna el valor neto del movimiento.
     * 
     * @return el valor neto del movimiento de tipo <code>double</code>.
     */
    public double getValorNeto() {
        return this.valorNeto;
    }
    
    /**
     * Establece el valor neto del movimiento.
     * 
     * @param valorNeto el nuevo valor neto del movimiento de tipo <code>double</code>.
     */
    public void setValorNeto(double valorNeto) {
        this.valorNeto = valorNeto;
    }
    
    /**
     * Retorna la descripción del movimiento.
     * 
     * @return la descripción del movimiento de la clase {@link String}.
     */
    public String getDescripcion() {
        return this.descripcion;
    }
    
    /**
     * Establece la descripción del movimiento.
     * 
     * @param descripcion la nueva descripción del movimiento de la clase {@link String}.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    /**
     * Establece la fecha de finalización del movimiento.
     * 
     * @param fechaFin la nueva fecha de finalización del movimiento de la clase {@link LocalDate}.
     */
    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }
    
    /**
     * Retorna la fecha de inicio del movimiento.
     * 
     * @return la fecha de inicio del movimiento de la clase {@link LocalDate}.
     */
    public LocalDate getFechaInicio() {
        return this.fechaInicio;
    }
    
    /**
     * Establece la fecha de inicio del movimiento.
     * 
     * @param fechaInicio la nueva fecha de inicio del movimiento de la clase {@link LocalDate}.
     */
    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    
    /**
     * <p>Retorna el tipo de repetición del movimiento como una cadena,
     * dependiendo que tipo de repeticion sea.</p>
     * 
     * @return el tipo de repetición del movimiento de la clase {@link String}.
     */
    public String getRepeticion(){
        if(this.repeticion == TipoRepeticion.SIN_REPETICION){
            return "Sin repeticion";
        }else {
            return "Mes";
        }
    }
    /**
     * Retorna el tipo de repeticion de cada movimiento.
     * @return TipoRepeticion de cada movimiento.
     */
    public TipoRepeticion getTipoRepeticion(){
        return this.repeticion;
    }
    
    /**
     * Establece el tipo de repetición del movimiento 
     * 
     * @param repeticion el nuevo tipo de repetición del movimiento de la clase {@link app.enums.TipoCategoria}.
     */
    public void setRepeticion(TipoRepeticion repeticion) {
        this.repeticion = repeticion;
    }
    
    
}



