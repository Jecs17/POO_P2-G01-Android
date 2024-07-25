package app.modelo.Banco;

import app.modelo.persona.Persona;
import java.time.LocalDate;
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
public class Banco {
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
        return String.format("%-18s %-20s %-24s %-23s Oficial: %-15s Teléfono: %-12s", fechaRegistro, ruc, entidadBancaria, email, oficialCredito.getNombre(), oficialCredito.getTelefono());
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
