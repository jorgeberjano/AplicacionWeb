package com.jbp.ges.serializacion;

import com.jbp.ges.entidad.CampoGes;
import com.jbp.ges.entidad.ConsultaGes;
import com.jbp.ges.entidad.Ges;
import java.util.List;
import org.w3c.dom.Element;
import uitl.xml.AsistenteXml;
import utiles.reflexion.Reflexion;

/**
 *
 * @author Jorge
 */
public class SerializadorGes {

    private boolean bSerializar;
    private AsistenteXml xml = new AsistenteXml();

//    public static void main(String[] args) {
//        SerializadorGes serializador = new SerializadorGes();
//
//        Ges gestor = serializador.deserializar("xml/bp.xml.ges");
//        serializador.serializar("d:/ges.xml", gestor);
//    }

    public boolean serializar(String nombreArchivo, Ges gestor) {
        bSerializar = true;
        xml.crearNuevo("raiz");
        procesar(gestor);
        return xml.guardarEnArchivo(nombreArchivo);
    }

    public Ges deserializar(String nombreArchivo) {
        Ges gestor = new Ges();
        bSerializar = false;
        xml.recuperarDeArchivo(nombreArchivo);
        procesar(gestor);
        return gestor;
    }
    
    public Ges deserializarRecurso(String nombre) {
        Ges gestor = new Ges();
        bSerializar = false;
        xml.recuperarDeRecurso(nombre);
        procesar(gestor);
        return gestor;
    }


    void procesar(Ges gestor) {
        procesarAtributo(xml.getElementoRaiz(), "version", gestor);
        procesarAtributo(xml.getElementoRaiz(), "nombreBaseDatos", gestor);
        procesarAtributo(xml.getElementoRaiz(), "estilo", gestor);
        procesarConsultas(xml.getElementoRaiz(), "consultaPantalla", gestor.getListaConsultasPantalla());
        procesarConsultas(xml.getElementoRaiz(), "consultaImpresora", gestor.getListaConsultasImpresora());
    }

    void procesarAtributo(Element elementoPadre, String nombreTag, Object objeto) {
        if (bSerializar) {
            Object obj = Reflexion.obtenerValorAtributoSimple(objeto, nombreTag);
            String texto = obj.toString().replace("\r\n", "\n");
            xml.escribirElementoTexto(elementoPadre, nombreTag, texto);
        } else {
            String texto = xml.leerElementoTexto(elementoPadre, nombreTag);
            //System.out.println(nombreTag + "=" + texto);
            Reflexion.asignarValorAtributoSimple(objeto, nombreTag, texto);
        }
    }

    void procesarConsultas(Element elementoPadre, String nombreTag, List<ConsultaGes> listaConsultas) {

        if (bSerializar) {
            for (ConsultaGes consulta : listaConsultas) {
                Element elemento = xml.escribirElemento(elementoPadre, nombreTag);
                procesarConsulta(elemento, consulta);
            }
        } else {
            List<Element> elementos = xml.leerElementos(elementoPadre, nombreTag);
            for (Element elemento : elementos) {
                ConsultaGes consulta = new ConsultaGes();
                procesarConsulta(elemento, consulta);
                listaConsultas.add(consulta);
            }
        }        
    }

    void procesarConsulta(Element elementoPadre, ConsultaGes consulta) {
        procesarAtributo(elementoPadre, "nombre", consulta);
        procesarAtributo(elementoPadre, "tabla", consulta);
        procesarAtributo(elementoPadre, "sql", consulta);
        procesarAtributo(elementoPadre, "nombreSubconsultas", consulta);
        procesarAtributo(elementoPadre, "camposFiltroPrevio", consulta);
        procesarAtributo(elementoPadre, "imagen", consulta);
        procesarAtributo(elementoPadre, "estilo", consulta);
        procesarAtributo(elementoPadre, "nombreEnSingular", consulta);
        procesarAtributo(elementoPadre, "camposPorDefecto", consulta);
        procesarAtributo(elementoPadre, "valoresPorDefecto", consulta);
        procesarAtributo(elementoPadre, "estiloImpresion", consulta);
        procesarAtributo(elementoPadre, "tituloImpresion", consulta);
        procesarAtributo(elementoPadre, "subtituloImpresion", consulta);
        procesarAtributo(elementoPadre, "encabezadoImpresion", consulta);
        procesarAtributo(elementoPadre, "pieImpresion", consulta);
        procesarAtributo(elementoPadre, "textoInicialImpresion", consulta);
        procesarAtributo(elementoPadre, "textoFinalImpresion", consulta);
        procesarAtributo(elementoPadre, "consultasSeleccionPrevia", consulta);

        procesarCampos(elementoPadre, "campo", consulta.getListaCampos());

//        consulta.CrearListaRelaciones();
//        consulta.CrearListaAgregados();
    }

    void procesarCampos(Element elementoPadre, String nombreTag, List<CampoGes> listaCampos) {        
        if (bSerializar) {

            for (CampoGes campo : listaCampos) {
                Element elemento = xml.escribirElemento(elementoPadre, nombreTag);
                procesarCampo(elemento, campo);
            }
        } else {
            List<Element> elementos = xml.leerElementos(elementoPadre, nombreTag);
            int indice = 0;
            for (Element elemento : elementos) {                
                CampoGes campo = new CampoGes();
                procesarCampo(elemento, campo);
                campo.setIndice(indice++);
                listaCampos.add(campo);
            }
        }
    }

    void procesarCampo(Element elementoPadre, CampoGes campo) {
        procesarAtributo(elementoPadre, "nombre", campo);
        procesarAtributo(elementoPadre, "tabla", campo);
        procesarAtributo(elementoPadre, "campo", campo);
        procesarAtributo(elementoPadre, "titulo", campo);
        procesarAtributo(elementoPadre, "tipoDato", campo);
        procesarAtributo(elementoPadre, "tipoRol", campo);
        procesarAtributo(elementoPadre, "alineacion", campo);
        procesarAtributo(elementoPadre, "longitud", campo);
        procesarAtributo(elementoPadre, "formato", campo);
        procesarAtributo(elementoPadre, "decimales", campo);
        procesarAtributo(elementoPadre, "unidad", campo);
        procesarAtributo(elementoPadre, "estilo", campo);
        procesarAtributo(elementoPadre, "expresionGT", campo);
        procesarAtributo(elementoPadre, "formatoGT", campo);
        procesarAtributo(elementoPadre, "consultaSeleccion", campo);
        procesarAtributo(elementoPadre, "valorNulo", campo);
        procesarAtributo(elementoPadre, "nombreCampoRelacion", campo);
        procesarAtributo(elementoPadre, "valorPorDefecto", campo);
        procesarAtributo(elementoPadre, "tipoDato", campo);
    }

//    private void reportarError(String mensaje, Exception ex) {
//        mensajeError = mensaje;
//        if (ex != null) {
//            mensaje += ". Causa: " + ex.getMessage();
//        }
//    }

}
