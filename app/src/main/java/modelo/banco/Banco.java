package modelo.banco;

import android.util.Log;

import modelo.persona.Persona;

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
 * <h3>Clase Banco</h3>
 * <p>
 * Representa a una entidad bancaria con datos básicos como: RUC, nombre de la
 * entidad, email, nombre y teléfono del ofical de crédito. Además, gestiona la
 * fecha de registro del banco.</p>
 * <p>
 * Cada instancia de banco incluye:
 * <ul>
 * <li>EntidadBancaria: {@link Banco#entidadBancaria}</li>
 * <li>RUC: {@link Banco#ruc}</li>
 * <li>Email: {@link Banco#email}</li>
 * <li>OficialCredito: {@link Banco#oficialCredito}</li>
 * </ul>
 * </p>
 *
 * @author Grupo1
 */
public class Banco implements Serializable {

    /**
     * Nombre de la entidad bancaria.
     */
    private String entidadBancaria;
    
    /**
     * Identificación única de cada banco, compuesto por 13 dígitos.
     */
    private String ruc;
    
    /**
     * Email del banco.
     */
    private String email;
    
    /**
     * Objeto de la clase Persona.
     */
    private Persona oficialCredito;
    
    /**
     * Fecha en la que el banco es registrado.
     */
    private LocalDate fechaRegistro;

    /**
     * Nombre del Archivo serializado de los Bancos.
     */
    public static final String nomArchivo = "Bancoss.ser";
    
    /**
     * Constructor de la clase Banco.
     *
     * @param entidadBancaria Nombre de la entidad bancaria.
     * @param ruc RUC de la entidad bancaria.
     * @param email Correo electrónico de contacto.
     * @param oficialCredito Persona encargada del crédito.
     */
    public Banco(String entidadBancaria, String ruc, String email, Persona oficialCredito) {
        this.entidadBancaria = entidadBancaria;
        this.ruc = ruc;
        this.email = email;
        this.oficialCredito = oficialCredito;
        this.fechaRegistro = LocalDate.now();
    }

    /**
     * Obtiene el nombre de la entidad bancaria.
     *
     * @return Nombre de la entidad bancaria.
     */
    public String getEntidadBancaria() {
        return entidadBancaria;
    }

    /**
     * Establece el nombre de la entidad bancaria.
     *
     * @param entidadBancaria Nuevo nombre de la entidad bancaria.
     */
    public void setEntidadBancaria(String entidadBancaria) {
        this.entidadBancaria = entidadBancaria;
    }

    /**
     * Obtiene el RUC de la entidad bancaria.
     *
     * @return RUC de la entidad bancaria.
     */
    public String getRuc() {
        return ruc;
    }

    /**
     * Establece el RUC de la entidad bancaria.
     *
     * @param ruc Nuevo RUC de la entidad bancaria.
     */
    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    /**
     * Obtiene el correo electrónico de contacto.
     *
     * @return Correo electrónico de contacto.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el correo electrónico de contacto.
     *
     * @param email Nuevo correo electrónico de contacto.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene la persona encargada del crédito.
     *
     * @return Persona encargada del crédito.
     */
    public Persona getOficialCredito() {
        return oficialCredito;
    }

    /**
     * Obtiene la fecha de registro del banco.
     *
     * @return Fecha de registro del banco.
     */
    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * Devuelve una representación en cadena de la información del banco.
     *
     * @return Cadena formateada con los detalles del banco.
     */
    @Override
    public String toString() {
        return String.format("Oficial: %s\n%s",oficialCredito.getNombre(), oficialCredito.getTelefono());
    }

    public static ArrayList<Banco> obtenerBancosInciales(){
        ArrayList<Banco> lista = new ArrayList<>();
        Persona oficial = new Persona("Mario Duarte", "0994312563");
        lista.add(new Banco("Banco del Pacífico", "2341654582001", "pacif@banco.com", oficial));
        return lista;
    }

    public static boolean crearDatosIniciales(File directorio){
        ArrayList<Banco> lista = Banco.obtenerBancosInciales();
        boolean guardado = false;
        File f = new File(directorio, nomArchivo);

        if(! f.exists()){
            try (ObjectOutputStream os= new ObjectOutputStream(new FileOutputStream(f))){
                os.writeObject(lista);
                guardado = true;
            }catch (IOException e){
                Log.e("Banco", "Crear Datos Iniciales: " + e.getMessage() );
            }
        } else guardado = true;
        return guardado;
    }

    public static ArrayList<Banco> cargarBanco(File directorio){
        ArrayList<Banco> lista = new ArrayList<>();
        File f = new File(directorio, nomArchivo);
        if(f.exists()){
            try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(f))){
                lista = (ArrayList<Banco>) is.readObject();
            }catch (Exception e){
                Log.e("Banco", "cargarBanco: " + e.getMessage() );
            }
        }
        return lista;
    }

    public static boolean guardarBanco(File directorio, Banco banco){
        boolean guardado = false;
        ArrayList<Banco> listaBanco = Banco.cargarBanco(directorio);
        File f = new File(directorio, nomArchivo);
        if(f.exists()){
            try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(f))){
                listaBanco.add(banco);
                os.writeObject(listaBanco);
                guardado = true;
            }catch (Exception e){
                Log.e("Banco", "guardarBanco: " + e.getMessage());
            }
        }
        return guardado;
    }

    public static boolean eliminarBanco(File directorio, Banco banco){
        boolean guardado = false;
        ArrayList<Banco> listaBanco = Banco.cargarBanco(directorio);
        File f = new File(directorio, nomArchivo);
        if(f.exists()){
            try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(f))){
                listaBanco.remove(banco);
                os.writeObject(listaBanco);
                guardado = true;
            }catch (Exception e){
                Log.e("Banco", "eliminarBanco: " + e.getMessage());
            }
        }
        return guardado;
    }

    /**
     * <p>Compara este banco con otro objeto para determinar si son iguales. La
     * igualdad se basa en la comparación del RUC.</p>
     *
     * @param obj El objeto a comparar con este banco.
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

        final Banco other = (Banco) obj;
        return Objects.equals(this.ruc, other.ruc);
    }
}
