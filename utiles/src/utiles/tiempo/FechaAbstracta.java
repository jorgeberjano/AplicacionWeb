
package utiles.tiempo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Clase base para Fecha y FechaHora
 * @author jberjano
 */
public abstract class FechaAbstracta implements Serializable {
    
    private DateFormat formato;
    private Calendar calendar;
    protected boolean esPresente;
       
    public FechaAbstracta() {
        this.calendar = Calendar.getInstance();
        normalizar();
    }
    
    public FechaAbstracta(Calendar calendar) {
        this.calendar = calendar;
        normalizar();
    }

    public FechaAbstracta(Date date) {
        if (date == null) {
            this.calendar = null;
        } else {
            this.calendar = Calendar.getInstance();
            this.calendar.setTime(date);
            normalizar();
        }
    }
    
    public FechaAbstracta(long milisegundos) {  
        this.calendar = Calendar.getInstance();
        this.calendar.setTimeInMillis(milisegundos);
        normalizar();   
    }
    
    public boolean esPresente() {
        return esPresente;
    }
    
    protected abstract void normalizar();
    
    public abstract DateFormat getFormatoPorDefecto();

    public DateFormat getFormato() {
        return formato == null ? getFormatoPorDefecto() : formato;
    }

    public void setFormato(DateFormat formato) {
        this.formato = formato;
    }
    
    public boolean esValida() {
        return getCalendar() != null;
    }

    public boolean esNula() {
        return getCalendar() == null;
    }

    public boolean posteriorA(FechaAbstracta otraFecha) {
        if (esNula()) {
            return false;
        }
        return getCalendar().after(otraFecha.getCalendar());
    }

    public boolean esAnteriorA(FechaAbstracta otraFecha) {
        if (esNula()) {
            return false;
        }
        return getCalendar().before(otraFecha.getCalendar());
    }

    public boolean esPosteriorA(FechaAbstracta otraFecha) {
        if (esNula()) {
            return false;
        }
        return getCalendar().after(otraFecha.getCalendar());
    }

    public boolean esMismoDia(FechaAbstracta otraFecha) {
        if (otraFecha == null) {
            return false;
        }
        return esMismoDia(otraFecha.getCalendar());
    }
    
    public boolean esMismoDia(Calendar otroCalendar) {
        if (esNula() || otroCalendar == null) {
            return false;
        }

        return getCalendar().get(Calendar.DATE) == otroCalendar.get(Calendar.DATE)
                && getCalendar().get(Calendar.MONTH) == otroCalendar.get(Calendar.MONTH)
                && getCalendar().get(Calendar.YEAR) == otroCalendar.get(Calendar.YEAR);
    }
  
    public boolean esHoy() {
        return esMismoDia(Calendar.getInstance());
    }

    public int getDiaDelMes() {
        if (esNula()) {
            return 0;
        }
        return getCalendar().get(Calendar.DAY_OF_MONTH);
    }

    public boolean esPasado() {
        if (esNula()) {
            return false;
        }
        return getCalendar().compareTo(Calendar.getInstance()) < 0;
    }

    public void sumarDias(int dias) {
        if (esNula()) {
            return;
        }
        getCalendar().add(Calendar.DAY_OF_YEAR, dias);
    }

    public int getDiaDeLaSemana() {
        if (esNula()) {
            return 0;
        }
        return getCalendar().get(Calendar.DAY_OF_WEEK);
    }

    public void setDiaDeLaSemana(int numeroDiaDeLaSemana) {
        if (esNula()) {
            return;
        }
        getCalendar().set(Calendar.DAY_OF_WEEK, numeroDiaDeLaSemana);
    }

    public boolean esFuturo() {        
        if (esNula()) {
            return false;
        }
        return getCalendar().compareTo(Calendar.getInstance()) < 0;
    }

    public int diferenciaDias(FechaAbstracta otraFecha) {
        if (esNula()) {
            return 0;
        }
        long diff = getCalendar().getTime().getTime() - otraFecha.getCalendar().getTime().getTime();

        long dias = diff / (1000 * 60 * 60 * 24);
        return (int) dias;
    }

    public void setDiaDelMes(int diaDelMes) {
        if (esNula()) {
            return;
        }
        getCalendar().set(Calendar.DAY_OF_MONTH, diaDelMes);
    }

    public void sumarMeses(int meses) {
        if (esNula()) {
            return;
        }
        getCalendar().add(Calendar.MONTH, meses);
    }

    @Override
    public boolean equals(Object obj) {
        if (esNula()) {
            return false;
        }
        if (!(obj instanceof FechaAbstracta)) {
            return false;
        }
        if (obj == this) {
            return true;
        }       
        FechaAbstracta otraFecha = (FechaAbstracta) obj;
        return getCalendar().equals(otraFecha.getCalendar());
    }

    @Override
    public int hashCode() {
        return (int) this.getCalendar().getTimeInMillis();
    }


    public Calendar toCalendar() {
        return getCalendar();
    }

    public Date toDate() {
        if (esNula()) {
            return null;
        }
        return getCalendar().getTime();
    }
    
    
    public java.sql.Date toSqlDate() {
        if (esNula()) {
            return null;
        }
        return new java.sql.Date(getCalendar().getTimeInMillis());
    }

    public Timestamp toTimestamp() {
        if (esNula()) {
            return null;
        }
        return new Timestamp(getCalendar().getTimeInMillis());
    }
    
    public int compareTo(FechaAbstracta otraFecha) {
        if (esNula()) {
            return otraFecha.esNula() ? 0 : 1;
        }
        return getCalendar().compareTo(otraFecha.getCalendar());
    }

    public Calendar getCalendar() {
        if (esPresente) {
            return Calendar.getInstance();
        }
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
        normalizar();
    }
    
    public String formatear(String formato) {
        return formatear(new SimpleDateFormat(formato));
    } 
    
    public String formatear(DateFormat formateador) {
        if (esNula()) {
            return "";
        } else {
            return formateador.format(getCalendar().getTime());
        }
    } 
        
    @Override
    public String toString() {
        return formatear(getFormato());
    }
    
    public String getTexto() {
        return toString();
    }
    
    public void setTexto(String texto) {
        try {
            calendar.setTime(getFormato().parse(texto));
        } catch (ParseException ex) {
            calendar = null;
        }
    }
}
