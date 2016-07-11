package utiles.sql.sincronizacion;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import utiles.sql.ConstructorLong;
import utiles.sql.EjecutorScript;
import utiles.sql.EjecutorSentenciaDelete;
import utiles.sql.EjecutorSentenciaInsert;
import utiles.sql.EjecutorSentenciaSelect;
import utiles.sql.EjecutorSentenciaUpdate;
import utiles.sql.GestorConexiones;
import utiles.sql.MetadatosBd;
import utiles.sql.esquema.CampoBd;
import utiles.sql.esquema.EsquemaBd;
import utiles.sql.esquema.ExportadorDDL;
import utiles.sql.esquema.TablaBd;

/**
 * Sincroniza la tablas de dos bases de datos.
 *
 * @author jberjano
 */
public class SincronizadorBd {

    protected final GestorConexiones gestorConexionOrigen;
    protected final GestorConexiones gestorConexionDestino;

    public SincronizadorBd(GestorConexiones gestorConexionOrigen,
            GestorConexiones gestorConexionDestino) {
        this.gestorConexionOrigen = gestorConexionOrigen;
        this.gestorConexionDestino = gestorConexionDestino;
    }

    public boolean sincronizarTablas(EsquemaBd esquema, boolean soloInsertar) throws SQLException {

        List<TablaBd> tablas = esquema.getTablas();
        for (TablaBd tabla : tablas) {
            boolean ok;
            if (soloInsertar) {
                ok = copiarTabla(tabla);
            } else {
                ok = sincronizarTabla(tabla);
            }
            if (!ok) {
                return false;
            }
        }
        return true;
    }

    protected String getSelectTabla(TablaBd tabla) {
        return "SELECT * FROM " + tabla.getNombre();
    }

    protected String buscarClavePrimaria(TablaBd tabla) {
        String clavePrimaria = null;
        for (CampoBd campo : tabla.getCampos()) {
            if (campo.isClavePrimaria()) {
                clavePrimaria = campo.getNombreSql();
                break;
            }
        }
        if (clavePrimaria == null) {
            clavePrimaria = "ID";
        }
        return clavePrimaria;
    }

    protected String getSelectClavesPrimarias(TablaBd tabla) {
        String clavePrimaria = buscarClavePrimaria(tabla);
        return "SELECT " + clavePrimaria + " FROM " + tabla.getNombre();
    }
    
    private boolean copiarTabla(TablaBd tabla) throws SQLException {

        EjecutorSentenciaSelect ejecutorSelectOrigen = new EjecutorSentenciaSelect(gestorConexionOrigen);

        String sql = getSelectTabla(tabla);
        ConstructorEntidadGenerica constructor = new ConstructorEntidadGenerica(tabla);
        List<EntidadGenerica> listaEntidades = ejecutorSelectOrigen.obtenerListaEntidades(sql, constructor);

        for (EntidadGenerica entidad : listaEntidades) {
            boolean ok = insertarFila(tabla, entidad);
            if (!ok) {
                return false;
            }
        }
        return true;
    }

    private boolean sincronizarTabla(TablaBd tabla) throws SQLException {

        EjecutorSentenciaSelect ejecutorSelectOrigen = new EjecutorSentenciaSelect(gestorConexionOrigen);
        EjecutorSentenciaSelect ejecutorSelectDestino = new EjecutorSentenciaSelect(gestorConexionDestino);

        ConstructorLong constructorLong = new ConstructorLong();
        
        String sqlClavesPrimarias = getSelectClavesPrimarias(tabla);

        List<Long> listaClavesPrimariasOrigen = ejecutorSelectOrigen.obtenerListaEntidades(sqlClavesPrimarias, constructorLong);
        Set<Long> clavesPrimariasOrigen = new HashSet<Long>(listaClavesPrimariasOrigen);

        List<Long> listaClavesPrimariasDestino = ejecutorSelectDestino.obtenerListaEntidades(sqlClavesPrimarias, constructorLong);
        Set<Long> clavesPrimariasDestino = new HashSet<Long>(listaClavesPrimariasDestino);

        String sql = getSelectTabla(tabla);
        ConstructorEntidadGenerica constructor = new ConstructorEntidadGenerica(tabla);
        List<EntidadGenerica> listaEntidadesOrigen = ejecutorSelectOrigen.obtenerListaEntidades(sql, constructor);

        for (EntidadGenerica entidad : listaEntidadesOrigen) {
            boolean ok;
            boolean existeEntidad = clavesPrimariasDestino.contains(entidad.getPk());
            if (existeEntidad) {
                ok = modificarFila(tabla, entidad);
            } else {
                ok = insertarFila(tabla, entidad);
            }
            if (!ok) {
                return false;
            }
        }

        for (Long id : listaClavesPrimariasDestino) {
            if (!clavesPrimariasOrigen.contains(id)) {
                boolean ok = borrarFila(tabla, id);
                if (!ok) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean insertarFila(TablaBd tabla, EntidadGenerica entidad) throws SQLException {

        EjecutorSentenciaInsert insert = new EjecutorSentenciaInsert(gestorConexionDestino);
        insert.setTabla(tabla.getNombre());

        List<CampoBd> campos = tabla.getCampos();
        for (CampoBd campo : campos) {
            insert.agregarCampo(campo.getNombreSql(), entidad.get(campo.getNombreSql()));
        }

        return insert.ejecutar();
    }

    private boolean modificarFila(TablaBd tabla, EntidadGenerica entidad) throws SQLException {
        EjecutorSentenciaUpdate update = new EjecutorSentenciaUpdate(gestorConexionDestino);
        update.setTabla(tabla.getNombre());

        List<CampoBd> campos = tabla.getCampos();
        for (CampoBd campo : campos) {
            Object valor = entidad.get(campo.getNombreSql());
            if (campo.isClavePrimaria()) {
                update.agregarPk(campo.getNombreSql(), valor);
            } else {
                update.agregarCampo(campo.getNombreSql(), valor);
            }
        }

        return update.ejecutar();
    }

    private boolean borrarFila(TablaBd tabla, Long id) throws SQLException {
        EjecutorSentenciaDelete delete = new EjecutorSentenciaDelete(gestorConexionDestino);
        delete.setTabla(tabla.getNombre());
        String clavePrimaria = buscarClavePrimaria(tabla);
        delete.agregarPk(clavePrimaria, id);
        return delete.ejecutar();
    }

    public boolean crearBaseDatosDestino(List<String> listaTablas) throws SQLException {
        MetadatosBd metadatos = new MetadatosBd(
                gestorConexionOrigen.obtenerConexion(), listaTablas);
        EsquemaBd esquemaBd = metadatos.getEsquemaBd();

        EjecutorScript ejecutor = new EjecutorScript(gestorConexionDestino);
        ExportadorDDL exportadorDDL = new ExportadorDDL(gestorConexionDestino.getFormateadorSql());

        try {
            String sqlBorrado = exportadorDDL.getSqlBorrado(esquemaBd);
            ejecutor.ejecutar(sqlBorrado);
        } catch (SQLException ex) {
            System.out.println("Borrado no realizado");
        }

        String sqlCreacion = exportadorDDL.getSqlCreacion(esquemaBd);
        return ejecutor.ejecutar(sqlCreacion);

    }
}
