package utiles.sql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import utiles.sql.esquema.CampoBd;
import utiles.sql.esquema.ConstructorCampoBd;
import utiles.sql.esquema.EsquemaBd;
import utiles.sql.esquema.TablaBd;

/**
 *
 * @author jberjano
 */
public class MetadatosBd {

    private DatabaseMetaData metadata;
    private String catalogo;
    private List<String> listaTablas;
    private final Connection conexion;
    private final ConstructorCampoBd constructorCampoBd = new ConstructorCampoBd();

    public MetadatosBd(Connection conexion) {
        this.conexion = conexion;
    }

    public MetadatosBd(Connection conexion, String catalogo) {
        this.conexion = conexion;
        this.catalogo = catalogo;
    }

    public MetadatosBd(Connection conexion, List<String> listaTablas) {
        this.conexion = conexion;
        this.listaTablas = listaTablas;
    }

    private void verificarMetadata() throws SQLException {
        if (metadata == null) {
            metadata = conexion.getMetaData();
        }
    }

    public List<String> getEsquemas() throws SQLException {
        verificarMetadata();

        List<String> esquemas = null;
        ResultSet resultset = null;
        try {
            resultset = metadata.getSchemas(catalogo, "%");
            ConstructorString constructor = new ConstructorString("TABLE_SCHEM");
            esquemas = constructor.obtenerListaEntidades(resultset);
        } finally {
            if (resultset != null) {
                resultset.close();
            }
        }
        return esquemas;
    }

    public List<String> getTablas() throws SQLException {
        return getTablas("");
    }

    public List<String> getTablas(String esquema) throws SQLException {
        verificarMetadata();

        if (listaTablas != null) {
            return listaTablas;
        }
        ResultSet resultset = null;
        try {
            resultset = metadata.getTables(catalogo, esquema, "%", new String[]{"TABLE"});
            ConstructorString constructor = new ConstructorString("TABLE_NAME");
            listaTablas = constructor.obtenerListaEntidades(resultset);
        } finally {
            if (resultset != null) {
                resultset.close();
            }
        }
        return listaTablas;
    }

    public List<CampoBd> getCampos(String esquema, String tabla) throws SQLException {

        List<String> clavesPrimarias = null;
        ResultSet rsPk = null;
        try {
            rsPk = metadata.getPrimaryKeys(null, null, tabla);
            ConstructorString constructorString = new ConstructorString("COLUMN_NAME");
            clavesPrimarias = constructorString.obtenerListaEntidades(rsPk);
        } finally {
            if (rsPk != null) {
                rsPk.close();
            }
        }
        if (clavesPrimarias == null) {
            return null;
        }

        List<CampoBd> campos = null;
        ResultSet rs = null;
        try {
            rs = metadata.getColumns(null, null, tabla, "%");
            campos = constructorCampoBd.obtenerListaEntidades(rs);
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
        if (campos == null) {
            return null;
        }

        Map<String, CampoBd> mapaCampos = new HashMap<String, CampoBd>();
        for (CampoBd campo : campos) {
            mapaCampos.put(campo.getNombreSql(), campo);
        }

        for (String clavePrimaria : clavesPrimarias) {
            CampoBd campo = mapaCampos.get(clavePrimaria);
            campo.setClavePrimaria(true);
        }

        return campos;
    }

    public EsquemaBd getEsquemaBd() throws SQLException {
        return getEsquemaBd("");
    }

    public EsquemaBd getEsquemaBd(String esquema) throws SQLException {

        verificarMetadata();

        EsquemaBd esquemaBd = new EsquemaBd();

        List<String> nombresTablas = getTablas();

        if (nombresTablas == null) {
            return null;
        }

        for (String tabla : nombresTablas) {
            TablaBd tablaBd = new TablaBd();
            tablaBd.setNombre(tabla);
            List<CampoBd> campos = getCampos(esquema, tabla);
            tablaBd.setCampos(campos);
            esquemaBd.agregarTabla(tablaBd);
        }
        return esquemaBd;
    }

}
