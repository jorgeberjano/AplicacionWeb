package utiles.comunicacion;

import utiles.hilos.EjecutorAsincrono;

/**
 * Proceso que se repite con una latencia determinada. Comprueba periodicamente 
 * si se produce alguna recepción a traves de la comunicación.
 * @author jberjano
 */
public class ProcesoComunicacion implements EjecutorAsincrono.Proceso {

    private final Comunicacion comunicacion;
    private EjecutorAsincrono ejecutor;   
    private String nombre = "Comunicacion";
    //private int tiempoReintentoApertura = 1000;
    
    public ProcesoComunicacion(Comunicacion comunicacion) {
        this.comunicacion = comunicacion;
        ejecutor = new EjecutorAsincrono();
    }
    
    public void iniciar() {        
        ejecutor.ejecutar(this);
    }
    
    public void finalizar() {
        ejecutor.cancelar();
    }   
   
    @Override
    public void procesar() {
        if (comunicacion == null) {
            return;
        }
        
        if (comunicacion.estaAbierta()) {
            comunicacion.procesarRecepcion();
        } else if (comunicacion.isForzarApertura()) {
            comunicacion.abrir();
        }
    }   

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }

}
