package com.jbp.aplicacionweb.dao;

import com.jbp.aplicacionweb.dto.FiltroDtoGenerico;
import com.jbp.ges.entidad.CampoGes;
import com.jbp.ges.entidad.ConsultaGes;
import java.util.List;
import utiles.conversion.Conversion;
import utiles.sql.Dao;
import utiles.sql.EjecutorSentenciaGuardado;
import utiles.sql.GestorConexiones;
import utiles.sql.sentencia.SentenciaSql;

/**
 *
 * @author Jorge
 */
public class RepositorioGes extends Dao implements Dao.Listener {
    
    private ConsultaGes consulta;
    private ConstructorEntidadGes constructor;
    private String mensajeError;
    
    public RepositorioGes(GestorConexiones gestorConexiones, ConsultaGes consulta) {
        super(gestorConexiones);
        setListener(this);
        this.consulta = consulta;
        constructor = new ConstructorEntidadGes(consulta);
    }
    
    /**
     * Guarda una entidad.
     */
    public boolean guardar(EntidadGes entidad) {

        boolean esInserccion = entidad.esNueva();
        EjecutorSentenciaGuardado ejecutor = esInserccion ? ejecutorInsert : ejecutorUpdate;

        ejecutor.setTabla(consulta.getTabla());
        List<CampoGes> campos = consulta.getListaCampos();
               
        ClavePrimaria nuevaClavePrimaria = new ClavePrimaria();
        for (CampoGes campo : campos) {
            String nombreCampo = campo.getNombre();
            String nombreSqlCampo = campo.getNombreSql();
            Object valor = entidad.get(nombreCampo);
            if (campo.isClave()) {                
                Object valorClave = entidad.getClavePrimaria().get(nombreCampo);
                if (valorClave != null) {
                    ejecutor.agregarPk(nombreSqlCampo, valorClave);
                }
                nuevaClavePrimaria.set(nombreCampo, valor);
            }
            ejecutor.agregarCampo(nombreSqlCampo, valor);
        }
        boolean ok = false;
        try {
            ok = ejecutor.ejecutar();
        } catch (Exception e) {
            reportarExcepcion("Error guardando entidad", e);
        }
        
        if (ok) {
            entidad.setClavePrimaria(nuevaClavePrimaria);
        }

//        if (esInserccion && ok) {
//            entidad.setId(secuencia.getValorGenerado());
//        }
        return ok;
    }

    /**
     * Borra una entidad.
     */
    public boolean borrar(EntidadGes usuario) {
        return borrar(usuario.getClavePrimaria());
    }

    public boolean borrar(ClavePrimaria pk) {

        if (pk == null) {
            return false;
        }

        try {
            ejecutorDelete.setTabla(consulta.getTabla());
            for (String nombreCampo : pk.getNombres()) {
                //CampoGes campo = consulta.getCampoPorNombre(nombreCampo);
                Object valor = pk.get(nombreCampo);
                ejecutorDelete.agregarPk(nombreCampo, valor);
            }
            ejecutorDelete.ejecutar();
        } catch (Exception e) {
            reportarExcepcion("Error eliminando " + consulta.getNombreEnSingular(), e);
            return false;
        } finally {
        }
        return true;
    }

    /**
     * Devuelve todas las entidades
     */
    public List<EntidadGes> getLista() {
        List<EntidadGes> entidades = null;

        try {
            String sql = consulta.getSql();
            entidades = ejecutorSelect.obtenerListaEntidades(sql, constructor);
        } catch (Exception e) {
            reportarExcepcion("Error en obtener la lista de " + consulta.getNombre(), e);
        }
        return entidades;
    }

    /**
     * Devuelve una entidad por su clave primaria
     */
    public EntidadGes getEntidad(ClavePrimaria pk) {

        if (pk == null || consulta == null) {
            return null;
        }

        EntidadGes entidad = null;
        SentenciaSql sentencia = new SentenciaSql(consulta.getSql());
        String strWhere = construirWhere(pk);
        sentencia.where(strWhere);
        try {
            String sql = sentencia.getSql();

            entidad = ejecutorSelect.obtenerEntidad(sql, constructor);
        } catch (Exception e) {
            reportarExcepcion("Error al obtener un usuario por id", e);
        }

        return entidad;
    }

    public List<EntidadGes> getPagina(FiltroDtoGenerico filtro,
            String campoOrden, boolean ordenDescendente, int primerElemento, int numeroElementos) {

        List<EntidadGes> entidades = null;
        SentenciaSql sentencia = crearSentenciaSql(filtro, campoOrden, ordenDescendente);

        try {
            entidades = ejecutorSelect.obtenerPaginaEntidades(sentencia.getSql(), constructor,
                    primerElemento, numeroElementos);
        } catch (Exception e) {
            reportarExcepcion("Error al obtener página de " + consulta.getNombre(), e);
        }
        return entidades;
    }

    public int getNumeroTotalEntidades() {
        return ejecutorSelect.getNumeroTotalEntidades();
    }

    private SentenciaSql crearSentenciaSql(FiltroDtoGenerico filtro, String campoOrden, boolean ordenDescendente) {
        
        SentenciaSql sentencia = new SentenciaSql(consulta.getSql());
        if (!Conversion.isBlank(campoOrden)) {
            sentencia.orderBy(campoOrden, ordenDescendente);
        }
//
//        if (filtro != null) {
//            if (filtro.getNombre() != null) {
//                sentencia.where("NOMBRE LIKE '" + filtro.getNombre() + "%'");
//            }
//            if (filtro.getApellido1() != null) {
//                sentencia.where("APELLIDO1 LIKE '" + filtro.getApellido1() + "%'");
//            }
//            if (filtro.getApellido2() != null) {
//                sentencia.where("APELLIDO2 LIKE '" + filtro.getApellido2() + "%'");
//            }
//        }

        return sentencia;
    }

    public List<EntidadGes> getListaFiltrada(FiltroDtoGenerico filtro, String campoOrden, boolean ordenDescendente) {
        List<EntidadGes> entidades = null;
        SentenciaSql sentencia = crearSentenciaSql(filtro, campoOrden, ordenDescendente);

        try {
            entidades = ejecutorSelect.obtenerListaEntidades(sentencia.getSql(), constructor);
        } catch (Exception e) {
            reportarExcepcion("Error al obtener " + consulta.getNombre(), e);
        }
        return entidades;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    @Override
    public void error(String mensaje, Exception ex) {
        mensajeError = mensaje;
        if (ex != null) {
            mensajeError += ". Causa: " + ex.getMessage();
        }
    }

    private String construirWhere(ClavePrimaria pk) {
        
        SentenciaSql sentencia = new SentenciaSql();
        
        List<CampoGes> campos = consulta.getListaCampos();
        for (CampoGes campo : campos) {
            String nombreSqlCampo = campo.getNombreSql();
            String nombreCampo = campo.getNombre();
            Object valor = pk.get(nombreCampo);            
            if (valor != null) {
                Object valorTipado = campo.convertirValor(valor);
                String valorSql = SentenciaSql.aFormatoSql(valorTipado);
                sentencia.where(nombreSqlCampo + " = " + valorSql);
            }  
        } 
        return sentencia.getWhere();
    }

}
