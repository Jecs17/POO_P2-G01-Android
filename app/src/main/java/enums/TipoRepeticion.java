
package enums;

/**
 *<h3>Clase Enum TipoRepeticion</h3>
 *<p>Esta enumeración permite clasificar y definir la frecuencia de repetición
 * para movimientos financieros recurrentes, como ingresos o gastos.</p>
 * <p>
 * Los tipos de repetición disponibles son:
 * <ul>
 *   <li>SIN_REPETICION: Movimiento único sin repetición.</li>
 *   <li>MES: Repetición mensual del movimiento.</li>
 * </ul>
 * </p>
 * @author Grupo1
 */
public enum TipoRepeticion {
    /**
     * Movimiento único sin repetición.
     */
    SIN_REPETICION,

    /**
     * Repetición mensual del movimiento.
     */
    MES;
}
