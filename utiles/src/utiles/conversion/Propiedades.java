
package utiles.conversion;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Utilidad para la lectura de propiedades con conversión de datos.
 * @author jberjano
 */
public class Propiedades {
    private final Properties properties;
    private final String namespace;    
    
    public Propiedades() {
        this.properties = new Properties();
        this.namespace = "";
    }
            
    public Propiedades(Properties properties) {
        this.properties = properties;
        this.namespace = "";
    }
    
    public Propiedades(Properties properties, String namespace) {
        this.properties = properties;
        this.namespace = namespace.endsWith(".") ? namespace : namespace + ".";
    }
    
    public Propiedades(Propiedades propiedades, String namespace) {
        this(propiedades.getProperties(), namespace);
    }
    
    public void cargar(InputStream inputStream) throws IOException {
        properties.load(inputStream);
    }
    
    public void guardar(OutputStream outputStream) throws IOException {
        properties.store(outputStream, "");
    }    

    public Properties getProperties() {
        return properties;
    }
    
    public String getString(String nombre) {
        return getString(nombre, "");
    }
    
    public String getString(String nombre, String valorPorDefecto) {
        return properties.getProperty(namespace + nombre, valorPorDefecto);
    }
    
    public void setString(String nombre, String valor) {
        properties.setProperty(namespace + nombre, valor);        
    }
    
    public Integer getInteger(String nombre, Integer valorPorDefecto) {
        
        String valorCadena = properties.getProperty(namespace + nombre);
        Integer valor = Conversion.toInteger(valorCadena);
        return valor != null ? valor : valorPorDefecto;        
    }
    
    public void setInteger(String nombre, Integer valor) {
        properties.setProperty(namespace + nombre, Integer.toString(valor));        
    }
    
    public Long getLong(String nombre, Long valorPorDefecto) {
        
        String valorCadena = properties.getProperty(namespace + nombre);
        Long valor = Conversion.toLong(valorCadena);
        return valor != null ? valor : valorPorDefecto;        
    }
    
    public void setLong(String nombre, Long valor) {
        properties.setProperty(namespace + nombre, Long.toString(valor));        
    }
    
    public Boolean getBoolean(String nombre, Boolean valorPorDefecto) {
        
        String valorCadena = properties.getProperty(namespace + nombre);
        Boolean valor = Conversion.toBoolean(valorCadena);
        return valor != null ? valor : valorPorDefecto;        
    }
    
    public void setBoolean(String nombre, boolean valor) {
        properties.setProperty(namespace + nombre, valor ? "si" : "no");
    }
    
    public Double getDouble(String nombre, Double valorPorDefecto) {
        
        String valorCadena = properties.getProperty(namespace + nombre);
        Double valor = Conversion.toDouble(valorCadena);
        return valor != null ? valor : valorPorDefecto;        
    }
    
    public void setDouble(String nombre, Double valor) {
        properties.setProperty(namespace + nombre, Double.toString(valor));        
    }
    
}
