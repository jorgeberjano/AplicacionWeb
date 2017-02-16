package utiles.comunicacion.protocolo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import utiles.comunicacion.Comunicacion;
import utiles.comunicacion.ProcesoComunicacion;
import utiles.tiempo.Cronometro;

/**
 *
 * @author jberjano
 */
public class Comunicador {

    private Comunicacion comunicacion;
    private Listener listener;
    //private StringBuilder builderTrama;
    private ByteArrayOutputStream constructorTramaEntrante;
    private ProcesoComunicacion proceso;
    private Boolean esperandoTrama = false;

    public interface Listener {

        void conectado();

        void tramaRecibida(Trama trama);

        void error(String mensaje, Throwable ex);

        void desconectado();
    }

    public Comunicador() {
    }

    public Comunicador(Comunicacion comunicacion) {
        this.comunicacion = comunicacion;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void iniciar(Comunicacion comunicacion) {
        this.comunicacion = comunicacion;
        iniciar();
    }

    public void iniciar() {
        if (comunicacion == null) {
            return;
        }

        comunicacion.setListener(new Comunicacion.Listener() {

            @Override
            public void conectado() {
                if (listener != null) {
                    listener.conectado();
                }
            }

            @Override
            public void recibido(byte[] bytes) {
                procesarRecepcion(bytes);
            }

            @Override
            public void error(String mensaje, Throwable ex) {
                if (listener != null) {
                    listener.error(mensaje, ex);
                }
            }

            @Override
            public void desconectado() {
                comunicacion.cerrar();
                if (listener != null) {
                    listener.desconectado();
                }

            }
        });
        proceso = new ProcesoComunicacion(comunicacion);
        proceso.iniciar();
    }

    public void finalizar() {
        if (proceso != null) {
            proceso.finalizar();
        }
    }

    public void enviar(byte[] bytes) {

        comunicacion.enviar(bytes);
    }

    public void enviar(Trama trama) {

        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.write(Comunicacion.STX);
        try {
            out.write(trama.toString().getBytes(Charset.forName("UTF-8")));
        } catch (IOException ex) {            
        }
        out.write(Comunicacion.ETX);
                
        comunicacion.enviar(out.toByteArray());
    }

    public Trama esperarTrama(int timeout) {

        if (!comunicacion.estaAbierta()) {
            return null;
        }

        Cronometro crono = new Cronometro();
        while (crono.getMilisegundosTranscurridos() < timeout) {
            byte[] bytes = comunicacion.recibir();
            Trama trama = procesarRecepcion(bytes);
            if (trama != null) {
                return trama;
            }
        }
        return null;
    }
    
    private Trama procesarRecepcion(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        for (byte b : bytes) {
            if (constructorTramaEntrante == null) {
                if (b == Comunicacion.STX) {
                    constructorTramaEntrante = new ByteArrayOutputStream();
                }
            } else if (b == Comunicacion.ETX) {
                Trama tramaRecibida = null;
                try {
                    tramaRecibida = tramaRecibida(constructorTramaEntrante.toString("UTF-8"));
                } catch (UnsupportedEncodingException ex) {
                }
                constructorTramaEntrante = null;
                return tramaRecibida;
            } else {
                constructorTramaEntrante.write(b);
            }
        }
        return null;
    }

//    private Trama procesarRecepcion(byte[] bytes) {
//        if (bytes == null) {
//            return null;
//        }
//        for (byte b : bytes) {
//            if (builderTrama == null) {
//                if (b == Comunicacion.STX) {
//                    builderTrama = new StringBuilder();
//                }
//            } else if (b == Comunicacion.ETX) {
//                Trama tramaRecibida = tramaRecibida(builderTrama.toString());
//                builderTrama = null;
//                return tramaRecibida;
//            } else {
//                builderTrama.append((char) b);
//            }
//        }
//        return null;
//    }

    private Trama tramaRecibida(String texto) {

        Trama tramaRecibida = new Trama(texto);
        if (listener != null) {
            listener.tramaRecibida(tramaRecibida);
        }
        return tramaRecibida;
    }

    public boolean isComunicacionAbierta() {
        return comunicacion.estaAbierta();
    }

    public String getDireccion() {
        return comunicacion.getDireccion();
    }
}
