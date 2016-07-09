package com.jbp.ges.utilidades;

/**
 *
 * @author Jorge
 */
public class UtilidadesFormato {
   public static String formatear(Object valor, String formato) {
       // TODO: tener en cuenta el formato
       if (valor == null) {
           return "";
       }
       return valor.toString();
   } 
}
