
package modelo.persona;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

/**
 * <p>Esta clase encapsula los datos básicos de una persona como su nombre, cédula, teléfono,
 * correo electrónico. Además, gestiona la fecha de registro de la persona.</p>
 * <p>Esta clase proporciona métodos para acceder y modificar cada uno de los atributos.</p>
 * <p>
 * En la creación de cada persona se instancian los siguientes propiedades:
 * <ul>
 *  <li>Cédula: {@link Persona#cedula}</li>
 *  <li>Nombre: {@link Persona#nombre}</li>
 *  <li>Teléfono: {@link Persona#telefono}</li>
 *  <li>Email: {@link Persona#email}</li>
 *  <li>FechaRegistro: {@link Persona#fechaRegistro}</li>
 * </ul>
 * </p>
 * @author Grupo1
 */
public class Persona implements Serializable {

    /**
     * Cédula de identidad de la persona.
     */
    private String cedula;
    
    /**
     * Nombre completo de la persona.
     */
    private String nombre;
    
    /**
     * Número de teléfono de la persona.
     */
    private String telefono;
    
    /**
     * Dirección de correo electrónico de la persona.
     */
    private String email;
    
    /**
     * Fecha en la que la persona fue registrada en el sistema.
     */
    private LocalDate fechaRegistro;

    /**
     * Nombre del Archivo serializado de las Personas
     */
    public static final String nomArchivo = "Personass.ser";
    
    /**
     * <h3>Constructor clase Persona(Oficial Crédito)</h3>
     * <p>Constructor para crear una nueva persona con nombre y teléfono
     * La fecha de registro se establece automáticamente como la fecha actual.</p>
     * 
     * @param nombre Nombre completo de la persona.
     * @param telefono Número de teléfono de la persona.
     */
    public Persona(String nombre, String telefono){
        this.cedula = " ";
        this.nombre = nombre;
        this.telefono = telefono;
    }
    
    /**
     * <h3>Constructor clase Persona</h3>
     * <p>Constructor para crear una nueva persona con cédula, nombre, teléfono y correo electrónico.
     * La fecha de registro se establece automáticamente como la fecha actual.</p>
     * 
     * @param cedula Número de cédula de la persona.
     * @param nombre Nombre completo de la persona.
     * @param telefono Número de teléfono de la persona.
     * @param email Correo electrónico de la persona.
     */
    public Persona(String cedula, String nombre, String telefono, String email){
        this.cedula = cedula;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.fechaRegistro = LocalDate.now();
    }
    
    /**
     * Retorna la cédula de la persona.
     * 
     * @return La cédula de la persona.
     */
    public String getCedula(){
        return this.cedula;
    }
    
    /**
     * Establece la cédula de la persona.
     * 
     * @param cedula Nueva cédula de la persona.
     */

    public void setCedula(String cedula){
        this.cedula = cedula;
    }
    
    /**
     * Retorna el nombre completo de la persona.
     * 
     * @return El nombre de la persona.
     */
    public String getNombre(){
        return this.nombre;
    }
    
    /**
     * Establece el nombre completo de la persona.
     * 
     * @param nombre Nuevo nombre de la persona.
     */
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    
    /**
     * Retorna el número de teléfono de la persona.
     * 
     * @return El número de teléfono de la persona.
     */
    public String getTelefono(){
        return "Tel: " + this.telefono;
    }
    
    /**
     * Establece el número de teléfono de la persona.
     * 
     * @param telefono Nuevo número de teléfono de la persona.
     */
    public void setTelefono(String telefono){
        this.telefono = telefono;
    }
    
    /**
     * Retorna el correo electrónico de la persona.
     * 
     * @return El correo electrónico de la persona..
     */
    public String getEmail(){
        return this.email;
    }
    
    /**
     * Establece el correo electrónico de la persona.
     * 
     * @param email Nuevo correo electrónico de la persona..
     */
    public void setEmail(String email){
        this.email = email;
    }
    
    /**
     * Retorna la fecha de registro de la persona.
     * 
     * @return La fecha de registro de la persona.
     */
    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }
    
    /**
     * Devuelve una representación en cadena de la información de la Persona.
     *
     * @return Cadena formateada con los detalles de la persona.
     */
    @Override
    public String toString() {
        return String.format("%-18s %-20s %-24s %-23s Telf: %-30s", fechaRegistro, cedula, nombre, email, telefono);
    }

    public static ArrayList<Persona> obtenerPersonasIniciales(){
        ArrayList<Persona> lista = new ArrayList<>();
        lista.add(new Persona("094232152", "Pedro Lopéz", "0994354621", "pedrol12@gmail.com"));
        lista.add(new Persona("045613445", "Luis Correa", "075389753", "luisco@gmail.com"));
        return lista;
    }

    public static boolean crearDatosIniciales(File directorio){
        ArrayList<Persona> lista = Persona.obtenerPersonasIniciales();
        boolean guardado = false;
        File f = new File(directorio, nomArchivo);

        if(! f.exists()){
            try (ObjectOutputStream os= new ObjectOutputStream(new FileOutputStream(f))){
                os.writeObject(lista);
                guardado = true;
            }catch (IOException e){
                Log.e("Persona", "crear Datos Iniciales: " + e.getMessage());
            }
        } else guardado = true;
        return guardado;
    }

    public static ArrayList<Persona> cargarPersonas(File directorio){
        ArrayList<Persona> lista = new ArrayList<>();
        File f = new File(directorio, nomArchivo);
        if(f.exists()){
            try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(f))){
                lista = (ArrayList<Persona>) is.readObject();
            }catch (Exception e){
                Log.e("Persona", "cargarPersonas: " + e.getMessage());
            }
        }
        return lista;
    }

    public static boolean guardarPersona(File directorio, Persona persona){
        boolean guardado = false;
        ArrayList<Persona> listaPersona = Persona.cargarPersonas(directorio);
        File f = new File(directorio, nomArchivo);
        if(f.exists()){
            try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(f))){
                listaPersona.add(persona);
                os.writeObject(listaPersona);
                guardado = true;
            }catch (Exception e){
                Log.e("Persona", "guardarPersona: " + e.getMessage());
            }
        }
        return guardado;
    }

    public static boolean eliminarPersona(File directorio, Persona persona){
        boolean guardado = false;
        ArrayList<Persona> listaPersona = Persona.cargarPersonas(directorio);
        File f = new File(directorio, nomArchivo);
        if(f.exists()){
            try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(f))){
                listaPersona.remove(persona);
                os.writeObject(listaPersona);
                guardado = true;
            }catch (Exception e){
                Log.e("Persona", "eliminarPersona: " + e.getMessage());
            }
        }
        return guardado;
    }
    
    /**
     * <p>Compara esta persona con otro objeto para determinar si son iguales.
     * La igualdad se basa en la comparación de las cédulas.</p>
     * 
     * @param obj El objeto a comparar con esta persona.
     * @return true si los objetos son iguales, false de lo contrario.
     */
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        
        final Persona other = (Persona) obj;
        return Objects.equals(this.cedula, other.cedula);
    }    
}
