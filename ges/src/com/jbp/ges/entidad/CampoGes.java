package com.jbp.ges.entidad;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import utiles.conversion.Base64;
import utiles.conversion.Conversion;
import utiles.sql.Campo;
import utiles.sql.TipoDato;

/**
 * Representa un campo de una consulta GES
 *
 * @author Jorge
 */
public class CampoGes implements Campo, Serializable {

    // Estilos
    public final static int CAMPO_NO_EDITABLE = 0x000001;
    public final static int CAMPO_NO_ACTUALIZABLE_EN_EDICION = 0x000002;
    public final static int CAMPO_SIEMPRE_MAYUSCULAS = 0x000004;
    public final static int CAMPO_MOSTRAR_BOTON_LUPA = 0x000008;
    public final static int CAMPO_REQUERIDO = 0x000010;
    public final static int CAMPO_SELECCION_OBLIGATORIA = 0x000020;
    public final static int CAMPO_CLAVE = 0x000040;
    public final static int CAMPO_FORMATO_CON_UNIDAD = 0x000080;
    public final static int CAMPO_NO_EDITABLE_EN_SUBCONSULTA = 0x000100;
    public final static int CAMPO_SOLO_LECTURA = 0x000200;
    public final static int CAMPO_SELECCION_RECOMENDABLE = 0x000400;
    public final static int CAMPO_IMPRIMIR_ACUMULADO = 0x000800;
    public final static int CAMPO_ACUMULAR_MEDIA = 0x001000;
    public final static int CAMPO_OCULTO = 0x002000;
    public final static int CAMPO_FILTRO_PREVIO_OBLIGATORIO = 0x004000;
    public final static int CAMPO_OCULTAR_SUBTOTALES_DE_GRUPO = 0x008000;
    public final static int CAMPO_SALTO_PAGINA_AL_SUBTOTALIZAR = 0x010000;
    public final static int CAMPO_NO_MODIFICABLE = 0x020000;
    public final static int CAMPO_PROPONER_VALORES = 0x040000;
    public final static int CAMPO_NO_NULO = 0x080000;
    public final static int CAMPO_NO_SUBTOTALIZAR = 0x100000;
    public final static int CAMPO_ORDEN_INICIAL = 0x200000;

    private String nombre;
    private String tabla;
    private String campo;
    private String titulo;
    private TipoDato tipoDato;
    private TipoRolGes tipoRol;
    private int alineacion;
    private int longitud;
    private String formato;
    private int decimales;
    private String unidad;
    private int estilo;
    private String expresionGT;
    private String formatoGT;
    private String consultaSeleccion;
    private String valorNulo;
    private String nombreCampoRelacion;
    private String valorPorDefecto;

    private List<String> opciones;

    private int indice;

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    public boolean isClave() {
        return tieneEstilo(CAMPO_CLAVE);
    }

    public boolean tieneEstilo(int mascara) {
        return (estilo & mascara) != 0;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public TipoDato getTipoDato() {
        return tipoDato;
    }

    public void setTipoDato(TipoDato tipoDato) {
        this.tipoDato = tipoDato;
    }

    public TipoRolGes getTipoRol() {
        return tipoRol;
    }

    public void setTipoRol(TipoRolGes tipoRol) {
        this.tipoRol = tipoRol;
    }

    public int getAlineacion() {
        return alineacion;
    }

    public void setAlineacion(int alineacion) {
        this.alineacion = alineacion;
    }

    public int getLongitud() {
        return longitud;
    }

    public void setLongitud(int longitud) {
        this.longitud = longitud;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public int getDecimales() {
        return decimales;
    }

    public void setDecimales(int decimales) {
        this.decimales = decimales;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public int getEstilo() {
        return estilo;
    }

    public void setEstilo(int estilo) {
        this.estilo = estilo;
    }

    public String getExpresionGT() {
        return expresionGT;
    }

    public void setExpresionGT(String expresionGT) {
        this.expresionGT = expresionGT;
    }

    public String getFormatoGT() {
        return formatoGT;
    }

    public void setFormatoGT(String formatoGT) {
        this.formatoGT = formatoGT;
    }

    public String getConsultaSeleccion() {
        return consultaSeleccion;
    }

    public void setConsultaSeleccion(String consultaSeleccion) {
        this.consultaSeleccion = consultaSeleccion;
    }

    public String getValorNulo() {
        return valorNulo;
    }

    public void setValorNulo(String valorNulo) {
        this.valorNulo = valorNulo;
    }

    public String getNombreCampoRelacion() {
        return nombreCampoRelacion;
    }

    public void setNombreCampoRelacion(String nombreCampoRelacion) {
        this.nombreCampoRelacion = nombreCampoRelacion;
    }

    public String getValorPorDefecto() {
        return valorPorDefecto;
    }

    public void setValorPorDefecto(String valorPorDefecto) {
        this.valorPorDefecto = valorPorDefecto;
    }

    public String toString() {
        return getNombre();
    }

    @Override
    public String getNombreSql() {
        return campo;
    }

    public String getTipoDatoJava() {
        if (tipoDato == null) {
            return null;
        }
        switch (tipoDato) {
            case CADENA:
                return "java.lang.String";
            case ENTERO:
                return "java.lang.Integer";
            case REAL:
                return "java.lang.Double";
            case BOOLEANO:
                return "java.lang.Boolean";
            case FECHA:
                return "utiles.tiempo.Fecha";
            case FECHA_HORA:
                return "utiles.tiempo.FechaHora";
            case BYTES:
                return "byte[]";
        }
        return null;
    }

    public Object convertirValor(Object valor) {
        switch (tipoDato) {
            case CADENA:
                return Conversion.aCadena(valor);
            case ENTERO:
                if (tieneOpciones()) {
                    String textoOpcion = Conversion.aCadena(valor);
                    int indiceOpcion = parsearOpcion(textoOpcion);
                    return new Long(indiceOpcion);
                }
                return Conversion.aEntero(valor);
            case REAL:
                return Conversion.aReal(valor);
            case BOOLEANO:
                if (tieneOpciones()) {
                    String textoOpcion = Conversion.aCadena(valor);
                    int indiceOpcion = parsearOpcion(textoOpcion);
                    switch (indiceOpcion) {
                        case 1:
                            return Boolean.FALSE;
                        case 2:
                            return Boolean.TRUE;
                        default:
                            return null;
                    }
                }
                return Conversion.aBooleano(valor);
            case FECHA:
                return Conversion.aFecha(valor);
            case FECHA_HORA:
                return Conversion.aFechaHora(valor);
            case BYTES:
                return Conversion.aByteArray(valor);
            default:
                return null;
        }
    }

    public void setOpciones(List<String> opciones) {
        this.opciones = opciones;
    }

    public List<String> getOpciones() {
        if (opciones != null) {
            return opciones;
        }
        opciones = new ArrayList<>();
        
        if (formato.startsWith("#")) {
            String cadenaOpciones;
            if (tieneEstilo(CAMPO_NO_NULO)) {
                cadenaOpciones = formato.substring(1);
            } else {
                cadenaOpciones = formato;
            }
            String[] arrayOpciones = cadenaOpciones.split("#");
            opciones = Arrays.asList(arrayOpciones);
        } else if (tipoDato == TipoDato.BOOLEANO) {
            String[] split = formato.split("/");
            if (!tieneEstilo(CAMPO_NO_NULO)) {
                opciones.add("");                
            }
            if (split.length > 0) {
                opciones.add(split[0]);                
            }
            if (split.length > 1) {
              opciones.add(split[1]);                
            }

        }
        
        return opciones;
    }

    public boolean tieneOpciones() {
        return getOpciones().size() > 0;
    }

    public int parsearOpcion(String texto) {
        if (!tieneOpciones()) {
            return 0;
        }
        for (int i = 0; i < opciones.size(); i++) {
            if (opciones.get(i).equals(texto)) {
                return i;
            }
        }
        return 0;
    }

    public String formatearOpcion(Integer indice) {

        if (!tieneOpciones()) {
            return "";
        }
        if (indice == null) {
            return opciones.get(0);
        }

        if (indice < 0 || indice >= opciones.size()) {
            return "";
        }
        return opciones.get(indice);
    }

    public String formatearValorParaVisualizacion(Object valor) {
        // Usar el formato del campo
        return valor.toString();
    }

    public String formatearValorParaEdicion(Object valor) {

        switch (tipoDato) {
            case CADENA:
                return Conversion.aCadena(valor);
            case ENTERO:
                if (tieneOpciones()) {
                    return formatearOpcion(Conversion.aEntero(valor).intValue());
                }
                return Conversion.aCadena(valor);
            case REAL:
                return Conversion.aCadena(valor);
            case BOOLEANO:
                Boolean b = Conversion.aBooleano(valor);
                if (tieneOpciones()) {
                    return formatearOpcion(b == null ? 0 : (b ? 1 : 2));
                }
                return Conversion.aCadena(b);
            case FECHA:
                return Conversion.aFecha(valor).getTexto();
            case FECHA_HORA:
                return Conversion.aFechaHora(valor).getTexto();
            case BYTES:
                return Base64.encode(Conversion.aByteArray(valor));
            default:
                return null;
        }
    }
    
}
