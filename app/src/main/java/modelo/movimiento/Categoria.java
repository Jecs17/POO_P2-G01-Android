
package modelo.movimiento;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
public class Categoria implements Serializable {
    /**
     * El nombre de la categoría.
     */
    private String nombre;

    /**
     * El tipo de categoría, representado por un valor de la enumeración {@link TipoCategoria}.
     */
    private TipoCategoria tipoCategoria;

    /**
     * El nombre del archivo, donde se almacenará información
     */
    private static final String nomArchivo = "CATEGORIAs.ser";

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
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()){
            return false;
        }
        Categoria otro = (Categoria) o;
        return otro.getNombre().equalsIgnoreCase(this.getNombre());
    }

    /**
     * toString que devuelve solo el nombre del objeto
     * @return nombre del objeto en formato String
     */
    @Override
    public String toString() {
        return nombre;
    }


    /**
     * <p>Devuele una lista de objetos Categorias</p>
     * @return lista de categorias
     */
    public static List<Categoria> obtenerCategorias() {
        List<Categoria> lstCategoria = new ArrayList<>();
        lstCategoria.add(new Categoria("Salario", TipoCategoria.INGRESO));
        lstCategoria.add(new Categoria("Alquiler", TipoCategoria.INGRESO));
        lstCategoria.add(new Categoria("Comida", TipoCategoria.GASTO));
        lstCategoria.add(new Categoria("Transporte", TipoCategoria.GASTO));
        return lstCategoria;
    }

    /**
     * Devuelve una lista de categorias, la cual se obtiene de un archivo serializado. Sino existe el archivo, se crea la lista.
     * @param directorio directorio en android donde se leerá el archivo
     * @return una lista de objetos categorias
     * @throws Exception lanza una excepción si hay algun error inesperado
     */
    public static List<Categoria> cargarCategorias(File directorio) throws IOException, ClassNotFoundException{
        List<Categoria> listaCategoria = new ArrayList<>();
        File file = new File(directorio, nomArchivo);
        if (file.exists()) {
            try(ObjectInputStream os = new ObjectInputStream(new FileInputStream(file))) {
                listaCategoria = (List<Categoria>) os.readObject();
            }
        }
        return listaCategoria;
    }

    /**
     * Devuelve booleano si esta creado el archivo con sus datos, por lo contrario lo crea;
     * @param directorio directorio en android donde se guardará el archivo
     * @return true si se pudo crear el archivo o ya existe.
     */
    public static boolean crearDatosIniciales(File directorio) {
        List<Categoria> lstCategoria = Categoria.obtenerCategorias();
        boolean guardado = false;
        File file = new File(directorio, nomArchivo);

        if (!file.exists()) {
            Categoria.escribirArchivo(directorio, lstCategoria);
            Log.d("Categoriasssss", "archivo creado por primera vez");
            guardado = true;

        } else {
            guardado = true;
        }
        if(guardado) {
            Log.d("Categoria", "se creo el archivo correctamente");
        }
        return guardado;
    }

    /**
     * Escribe una lista de categorías en un archivo.
     *
     * @param file El directorio donde se almacenará el archivo.
     * @param lstCategoria La lista de categorías a escribir en el archivo.
     */
    public static void escribirArchivo(File file, List<Categoria> lstCategoria) {
        File file1 = new File(file, nomArchivo);
        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(file1))) {
            os.writeObject(lstCategoria);
            os.flush();
        } catch(IOException e) {
            Log.d("Categoria", "Error al escribir el archivo");
        }
    }

    /**
     * Añade una categoría a la lista y actualiza el archivo correspondiente.
     *
     * @param directorio El directorio del archivo de categorías.
     * @param categoria La categoría a añadir.
     */
    public static void actualizarDatos(File directorio, Categoria categoria) {
        try {
            List<Categoria> listaCategorias = Categoria.cargarCategorias(directorio);
            listaCategorias.add(categoria);
            escribirArchivo(directorio, listaCategorias);
            Log.d("Categoria añadir Datos", listaCategorias.toString());
        } catch (Exception e) {
            Log.e("Categoria", "Error al leer el archivo categorias.ser" + e.getMessage());
        }
    }

    /**
     * Elimina una categoría de la lista y actualiza el archivo correspondiente.
     *
     * @param directorio El directorio del archivo de categorías.
     * @param categoria La categoría a eliminar.
     */
    public static void eliminarDatos(File directorio, Categoria categoria)  {
        try {
            List<Categoria> lstCategoria = cargarCategorias(directorio);
            lstCategoria.remove(categoria);
            Categoria.escribirArchivo(directorio, lstCategoria);
            Log.d("Categoria eliminar Datos", lstCategoria.toString());
        } catch (Exception e) {
            Log.e("Categoria", "Error al leer el archivo categorias.ser" + e.getMessage());
        }
    }

}
