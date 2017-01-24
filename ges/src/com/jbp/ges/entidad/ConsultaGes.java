package com.jbp.ges.entidad;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import utiles.conversion.Conversion;

/**
 * Representa una consulta GES
 *
 * @author Jorge
 */
public class ConsultaGes implements Serializable {

    // Estilos de visualizacion
    public final static int CONSULTA_EDICION_ESTANDAR = 0x000001;
    public final static int CONSULTA_OCULTA_MENU = 0x000002;
    public final static int CONSULTA_OCULTA_MENU_CONS = 0x000004;
    public final static int CONSULTA_OCULTA_BARRA = 0x000008;
    public final static int CONSULTA_SIN_ALTA = 0x000010;
    public final static int CONSULTA_SIN_BAJA = 0x000020;
    public final static int CONSULTA_SIN_MODIFICACION = 0x000040;
    public final static int CONSULTA_SIN_COPIA = 0x000800;
    public final static int CONSULTA_SIN_VISUALIZACION = 0x000100;
    public final static int CONSULTA_SIN_CONFIGURACION = 0x000200;
    public final static int CONSULTA_SIN_IMPRESION = 0x000400;
    public final static int CONSULTA_SIN_TOTALIZACION = 0x000800;
    public final static int CONSULTA_SIN_IMPORTACION = 0x001000;
    public final static int CONSULTA_SIN_EXPORTACION = 0x002000;
    public final static int CONSULTA_SIN_FILTRO = 0x004000;
    public final static int CONSULTA_SIN_EXTRACTO = 0x008000;
    public final static int CONSULTA_SELECCION_MULTIPLE = 0x010000;
    public final static int CONSULTA_PRECEDER_SEPARADOR = 0x020000;
    public final static int CONSULTA_NOMBRE_FEMENINO = 0x040000;
    public final static int CONSULTA_EDITAR_SUBCONSULTAS = 0x080000;
    public final static int CONSULTA_ORDEN_AUTO_ASC = 0x100000;
    public final static int CONSULTA_ORDEN_AUTO_DESC = 0x200000;

    // Estilos de impresion
    public final static int CONSIMPR_ORIENTACION_VERTICAL = 0x000001;
    public final static int CONSIMPR_ORIENTACION_HORIZONTAL = 0x000002;
    public final static int CONSIMPR_LINEAS_VERTICALES = 0x000004;
    public final static int CONSIMPR_LINEAS_HORIZONTALES = 0x000008;
    public final static int CONSIMPR_SUBTOTALES = 0x000010;
    public final static int CONSIMPR_TOTAL_GENERAL = 0x000020;
    public final static int CONSIMPR_TITULO_POR_DEFECTO = 0x000040;
    public final static int CONSIMPR_ENCABEZADO_POR_DEFECTO = 0x000080;
    public final static int CONSIMPR_ANCHURA_FIJA = 0x000100;
    public final static int CONSIMPR_INFORME_DE_DETALLE = 0x000200;
    public final static int CONSIMPR_FORMATO_TICKET = 0x000400;
    public final static int CONSIMPR_PERMITIR_EXPORTAR_RTF = 0x000800;
    public final static int CONSIMPR_PEDIR_NUMERO_COPIAS = 0x001000;
    public final static int CONSIMPR_NO_IMPRIMIR_RECUADRO = 0x002000;
    public final static int CONSIMPR_NO_IMPRIMIR_LISTA = 0x004000;
    public final static int CONSIMPR_TOTALIZAR_ANTERIORES = 0x008000;
    public final static int CONSIMPR_GRUPOS_FUERA_DE_TABLA = 0x010000;
    public final static int CONSIMPR_MANTENER_ESPACIADO = 0x020000;
    public final static int CONSIMPR_SIN_MARGENES = 0x040000;

    private String idConsulta;

    private String nombre;
    private String tabla;
    private int imagen;
    private String nombreSubconsultas;
    private String camposFiltroPrevio;
    private String sql;
    private List<CampoGes> listaCampos = new ArrayList();
    private int estilo;
    private String nombreEnSingular;
    private String camposPorDefecto;
    private String valoresPorDefecto;
    private int estiloImpresion;
    private String tituloImpresion;
    private String subtituloImpresion;
    private String encabezadoImpresion;
    private String pieImpresion;
    private String textoInicialImpresion;
    private String textoFinalImpresion;
    private String consultasSeleccionPrevia;

    public String getIdConsulta() {
        return idConsulta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
        idConsulta = Conversion.quitarAcentos(nombre).replaceAll("\\s", "").toLowerCase();
    }

    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public String getNombreSubconsultas() {
        return nombreSubconsultas;
    }

    public void setNombreSubconsultas(String nombreSubconsultas) {
        this.nombreSubconsultas = nombreSubconsultas;
    }

    public String getCamposFiltroPrevio() {
        return camposFiltroPrevio;
    }

    public void setCamposFiltroPrevio(String camposFiltroPrevio) {
        this.camposFiltroPrevio = camposFiltroPrevio;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<CampoGes> getListaCampos() {
        return listaCampos;
    }

    public void setListaCampos(List<CampoGes> listaCampos) {
        this.listaCampos = listaCampos;
    }

    public int getEstilo() {
        return estilo;
    }

    public void setEstilo(int estilo) {
        this.estilo = estilo;
    }

    public String getNombreEnSingular() {
        return nombreEnSingular;
    }

    public void setNombreEnSingular(String nombreEnSingular) {
        this.nombreEnSingular = nombreEnSingular;
    }

    public String getCamposPorDefecto() {
        return camposPorDefecto;
    }

    public void setCamposPorDefecto(String camposPorDefecto) {
        this.camposPorDefecto = camposPorDefecto;
    }

    public String getValoresPorDefecto() {
        return valoresPorDefecto;
    }

    public void setValoresPorDefecto(String valoresPorDefecto) {
        this.valoresPorDefecto = valoresPorDefecto;
    }

    public int getEstiloImpresion() {
        return estiloImpresion;
    }

    public void setEstiloImpresion(int estiloImpresion) {
        this.estiloImpresion = estiloImpresion;
    }

    public String getTituloImpresion() {
        return tituloImpresion;
    }

    public void setTituloImpresion(String tituloImpresion) {
        this.tituloImpresion = tituloImpresion;
    }

    public String getSubtituloImpresion() {
        return subtituloImpresion;
    }

    public void setSubtituloImpresion(String subtituloImpresion) {
        this.subtituloImpresion = subtituloImpresion;
    }

    public String getEncabezadoImpresion() {
        return encabezadoImpresion;
    }

    public void setEncabezadoImpresion(String encabezadoImpresion) {
        this.encabezadoImpresion = encabezadoImpresion;
    }

    public String getPieImpresion() {
        return pieImpresion;
    }

    public void setPieImpresion(String pieImpresion) {
        this.pieImpresion = pieImpresion;
    }

    public String getTextoInicialImpresion() {
        return textoInicialImpresion;
    }

    public void setTextoInicialImpresion(String textoInicialImpresion) {
        this.textoInicialImpresion = textoInicialImpresion;
    }

    public String getTextoFinalImpresion() {
        return textoFinalImpresion;
    }

    public void setTextoFinalImpresion(String textoFinalImpresion) {
        this.textoFinalImpresion = textoFinalImpresion;
    }

    public String getConsultasSeleccionPrevia() {
        return consultasSeleccionPrevia;
    }

    public void setConsultasSeleccionPrevia(String consultasSeleccionPrevia) {
        this.consultasSeleccionPrevia = consultasSeleccionPrevia;
    }

    @Override
    public String toString() {
        return nombre;
    }

    public CampoGes getCampoPorNombre(String nombre) {
        if (listaCampos == null || nombre == null) {
            return null;
        }
        for (CampoGes campo : listaCampos) {
            if (campo.getNombre().equals(nombre)) {
                return campo;
            }
        }
        return null;
    }

}
