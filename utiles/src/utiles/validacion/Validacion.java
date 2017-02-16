/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utiles.validacion;

/**
 *
 * @author jfjara
 */
public class Validacion {

    public static boolean isNIF(String documento) {
        
        if (documento == null) {
            return false;
        }
        
        if (documento.length() != 9) {
            return false;
        }
        
        if (!isNumero(documento.substring(0, 7))) {
            return false;
        }
        
        if (isNumero(documento.substring(7, 8))) {
            return false;
        }
        
        return true;
    }

    public static boolean isNumero(String cadena) {
        
        try {
            Integer.parseInt(cadena);
        } catch (Exception e) {
            return false;
        }
        return true;        
        
    }
    
}
