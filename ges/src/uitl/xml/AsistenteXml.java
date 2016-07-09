package uitl.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 * Asistente para lectura y escritura de archivos XML
 *
 * @author Jorge
 */
public class AsistenteXml {

    private Document documento;
    private String mensajeError;

    public AsistenteXml() {
    }

    public AsistenteXml(Document documento) {
        this.documento = documento;
    }

    public boolean crearNuevo(String nombreTagRaiz) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            reportarError("No se ha encontrado nungun parseador xml", ex);
            return false;
        }
	documento = builder.newDocument();
        Element elementoRaiz = documento.createElement(nombreTagRaiz);
        documento.appendChild(elementoRaiz);
        return true;
    }

    public boolean recuperarDeArchivo(String nombre) {
        File file = new File(nombre);
        try {
            return recuperar(new FileInputStream(file));
        } catch (FileNotFoundException ex) {
            reportarError("No se ha podido recuperar el leer xml", ex);
            return false;
        }
    }

    public boolean recuperarDeRecurso(String nombre) {
        InputStream stream = getClass().getResourceAsStream(nombre);
        //InputStream stream = ClassLoader.getSystemResourceAsStream(nombre);
        if (stream == null) {
            reportarError("No se encuentra el recurso", null);
            return false;
        }
        return recuperar(stream);
    }
    
    public boolean recuperar(InputStream stream) {
        if (stream == null) {          
            return false;
        }
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            reportarError("No se ha encontrado nungún parseador xml", ex);
            return false;
        }        

        try {
            documento = builder.parse(stream);
        } catch (Exception ex) {
            reportarError("No se ha podido parsear el documento", ex);
            return false;
        }
        return true;
    }
    
    public boolean guardarEnArchivo(String nombre) {
        File file = new File(nombre);
        if (!file.exists()) {
            return false;
        }
        try {
            return guardar(new FileOutputStream(file));
        } catch (FileNotFoundException ex) {
            reportarError("No se ha podido guardar el documento xml", ex);
            return false;
        }
    }

    public boolean guardar(OutputStream stream) {
        Source source = new DOMSource(documento);
        Result result = new StreamResult(stream);
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.transform(source, result);
        } catch (TransformerException ex) {
            reportarError("No se ha podido gurdar el archivo xml", ex);
            return false;
        }
        return true;
    }

    public Element getElementoRaiz() {
        return documento.getDocumentElement();
    }

    public Element escribirElemento(Element elementoPadre, String nombreTag) {
        Element elemento = documento.createElement(nombreTag);
        elementoPadre.appendChild(elemento);
        return elemento;
    }

    public Element escribirElementoTexto(Element elementoPadre, String nombreTag, String texto) {
        Element elemento = escribirElemento(elementoPadre, nombreTag);
        Text nodoTexto = documento.createTextNode(texto);
        elemento.appendChild(nodoTexto);
        return elemento;
    }

    public void escribirAtributo(Element elemento, String nombreAtributo, String valor) {
        elemento.setAttribute(nombreAtributo, valor);
    }

    public List<Element> leerElementos(Element elementoPadre, String nombreTag) {
        List<Element> listaElementos = new ArrayList();

        NodeList lista = elementoPadre.getChildNodes();
        for (int i = 0; i < lista.getLength(); i++) {
            Node nodo = lista.item(i);
            if (nodo instanceof Element) {
                Element elemento = (Element) nodo;
                if (elemento.getTagName().equals(nombreTag)) {
                    listaElementos.add(elemento);
                }
            }
        }
        return listaElementos;
    }

    public String leerElementoTexto(Element elementoPadre, String nombreTag) {
        NodeList lista = elementoPadre.getChildNodes();
        for (int i = 0; i < lista.getLength(); i++) {
            Node nodo = lista.item(i);
            if (nodo instanceof Element) {
                Element elementoHijo = (Element) nodo;
                if (elementoHijo.getTagName().equals(nombreTag)) {
                    return elementoHijo.getTextContent();
                }
            }
        }

        return "";
    }

    public String leerAtributo(Element elemento, String nombreAtributo) {
        return elemento.getAttribute(nombreAtributo);
    }

    private void reportarError(String mensaje, Exception ex) {
        mensajeError = mensaje;
        if (ex != null) {
            mensaje += ". Causa: " + ex.getMessage();
        }
    }
}
