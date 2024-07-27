
package modelo.movimiento;
import enums.TipoCategoria;

/**
 * <h3>Clase Categoria</h3>
 * <p>La clase Categoria representa una categoría para clasificar movimientos financieros
 * Cada instancia de Categoria tiene un nombre y un tipo de categoría asociado.</p>
 * 
 * <p>La clase proporciona métodos para acceder y modificar el nombre y el tipo de categoría,
 * así como métodos para comparar y obtener representaciones en cadena de las instancias.</p>
 * 
 * <p>
 * Cada instancia tiene:
 * <ul>
 *  <li>Nombre: {@link Categoria#nombre}</li>
 *  <li>Tipo de Categoria : {@link Categoria#tipoCategoria}</li>
 * </ul>
 * @author Grupo1
 */
public class Categoria {
    /**
     * El nombre de la categoría.
     */
    private String nombre;
    
    /**
     * El tipo de categoría, representado por un valor de la enumeración {@link TipoCategoria}.
     */
    private TipoCategoria tipoCategoria;
    
    /**
     * <h3>Constructor de la clase Categoria</h3>
     * <h3>Constructor de la clase Categoria.</h3>
     * <p>Inicializa una nueva instancia de Categoria
     * con el nombre y tipo de categoría especificados.</p>
     * 
     * @param nombre el nombre de la categoría de la clase {@link String}.
     * @param tipoCategoria el tipo de categoría, representado por un valor de {@link TipoCategoria}.
     */
    public Categoria(String nombre, TipoCategoria tipoCategoria){
        this.nombre = nombre;
        this.tipoCategoria = tipoCategoria;
    }
    
    /**
     * Obtiene el nombre de la categoría.
     * 
     * @return el nombre de la categoría de la clase {@link String}.
     */
    public String getNombre() {
        return this.nombre;
    }
    
    /**
     * Establece el nombre de la categoría.
     * 
     * @param nombre el nuevo nombre de la categoría de la clase {@link String}.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    /**
     * Obtiene el tipo de categoría.
     * 
     * @return el tipo de categoría, representado por un valor de {@link TipoCategoria}.
     */
    public TipoCategoria getTipoCategoria() {
        return this.tipoCategoria;
    }
    
    /**
     * Establece el tipo de categoría.
     * 
     * @param tipoCategoria el nuevo tipo de categoría, representado por un valor de {@link TipoCategoria}
     */
    public void setTipoCategoria(TipoCategoria tipoCategoria) {
        this.tipoCategoria = tipoCategoria;
    }
       
    /**
     * <p>Compara si esta categoría es igual a otro objeto.
     * Dos categorías son iguales si tienen el mismo nombre y el mismo tipo de categoría.</p>
     * 
     * @param o el objeto a comparar
     * @return true si son iguales, false en caso contrario
     */
    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(o == null || getClass() != o.getClass()){
            return false;
        }
        Categoria otro = (Categoria) o;
        return otro.getNombre().equalsIgnoreCase(this.getNombre()) && otro.getTipoCategoria() == this.getTipoCategoria();
    }  
}
