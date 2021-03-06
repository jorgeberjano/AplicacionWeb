package utiles.swing.tabla;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Comparator;
import utiles.reflexion.Reflexion;
import utiles.tiempo.FechaAbstracta;

/**
 * Comparador de atributos de un objeto para ordenación de listas de entidades.
 *
 * @author jberjano
 */
public class ComparadorAtributo implements Comparator {

    private String nombreAtributo;
    private boolean invertirResultado = false;
    
    public ComparadorAtributo() {
        
    }
            
    public ComparadorAtributo(String nombreAtributo) {
        this.nombreAtributo = nombreAtributo;
    }
    
    public ComparadorAtributo(String nombreAtributo, boolean invertirResultado) {
        this.nombreAtributo = nombreAtributo;
        this.invertirResultado = invertirResultado;
    }

    @Override
    public int compare(Object objeto1, Object objeto2) {
        
        Object valor1 = obtenerValorAtributo(objeto1);
        Object valor2 = obtenerValorAtributo(objeto2);
        
        if (valor1 == null || valor2 == null) {
            return 0;
        }
        
        if (valor1.getClass() != valor2.getClass()) {
            valor1 = valor1.toString();            
            valor2 = valor2.toString();
        }
  
        int diferencia = 0;
        if (valor1 instanceof Integer) {
            diferencia = (Integer) valor1 - (Integer) valor2;
        } else if (valor1 instanceof Long) {
            diferencia = (int) ((Long) valor1 - (Long) valor2);
        } else if (valor1 instanceof Float) {
            diferencia = (int) ((Float) valor1 - (Float) valor2);
        } else if (valor1 instanceof Double) {
            diferencia = (int) ((Double) valor1 - (Double) valor2);
        } else if (valor1 instanceof FechaAbstracta) {
            diferencia = ((FechaAbstracta) valor1).compareTo((FechaAbstracta) valor2);
        } else {
            diferencia = valor1.toString().compareTo(valor2.toString());
        }
        
        if (invertirResultado) {
            return diferencia;
        } else {
            return -diferencia;            
        }
    }  

    private Object obtenerValorAtributo(Object objeto) {
        return Reflexion.obtenerValorAtributo(objeto, nombreAtributo);
    }

}
