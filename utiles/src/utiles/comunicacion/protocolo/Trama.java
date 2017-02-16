package utiles.comunicacion.protocolo;

import java.util.HashMap;
import java.util.Map;
import utiles.buffer.Buffer;
import utiles.conversion.Conversion;
import utiles.tiempo.Fecha;
import utiles.tiempo.FechaHora;

/**
 * Trama de comunicaciones consistente en una serie de entradas con una clave y 
 * un valor.
 * @author jberjano
 */
public class Trama {

    private final Map<String, Object> mapaValores = new HashMap();
    private final String separadorClaveValor = "~";
    private final String separadorEntradas = "|";

    public Trama() {
    }
    
    public Trama(String texto) {
        parsear(texto);
    }
    
    public void set(String clave, Object valor) {
        mapaValores.put(clave, valor);
    }
    
    public String getString(String clave) {
        Object valor = mapaValores.get(clave);
        return Conversion.toString(valor);
    }
    
    public Integer getInteger(String clave) {
        Object valor = mapaValores.get(clave);
        return Conversion.toInteger(valor);
    }
    
    public Long getLong(String clave) {
        Object valor = mapaValores.get(clave);
        return Conversion.toLong(valor);
    }

    public Double getDounble(String clave) {
        Object valor = mapaValores.get(clave);
        return Conversion.toDouble(valor);
    }
    
    public Boolean getBoolean(String clave) {
        Object valor = mapaValores.get(clave);
        return Conversion.toBoolean(valor);
    }
    
    public Fecha getFecha(String clave) {
        Object valor = mapaValores.get(clave);
        return Conversion.toFecha(valor);
    }
    
    public FechaHora getFechaHora(String clave) {
        Object valor = mapaValores.get(clave);
        return Conversion.toFechaHora(valor);
    }
    
    public byte[] getBytes(String clave) {
        Object valor = mapaValores.get(clave);
        return Conversion.toByteArray(valor);
    }
    
    public Buffer getBuffer(String clave) {
        Object valor = mapaValores.get(clave);
        return Conversion.toBuffer(valor);
    }
    
//    static boolean esTrama(byte[] bytes) {
//        Buffer buffer = new Buffer(bytes);        
//        return buffer.getPrimerByte() == Comunicacion.ETX &&
//                buffer.getPrimerByte() == Comunicacion.STX;
//    }

    
    public final void parsear(String texto) {
        String[] entradas = texto.split("\\" + separadorEntradas);
        for (String entrada : entradas) {
            String claveValor[] = entrada.split("\\" + separadorClaveValor);
            if (claveValor.length >= 2) {
                set(claveValor[0], claveValor[1]);
            }
        }
    }
   
//    public byte[] getBytesTrama() {
//        //StringBuilder builder = new StringBuilder();
//        
//                ByteArrayOutputStream out = new ByteArrayOutputStream();
//        out.write(Comunicacion.STX);
//        out.write
//        out.write(Comunicacion.STX);
//        boolean primerValor = true;
//        for (String clave : mapaValores.keySet()) {
//            String valor = getString(clave);
//            if (valor == null) {
//                continue;
//            }
//            if (!primerValor) {
//                out.write(separadorEntradas);
//            }            
//            out.write(clave.getBytes("utf8"));
//            out.write(separadorClaveValor);
//            builder.append(valor);
//            primerValor = false;
//        }       
//        out.write(Comunicacion.ETX);
//        return out.toByteArray();
//    }
    
    public String toString() {
        StringBuilder builder = new StringBuilder();       
        //builder.append((char) Comunicacion.STX);
        for (String clave : mapaValores.keySet()) {
            String valor = getString(clave);
            if (valor == null) {
                continue;
            }
            if (builder.length() > 0) {
                builder.append(separadorEntradas);
            }
            builder.append(clave);
            builder.append(separadorClaveValor);
            builder.append(valor);
        }       
        //builder.append((char) Comunicacion.ETX);
        return builder.toString();
    }

}
