
package modelo.cuenta;

import android.content.Context;
import android.util.Log;

import com.example.poo_p2_g01_android.PersonaBancoActivity;

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

import modelo.banco.Banco;
import modelo.persona.Persona;

/**
 * <h3>Clase CuentaFinanciera</h3>
 * <p>Representa una cuenta financiera con atributos comunes a cuentas por pagar y por cobrar.</p>
 * 
 * @autor Grupo1
 */
public class CuentaFinanciera implements Serializable {
    /**
     * Código único de la cuenta.
     */
    protected int codigo;
    /**
     * Valor de la cuenta.
     */
    protected double valor;
    /**
     * Descripción de la cuenta.
     */
    protected String descripcion;
    /**
     * Fecha en la que se realizó el préstamo.
     */
    protected LocalDate fechaPrestamo;
    /**
     * Cuota de pago de la cuenta.
     */
    protected double cuota;
    /**
     * Fecha de inicio del pago de la cuenta.
     */
    protected LocalDate fechaInicio;
    /**
     * Fecha de finalización del pago de la cuenta.
     */
    protected LocalDate fechaFin;

    /**
     * Nombre del Archivo serializado de las CuentasFinancieras
     */
    public static final String nomArchivo = "cuentas_financieras.ser";
    
    /**
     * <h3>Constructor de la clase CuentaFinanciera</h3>
     * <p>Inicializa una cuenta financiera con los detalles específicos.</p>
     * 
     * @param codigo el código de la cuenta.
     * @param valor el valor de la cuenta.
     * @param descripcion la descripción de la cuenta.
     * @param fechaPrestamo la fecha en la que se realizó el préstamo.
     * @param cuota la cuota de pago.
     * @param fechaInicio la fecha de inicio del pago.
     * @param fechaFin la fecha de finalización del pago.
     */
    public CuentaFinanciera(int codigo, double valor, String descripcion, LocalDate fechaPrestamo, double cuota, LocalDate fechaInicio, LocalDate fechaFin) {
        this.codigo = codigo;
        this.valor = valor;
        this.descripcion = descripcion;
        this.fechaPrestamo = fechaPrestamo;
        this.cuota = cuota;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }
    
    /**
     * Obtiene el código de la cuenta.
     * 
     * @return el código de la cuenta.
     */
    public int getCodigo() {
        return codigo;
    }
    
    /**
     * Establece el código de la cuenta.
     * 
     * @param codigo el nuevo código de la cuenta.
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    
    /**
     * Obtiene el valor de la cuenta.
     * 
     * @return el valor de la cuenta.
     */
    public double getValor() {
        return valor;
    }
    
    /**
     * Establece el valor de la cuenta.
     * 
     * @param valor el nuevo valor de la cuenta.
     */
    public void setValor(double valor) {
        this.valor = valor;
    }
    
    /**
     * Obtiene la descripción de la cuenta.
     * 
     * @return la descripción de la cuenta.
     */
    public String getDescripcion() {
        return descripcion;
    }
    
    /**
     * Establece la descripción de la cuenta.
     * 
     * @param descripcion la nueva descripción de la cuenta.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    /**
     * Obtiene la fecha en la que se realizó el préstamo.
     * 
     * @return la fecha del préstamo.
     */
    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }
    
    /**
     * Establece la fecha en la que se realizó el préstamo.
     * 
     * @param fechaPrestamo la nueva fecha del préstamo.
     */
    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }
    
    /**
     * Obtiene la cuota de pago de la cuenta.
     * 
     * @return la cuota de la cuenta.
     */
    public double getCuota() {
        return cuota;
    }
    
    /**
     * Establece la cuota de pago de la cuenta.
     * 
     * @param cuota la nueva cuota de la cuenta.
     */
    public void setCuota(double cuota) {
        this.cuota = cuota;
    }
    
     /**
     * Obtiene la fecha de inicio del pago de la cuenta.
     * 
     * @return la fecha de inicio del pago.
     */
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }
    
    /**
     * Establece la fecha de inicio del pago de la cuenta.
     * 
     * @param fechaInicio la nueva fecha de inicio del pago.
     */
    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    
    /**
     * Obtiene la fecha de finalización del pago de la cuenta.
     * 
     * @return la fecha de finalización del pago.
     */
    public LocalDate getFechaFin() {
        return fechaFin;
    }
    
    /**
     * Establece la fecha de finalización del pago de la cuenta.
     * 
     * @param fechaFin la nueva fecha de finalización del pago.
     */
    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public static ArrayList<CuentaFinanciera> obtenerCuentasIniciales(Context context){
        ArrayList<CuentaFinanciera> lista = new ArrayList<>();
        Persona deudor = PersonaBancoActivity.buscarPersona("094232152",context);
        Persona acreedor = PersonaBancoActivity.buscarPersona("045613445", context);
        Banco banco = PersonaBancoActivity.buscarBanco("2341654582001", context);
        lista.add(new CuentaxCobrar(500.00, "Sueldo", LocalDate.of(2024, Month.JULY, 02), 50.00, LocalDate.of(2024, Month.JULY, 2), LocalDate.of(2024, Month.APRIL, 12), deudor));
        lista.add(new CuentaxPagar(1500.00, "Cambio", LocalDate.of(2024, Month.JULY, 02), 50.00, LocalDate.of(2024, Month.JULY, 2), LocalDate.of(2024, Month.APRIL, 12), 0.00, acreedor));
        lista.add(new CuentaxPagar(1500.00, "Para la compra de una casa", LocalDate.of(2024, Month.FEBRUARY, 02), 50.00, LocalDate.of(2024, Month.FEBRUARY, 2), LocalDate.of(2024, Month.APRIL, 12), 0.00, banco));
        return lista;
    }

    public static boolean crearDatosIniciales(File directorio, Context context){
        ArrayList<CuentaFinanciera> lista = CuentaFinanciera.obtenerCuentasIniciales(context);
        boolean guardado = false;
        File f = new File(directorio, nomArchivo);

        if(! f.exists()){
            try (ObjectOutputStream os= new ObjectOutputStream(new FileOutputStream(f))){
                os.writeObject(lista);
                guardado = true;
            }catch (IOException e){
                Log.e("CuentaFinanciera", "Crear Datos Iniciales: " + e.getMessage());
            }
        } else guardado = true;
        return guardado;
    }

    public static ArrayList<CuentaFinanciera> cargarCuentasFinancieras(File directorio){
        ArrayList<CuentaFinanciera> lista = new ArrayList<>();
        File f = new File(directorio, nomArchivo);
        if(f.exists()){
            try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(f))){
                lista = (ArrayList<CuentaFinanciera>) is.readObject();
            }catch (Exception e){
                Log.e("CuentaFinanciera", "cargarCuentasFinancieras: " + e.getMessage());
            }
        }
        return lista;
    }

    public static ArrayList<CuentaxCobrar> cargarCuentasxCobrar(File directorio){
        ArrayList<CuentaFinanciera> listaCF = cargarCuentasFinancieras(directorio);
        ArrayList<CuentaxCobrar> listaCuentaxCobrar = new ArrayList<>();
        for(CuentaFinanciera cuenta: listaCF){
            if(cuenta instanceof CuentaxCobrar){
                listaCuentaxCobrar.add((CuentaxCobrar) cuenta);
            }
        }
        return listaCuentaxCobrar;
    }

    public static ArrayList<CuentaxPagar> cargarCuentasxPagar(File directorio){
        ArrayList<CuentaFinanciera> listaCF = cargarCuentasFinancieras(directorio);
        ArrayList<CuentaxPagar> listaCuentaxPagar= new ArrayList<>();
        for(CuentaFinanciera cuenta: listaCF){
            if(cuenta instanceof CuentaxPagar){
                listaCuentaxPagar.add((CuentaxPagar) cuenta);
            }
        }
        return listaCuentaxPagar;
    }

    public static boolean guardarCuenta(File directorio, CuentaFinanciera cuenta){
        boolean guardado = false;
        ArrayList<CuentaFinanciera> listaCF = CuentaFinanciera.cargarCuentasFinancieras(directorio);
        File f = new File(directorio, nomArchivo);
        if(f.exists()){
            try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(f))){
                listaCF.add(cuenta);
                os.writeObject(listaCF);
                guardado = true;
            }catch (Exception e){
                Log.e("CuentaFinanciera", "guardarCuentas: " + e.getMessage());
            }
        }
        return guardado;
    }
    
}
