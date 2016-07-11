package com.jbp.ges.utilidades;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Jorge
 */
public class GestorSimbolos {
    
    private Map<String, Object> mapaSimbolos = new HashMap();

    public GestorSimbolos() {
    }
    
    public void asignarValorSimbolo(String simbolo, Object valor) {
        mapaSimbolos.put(simbolo.toLowerCase(), valor);
    }  
    
    public Object obtenerValorSimbolo(String simbolo) {
        Object valor = mapaSimbolos.get(simbolo.toLowerCase());
        return valor == null ? simbolo : valor;
         
    }

    public String sustituirSimbolos(String strTexto) {
        StringBuilder resultado = new StringBuilder();

        String[] partes = strTexto.split("}");
        for (int i = 0; i < partes.length; i++) {
            String parte = partes[i];
            String[] parteDividida = parte.split("\\{");
            if (parteDividida.length > 0) {
                resultado.append(parteDividida[0]);
            }
            if (parteDividida.length > 1) {
                String simboloFormato = "";
                if (parteDividida[1].startsWith("$")) {
                    simboloFormato = parteDividida[1].substring(1);
                }
                String[] partesSimboloFormato = simboloFormato.split(":");
                if (partesSimboloFormato.length > 0) {
                    String simbolo = partesSimboloFormato[0];
                    String formato = partesSimboloFormato.length > 1 ? partesSimboloFormato[1] : null;
                    Object valor = obtenerValorSimbolo(simbolo);
                    String valorFormatrado = UtilidadesFormato.formatear(valor, formato);
                    resultado.append(valorFormatrado);
                }
            }
        }
        return resultado.toString();
    }

}
