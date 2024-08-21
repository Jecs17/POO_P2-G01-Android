
package modelo.movimiento;
import android.content.Context;
import android.util.Log;

import com.example.poo_p2_g01_android.ControladorCategoria.CategoryActivity;
import enums.TipoRepeticion;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

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
public abstract class Movimiento implements Serializable {

    /**
     * Nombre de archivo movimiento que guarda los ingresos y los gastos
     */
    private final static String nombreArchivo = "MOVIMIENTOs.ser";


    /**
     * Código tipo <code>int</code> y <code>static</code> único para identificar el movimiento. Se utilizará para sumarle a las clases: {@link Ingreso} y {@link Gasto}.
     */
    protected static int codigo = 1;
    
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
     * Genera un código único para cada movimiento.
     *
     * @return el código único generado.
     */
    protected int generarCodigoUnico() {
        return codigo++;
    }

    /**
     * Método abstracto que se va a sobreescribir en sus clases hijas para obtener codigo único.
     * @return el codigo unico de cada instancia de ingreso y gasto.
     */
    public abstract int getCodigoUnico(); 
    

    public static void actualizarCodigo(int codigoActualizado) {
        codigo = codigoActualizado;
    }

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
     * @param repeticion el nuevo tipo de repetición del movimiento de la clase .
     */
    public void setRepeticion(TipoRepeticion repeticion) {
        this.repeticion = repeticion;
    }


    /**
     * <p>Devuele una lista de objetos Movimientos</p>
     * @return lista de categorias
     */
    public static List<Movimiento> obtenerMovimientos(Context context) {
        List<Movimiento> listaMovimiento = new ArrayList<>();
        listaMovimiento.add(new Ingreso(CategoryActivity.buscarCategoriaPorNombre("Salario", context), 45.00, "Salario mensual", LocalDate.of(2024, Month.JULY, 10), LocalDate.of(2025, Month.MARCH, 10), TipoRepeticion.SIN_REPETICION ));
        listaMovimiento.add(new Ingreso(CategoryActivity.buscarCategoriaPorNombre("Alquiler", context), 100.00, "Alquiler hogar", LocalDate.of(2023, Month.APRIL, 10), LocalDate.of(2025, Month.APRIL, 10), TipoRepeticion.MES));
        listaMovimiento.add(new Gasto(CategoryActivity.buscarCategoriaPorNombre("Comida", context), 10.00, "Comida diaria", LocalDate.of(2024, Month.JULY, 11), null, TipoRepeticion.SIN_REPETICION ));
        listaMovimiento.add(new Gasto(CategoryActivity.buscarCategoriaPorNombre("Transporte", context), 2.00, "Transporte diario", LocalDate.of(2024, Month.JUNE, 13), null, TipoRepeticion.MES));
        return listaMovimiento;
    }

    /**
     * Devuelve una lista de movimientos, la cual se obtiene de un archivo serializado. Sino existe el archivo, se crea la lista.
     * @param directorio directorio en android donde se leerá el archivo
     * @return una lista de objetos movimientos
     * @throws Exception lanza una excepción si hay algun error inesperado
     */
    public static List<Movimiento> cargarMovimientos(File directorio) throws IOException, ClassNotFoundException {
        List<Movimiento> listaMovimiento = new ArrayList<>();
        File file = new File(directorio, nombreArchivo);
        if (file.exists()) {
            Log.d("Movimiento", "entra");
            try(ObjectInputStream os = new ObjectInputStream(new FileInputStream(file))) {
                listaMovimiento = (List<Movimiento>) os.readObject();
                Log.d("Movimiento", "cargar movimientos " + listaMovimiento.toString());
            }
        } else {
            Log.d("Movimiento", "no entra");
        }

        return listaMovimiento;
    }

    /**
     * Devuelve booleano si esta creado el archivo con sus datos, por lo contrario lo crea;
     * @param directorio directorio en android donde se guardará el archivo
     * @return true si se pudo crear el archivo o ya existe.
     */
    public static boolean crearDatosIniciales(File directorio, Context context)  {
        List<Movimiento> lstMovimiento = Movimiento.obtenerMovimientos(context);
        boolean guardado;
        File file = new File(directorio, nombreArchivo);

        if (!file.exists()) {
            Movimiento.escribirArchivo(directorio, lstMovimiento);
            Log.d("Movimiento", "archivo creado por primera vez");
            guardado = true;

        } else {
            guardado = true;
        }
        if(guardado) {
            Log.d("Movimiento", "se creo el archivo correctamente");
        }
        return guardado;
    }

    /**
     * Escribe una lista de movimientos en un archivo.
     *
     * @param file El directorio donde se almacenará el archivo.
     * @param lstMovimiento La lista de categorías a escribir en el archivo.
     */
    public static void escribirArchivo(File file, List<Movimiento> lstMovimiento) {
        File file1 = new File(file, nombreArchivo);
            try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(file1))) {
                os.writeObject(lstMovimiento);
                os.flush();
            } catch (IOException e) {
                Log.d("Movimiento", "Error al escribir el archivo");
            }

    }

    /**
     * Añade una movimiento a la lista y lo añade a el archivo correspondiente.
     *
     * @param directorio El directorio del archivo de movimiento.
     * @param movimiento el objeto movimiento a añadir.
     * @return
     */
    public static boolean agregarMovimiento(File directorio, Movimiento movimiento) {
        boolean guardado;
        try {
            List<Movimiento> listaMovimiento = Movimiento.cargarMovimientos(directorio);
            listaMovimiento.add(movimiento);
            escribirArchivo(directorio, listaMovimiento);
            guardado = true;
            Log.d("Movimiento añadir Datos", listaMovimiento.toString());
        } catch (Exception e) {
            Log.e("Movimiento", "Error al leer el archivo movimiento.ser" + e.getMessage());
            guardado = false;
        }
        return guardado;
    }

    /**
     * Se elimina un movimiento del archivo movimientos.
     * @param directorio donde esta ubicado el archivo.
     * @param movimiento a eliminar.
     * @return boolean si se eliminó true, por lo contrario false.
     */
    public static boolean eliminarMovimiento(File directorio, Movimiento movimiento) {
        boolean eliminado;
        try {
            List<Movimiento> listaMovimiento = Movimiento.cargarMovimientos(directorio);
            listaMovimiento.remove(movimiento);
            escribirArchivo(directorio, listaMovimiento);
            eliminado = true;
            Log.e("Movimiento", "Eliminado correctamente" + movimiento);
         } catch (Exception e) {
            eliminado = false;
            Log.e("Movimiento", "Error al eliminar el movimiento"+ movimiento + e.getMessage());
        }
        return eliminado;
    }

    /**
     * Actualiza una movimiento a la lista y actualiza el archivo correspondiente.
     *
     * @param directorio El directorio del archivo de movimiento.
     * @param movimiento el objeto movimiento a añadir.
     * @return
     */
    public static boolean actualizarMovimiento(File directorio, Movimiento movimiento) {
        boolean guardado;
        try {
            List<Movimiento> listaMovimiento = Movimiento.cargarMovimientos(directorio);
            int posicion = listaMovimiento.indexOf(movimiento);
            listaMovimiento.set(posicion, movimiento);
            escribirArchivo(directorio, listaMovimiento);
            guardado = true;
            Log.d("Movimiento añadir Datos", listaMovimiento.toString());
        } catch (Exception e) {
            Log.e("Movimiento", "Error al leer el archivo movimiento.ser" + e.getMessage());
            guardado = false;
        }
        return guardado;
    }
}



