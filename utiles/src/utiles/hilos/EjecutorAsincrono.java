package utiles.hilos;

/**
 * Procesa en un hilo de ejecución paralela un proceso repetitivo con una latencia.
 * @author jberjano
 */
public class EjecutorAsincrono {
    protected boolean ejecutando;
    protected boolean cancelarEjecucion;
    protected int latencia = 200;
    protected boolean repetir;
    
    public interface Proceso {
        void procesar();
    }     
    
    public boolean ejecutarUnaVez(Proceso proceso) {
        repetir = false;
        return ejecutarHilo(proceso);
    }   
    
    public boolean ejecutar(Proceso proceso) {
        repetir = true;
        return ejecutarHilo(proceso);
    }   

    public void cancelar() {
        cancelarEjecucion = true;
    }

    public boolean isEjecutando() {
        return ejecutando;
    }

    public int getLatencia() {
        return latencia;
    }

    public void setLatencia(int latencia) {
        this.latencia = latencia;
    }

    private boolean ejecutarHilo(final Proceso proceso) {
        
        if (proceso == null) {
            return false;
        }
        if (ejecutando) {
            return false;
        }       
        cancelarEjecucion = false;
        ejecutando = true;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!cancelarEjecucion) {
                    proceso.procesar();
                    if (repetir) {
                        pausa(latencia);
                    } else {
                        cancelarEjecucion = true;
                    }
                }                
                ejecutando = false;
            }
        });
        thread.setName(proceso.toString());
        thread.start();
        return true;
    }   
    
    protected boolean pausa(long milisegundos) {
        long decisegundos = milisegundos / 100;
        long restoMs = milisegundos % 100;
        for (int i = 0; i < decisegundos; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                return false;
            }
            if (cancelarEjecucion) {
                return false;
            }
        } 
        if (restoMs > 0) {
             try {
                Thread.sleep(restoMs);
            } catch (InterruptedException ex) {
                return false;
            }
        }
        return true;
    }
}
