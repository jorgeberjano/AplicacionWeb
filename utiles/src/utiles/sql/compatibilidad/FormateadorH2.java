package utiles.sql.compatibilidad;

import java.util.Set;
import utiles.sql.esquema.CampoBd;
import static utiles.sql.TipoDato.BOOLEANO;
import static utiles.sql.TipoDato.BYTES;
import static utiles.sql.TipoDato.CADENA;
import static utiles.sql.TipoDato.ENTERO;
import static utiles.sql.TipoDato.FECHA;
import static utiles.sql.TipoDato.FECHA_HORA;
import static utiles.sql.TipoDato.REAL;
import utiles.tiempo.Fecha;
import utiles.tiempo.FechaHora;

/**
 *
 * @author jberjano
 */
public class FormateadorH2 implements FormateadorSql {

    @Override
    public String getFechaSql(Fecha fecha) {
        return "parsedatetime('" + fecha.toString() + "', 'dd-MM-yyyy')";
    }

    @Override
    public String getFechaHoraSql(FechaHora fechaHora) {
        return "parsedatetime('" + fechaHora.toString() + "', 'dd-MM-yyyy hh:mm:ss')";
    }

    @Override
    public String getBooleanSql(Boolean valor) {
        return valor ? "1" : "0";
    }

    @Override
    public String getNombreTipoSql(CampoBd campo) {
        switch (campo.getTipoDato()) {
            case CADENA:
                return "VARCHAR(" + campo.getTamano() + ")";
            case ENTERO:
                return "NUMBER(" + campo.getTamano() + ")";
            case REAL:
                return "DECIMAL(" + campo.getTamano() + ", " + campo.getDecimales() + ")";
            case BOOLEANO:
                return "NUMBER(1)";
            case FECHA:
                return "DATE";
            case FECHA_HORA:
                return "TIMESTAMP";
            case BYTES:
                return "BLOB";
            default:
                return "";
        }
    }

    @Override
    public String getSelectConsultaFechaHora() {
        return "select CURRENT_TIMESTAMP() from DUAL";
    }

    @Override
    public String getFucionNVL(String valor, String valorSiEsNulo) {
        return "NVL(" + valor + ", " + valorSiEsNulo + ")";
    }

    @Override
    public String getFechaHoraActual() {
        return "CURRENT_TIMESTAMP()";
    }

    @Override
    public Set<Integer> getCodigosErrorDesconexion() {
        return null;
    }

}
