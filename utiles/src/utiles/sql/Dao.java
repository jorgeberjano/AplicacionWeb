package utiles.sql;

/**
 * Clase base para los objetos de acceso a datos.
 *
 * @author jberjano
 */
public abstract class Dao {

    protected GestorConexiones gestorConexiones;
    protected EjecutorSentenciaSelect ejecutorSelect;
    protected EjecutorSentenciaInsert ejecutorInsert;
    protected EjecutorSentenciaUpdate ejecutorUpdate;
    protected EjecutorSentenciaDelete ejecutorDelete;
    protected EjecutorSentenciaSimple ejecutorSentenciaSimple;

    private static Listener listener;

    public interface Listener {

        void error(String mensaje, Exception ex);
    }

    public static void setListener(Listener listener) {
        Dao.listener = listener;
    }

    public Dao(GestorConexiones gestorConexiones) {
        this.gestorConexiones = gestorConexiones;
        ejecutorSelect = new EjecutorSentenciaSelect(gestorConexiones);
        ejecutorInsert = new EjecutorSentenciaInsert(gestorConexiones);
        ejecutorUpdate = new EjecutorSentenciaUpdate(gestorConexiones);
        ejecutorDelete = new EjecutorSentenciaDelete(gestorConexiones);
        ejecutorSentenciaSimple = new EjecutorSentenciaSimple(gestorConexiones);
    }

    protected void reportarExcepcion(final String mensaje, final Exception ex) {
        if (listener != null) {
            listener.error(mensaje, ex);
        }
    }
}
