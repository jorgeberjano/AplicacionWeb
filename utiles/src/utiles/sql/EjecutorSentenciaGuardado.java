package utiles.sql;

import java.io.ByteArrayInputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utiles.tiempo.Fecha;
import utiles.tiempo.FechaAbstracta;
import utiles.tiempo.FechaHora;

/**
 * Clase base para los ejecutores de sentecias de guardado de entidades (insert
 * y update)
 *
 * @author jberjano
 */
public abstract class EjecutorSentenciaGuardado {

    protected final GestorConexiones gestorConexiones;
    protected String tabla;
    protected final List<Campo> listaCampos = new ArrayList<Campo>();
    protected final List<Campo> listaPKs = new ArrayList<Campo>();
    private int indice = 1;

    protected static class Campo {

        String nombre;
        Object valor;

        Campo(String nombre, Object valor) {
            this.nombre = nombre;
            this.valor = valor;
        }
    }

    public EjecutorSentenciaGuardado(GestorConexiones gestorConexiones) {
        this.gestorConexiones = gestorConexiones;
    }

    public abstract boolean ejecutar() throws SQLException;

    public void setTabla(String tabla) {
        this.tabla = tabla;
        limpiar();
    }

    protected void limpiar() {
        indice = 1;
        listaCampos.clear();
        listaPKs.clear();
    }

    public void agregarCampo(String nombre, Object valor) {
        listaCampos.add(new Campo(nombre, valor));
    }

    public void agregarPk(String nombre, Object valor) {
        listaPKs.add(new Campo(nombre, valor));
    }

    protected void asignarValor(PreparedStatement statement, Object valor) throws SQLException {
        if (valor == null || valor instanceof Secuencia) {
            return;
        }
        if (valor instanceof FechaAbstracta && ((FechaAbstracta) valor).esPresente()) {
            return;
        }

        if (valor instanceof Boolean) {
            statement.setBoolean(indice, (Boolean) valor);
            //statement.setInt(indice, (Boolean) valor ? 1 : 0);
        } else if (valor instanceof Integer) {
            statement.setInt(indice, (Integer) valor);
        } else if (valor instanceof Long) {
            statement.setLong(indice, (Long) valor);
        } else if (valor instanceof Fecha) {
            statement.setDate(indice, ((FechaAbstracta) valor).toSqlDate());
        } else if (valor instanceof FechaHora) {
            statement.setTimestamp(indice, ((FechaAbstracta) valor).toTimestamp());
        } else if (valor instanceof byte[]) {
            byte[] array = (byte[]) valor;
            statement.setBinaryStream(indice, new ByteArrayInputStream(array), array.length);
        } else if (valor instanceof Enum) {
            statement.setInt(indice, ((Enum) valor).ordinal());
        } else {
            statement.setString(indice, valor.toString());
        }
        indice++;
    }

    protected String getSqlValor(Campo campo) throws SQLException {
        if (campo.valor == null) {
            return "null";
        }

        if (campo.valor instanceof FechaHora) {
            FechaHora fechaHora = (FechaHora) campo.valor;

            if (fechaHora.esPresente()) {
                return gestorConexiones.getFormateadorSql().getFechaHoraActual();
            }
        }

        if (campo.valor instanceof Secuencia) {           
            campo.valor = getValorSecuencia((Secuencia) campo.valor, campo);
        }

        return "?";
    }

    protected Long getValorSecuencia(Secuencia secuencia, Campo campo) throws SQLException {
        secuencia.setFormateadorSql(gestorConexiones.getFormateadorSql());
        String sql = secuencia.getSql(tabla, campo.nombre);
        EjecutorSentenciaSelect ejecutorSelect = new EjecutorSentenciaSelect(gestorConexiones);
        Long id = ejecutorSelect.obtenerEntidad(sql, new ConstructorLong());
        secuencia.setValorGenerado(id);
        return id;
    }
}
