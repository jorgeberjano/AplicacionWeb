package utiles.tiempo;

/**
 * Implementa la funcionalidad de un cronómetro para medir distancias de tiempo.
 * @author jberjano
 */
public class Cronometro {
    private long inicio;
    
    public Cronometro() {
        inicio();
    }
    
    public final void inicio() {
       inicio = System.nanoTime();
    }
    
    public long getSegundosTranscurridos() {       
        return getNanosegundosTranscurridos() / 1000000000;
    }
    
    public long getMilisegundosTranscurridos() {       
        return getNanosegundosTranscurridos() / 1000000;
    }
    
    public long getNanosegundosTranscurridos() {
        
        long ahora = System.nanoTime();
        long transcurrido = ahora - inicio;
        return transcurrido;
    }       
}
