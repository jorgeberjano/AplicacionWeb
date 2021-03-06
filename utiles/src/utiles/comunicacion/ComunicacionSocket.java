package utiles.comunicacion;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 * Comunicación basada en un socket.
 * @author jberjano
 */
public class ComunicacionSocket implements Comunicacion {

    private Comunicacion.Listener listener;
    private Socket socket;
    private String host;
    private int puerto;
    private int timeout = 1000;
    private boolean noMostrarErrorConexion;
    private boolean forzarApertura;

    public ComunicacionSocket() {
        socket = null;
    }

    public ComunicacionSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public boolean abrir(String nombre) {
        if (socket != null) {
            return false;
        }

        String[] partes = nombre.split(":");
        if (partes.length < 2) {
            return false;
        }
        return abrir(partes[0], Integer.parseInt(partes[1]));
    }

    public boolean abrir(String host, int puerto, int timeout) {
        this.timeout = timeout;
        return abrir(host, puerto);
    }
    
    public boolean abrir(String host, int puerto) {
        this.host = host;
        this.puerto = puerto;
        try {
            socket = new Socket(host, puerto);
            socket.setSoTimeout(timeout);
        } catch (IOException ex) {
            if (!noMostrarErrorConexion) {
                reportarError("No se ha podido abrir el socket " + host + ":" + puerto , ex);
            }
            noMostrarErrorConexion = true;
            return false;
        }
        return true;
    }

    @Override
    public boolean abrir() {
        boolean ok = abrir(host, puerto);
        forzarApertura = ok;
        if (ok && listener != null) {
            listener.conectado();
        }       
        return ok;
    }

    @Override
    public void cerrar() {
        noMostrarErrorConexion = false;
        forzarApertura = false;
        
        if (socket == null) {
            return;
        }

        try {
            socket.close();
            socket = null;
        } catch (IOException ex) {
            reportarError("No se ha podido cerrar el socket " + host + ":" + puerto, ex);
        }
    }

    @Override
    public boolean enviar(byte[] bytes) {
        if (socket == null) {
            return false;
        }
        try {
            OutputStream out = socket.getOutputStream();
            out.write(bytes);
            return true;
        } catch (IOException ex) {
            desconectado();
            return false;
        } catch (Exception ex) {
            desconectado();
            return false;
        }
    }

    @Override
    public void procesarRecepcion() {
        if (listener == null) {
            return;
        }
        byte[] bytesRecibidos = recibir();
        if (bytesRecibidos != null) {
            listener.recibido(bytesRecibidos);
        }
    }

    @Override
    public boolean hayRecepcion() {
        if (socket == null) {
            return false;
        }

        try {
            InputStream in = socket.getInputStream();
            return in.available() > 0;
        } catch (IOException ex) {
            return false;
        }
    }
    
    @Override
    public byte[] recibir() {
        if (socket == null) {
            return null;
        }
        try {
            InputStream in = socket.getInputStream();
            int disponibles = in.available();
            if (disponibles == 0) {
                disponibles = 1;
            }

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            while (disponibles > 0) {
                byte[] array = new byte[disponibles];
                int leidos = in.read(array);
                if (leidos < 0) {
                    // Aqui de detecta la desconexion cuando el otro extremo cierra el socket
                    desconectado();
                    return null;
                }
                buffer.write(array);
                disponibles = in.available();
            }

            return buffer.toByteArray();
        }  catch (SocketTimeoutException ex) {
            // Timeout de recepcion, es normal
            return null;
        } catch (SocketException ex) {
            // Aqui de detecta la desconexion cuando el otro extremo muere
            desconectado();
            return null;
        } catch (IOException ex) {
            // TODO: ver si es conveniente la dexconexion aqui
            desconectado();
            return null;            
        }
    }

    private void desconectado() {
        cerrar();
        if (listener != null) {
            listener.desconectado();
        }
    }

    @Override
    public void limpiarBufferEntrada() {
        if (socket == null) {
            return;
        }
        InputStream in;
        try {
            in = socket.getInputStream();
            if (in.available() > 0) {
                in.skip(in.available());
            }

        } catch (IOException ex) {
            reportarError("No se ha podido limpiar el buffer de entrads", ex);
        }
    }

    private void reportarError(String mensaje, Throwable ex) {
        if (listener != null) {
            listener.error(mensaje, ex);
        }
    }

    @Override
    public boolean estaAbierta() {
        if (socket == null) {
            return false;
        }

        boolean conectado = socket.isConnected();
        boolean cerrado = socket.isClosed();
        return conectado && !cerrado;
    }

    @Override
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    @Override
    public boolean isForzarApertura() {
        return forzarApertura && host != null;
    }

    @Override
    public String getDireccion() {
        if (socket == null) {
            return "----";
        } else {
            return socket.getInetAddress().toString();
        }
    }
}
