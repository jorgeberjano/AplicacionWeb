package utiles.sql.sincronizacion;

import java.sql.SQLException;
import java.util.List;
import utiles.sql.AdaptadorResultSet;
import utiles.sql.ConstructorEntidad;
import utiles.sql.esquema.CampoBd;
import utiles.sql.esquema.TablaBd;

/**
 *
 * @author jberjano
 */
public class ConstructorEntidadGenerica extends ConstructorEntidad<EntidadGenerica> {

    private TablaBd tabla;

    public ConstructorEntidadGenerica(TablaBd tabla) {
        this.tabla = tabla;
    }

    @Override
    protected EntidadGenerica construirEntidad(AdaptadorResultSet rs) throws SQLException {
        EntidadGenerica entidad = new EntidadGenerica();
        List<CampoBd> campos = tabla.getCampos();
        for (CampoBd campo : campos) {
            Object valor = rs.get(campo);
            if (campo.isClavePrimaria()) {
                entidad.setPk(campo.getNombreSql(), valor);
            } else {
                entidad.set(campo.getNombreSql(), valor);
            }
        }
        return entidad;
    }

}
