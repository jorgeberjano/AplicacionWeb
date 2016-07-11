package com.jbp.aplicacionweb.dao;

import com.jbp.ges.entidad.CampoGes;
import com.jbp.ges.entidad.ConsultaGes;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Jorge
 */
public class MapaValores {

    private Map<String, Object> mapaValores = new HashMap();
    
    public MapaValores() {        
    }
    
    public boolean isEmpty() {
        return mapaValores.isEmpty();
    }
    
    public Set<String> getNombres() {
        return mapaValores.keySet();
    }

    public void set(String nombre, Object valor) {
        mapaValores.put(nombre, valor);
    }

    public Object get(String nombre) {
        return mapaValores.get(nombre);
    }
    
    public String toString() {
        return formatear("%", "=");
    }
        
    public void parsear(String cadena, ConsultaGes consulta, String separadorCampos, String separadorNombreValor) {
        String[] partes = cadena.split(separadorCampos);
        for (String parte : partes) {
            String[] asignacion = parte.split(separadorNombreValor);
            if (asignacion.length == 2) {
                String nombre = asignacion[0];
                CampoGes campo = consulta.getCampoPorNombre(nombre);                
                Object valor = campo.convertirValor(asignacion[1]);
                set(nombre, valor);
            }
        }
    }
    
    public String formatear(String separadorCampos, String separadorNombreValor) {
        StringBuilder builder = new StringBuilder();
        boolean vacia = true;
        for (String nombre : mapaValores.keySet()) {            
            Object valor = mapaValores.get(nombre);
            if (valor == null) {
                continue;
            }
            if (!vacia) {
                builder.append(separadorCampos);
            }
            builder.append(nombre);
            builder.append(separadorNombreValor);
            builder.append(valor.toString());
            vacia = false;
        }
        return builder.toString();
    }  
}
