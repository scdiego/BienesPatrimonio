/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
/**
 *
 * @author demian
 */
public class FechaHora {

    public FechaHora() {
    }

    /**
     * Devuelve la fecha actual del sistema.
     * @return 
     */
    public Date fechaActual(){
        return Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));
    }
    
    /**
     * Convierte a String una fecha.
     * @param valor
     * @return 
     */
    public String DateToString(Date valor){
        String [] cadena = valor.toString().split("-");
        
        return cadena[2] + "/" + cadena[1] + "/" + cadena[0];
    }
    
    /**
     * Convierto una fecha pasada como String a Date.
     * @param valor
     * @return 
     */
    public Date StringToDate(String valor){
        Date retorno = null;
        
        String [] cadena = valor.split("/");
        
        try{
            retorno = Date.valueOf(cadena[2] + "-" + cadena[1] + "-" + cadena[0]);
        }catch(Exception ex){
            ex = new Exception();
        }
        
        return retorno;
    }
    
    /**
     * Devuelve la hora actual del sistema.
     * @return 
     */
    public Time horaActual(){
        String hora = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
        
        return Time.valueOf(hora);
    }
    
    /**
     * Convierte en String un dato tipo Time.
     * @param valor
     * @return 
     */
    public Time StringToTime(String valor){
        //String [] cadena = valor.split(":");
        Time retorno = null; 
        
        try{
            retorno = Time.valueOf(valor + ":00");
        }catch(Exception ex){
            ex = new Exception();
        }
        
        return retorno;
    }  
    
    /**
     * Convierte en Time un dato tipo String.
     * @param valor
     * @return 
     */
    public String TimeToString(Time valor){
        String [] cadena = valor.toString().split(":");
        return cadena[0] + ":" + cadena[1];
    }
    
    /**
     * Devuelve la suma de dos horas.
     * @param hora1
     * @param hora2
     * @return 
     */
    public Time sumarHoras(Time hora1, Time hora2){
        Time retorno = null;

        int hH, mM;
        
        /**
         * Convierto en cadena la hora pasada como argumento.
         */        
        String [] cadHora1 = hora1.toString().split(":");
        String [] cadHora2 = hora2.toString().split(":");
        
        try{
            /**
             * Sumo los minutos.
             */
            mM = Integer.parseInt(cadHora1[1]) + Integer.parseInt(cadHora2[1]);

            /**
             * Sumo las horas.
             */
            hH = Integer.parseInt(cadHora1[0]) + Integer.parseInt(cadHora2[0]);
            
            retorno = new Time(hH, mM, 0);
        }catch(Exception ex){
            ex = new Exception();
        }

        return retorno;
    }
    
    /**
     * Devuelve la resta de dos horas.
     * @param hora1
     * @param hora2
     * @return 
     */
    public Time restarHoras(Time hora1, Time hora2){
        Time retorno = null;

        int hH, mM;
        
        /**
         * Convierto en cadena la hora pasada como argumento.
         */        
        String [] cadHora1 = hora1.toString().split(":");
        String [] cadHora2 = hora2.toString().split(":");
        
        try{
            
            /**
             * Sumo los minutos.
             */
            mM = Integer.parseInt(cadHora1[1]) - Integer.parseInt(cadHora2[1]);

            /**
             * Sumo las horas.
             */
            hH = Integer.parseInt(cadHora1[0]) - Integer.parseInt(cadHora2[0]);
            
            retorno = new Time(hH, mM, 0);
        }catch(Exception ex){
            ex = new Exception();
        }

        return retorno;        
    }
    
    /**
     * Permite obtener el dia a partir de una fecha determinada
     * @param fecha
     * @return 
     */
    public int obtenerDia(Date fecha){
        return Integer.parseInt(new SimpleDateFormat("dd").format(fecha));
    }
    
     /**
     * Permite obtener el anio a partir de una fecha determinada
     * @param fecha
     * @return 
     */
    public int obtenerAnio(Date fecha){
        return Integer.parseInt(new SimpleDateFormat("yyyy").format(fecha.getTime()));
    }

    /**
     * Permite obtener el mes a partir de una fecha determinada
     * @param fecha
     * @return 
     */
    public int obtenerMes(Date fecha){
        return Integer.parseInt(new SimpleDateFormat("MM").format(fecha.getTime()));
    }
        
    /**
     * Verifico que la fecha a comparar sea mayor a la actual.
     * @param fechaActual
     * @param fechaComparar
     * @return 
     */
    public boolean fechaPosterior(Date fechaActual, Date fechaComparar){
        return (fechaActual.compareTo(fechaComparar) < 0) ? true : false;
    }
    
    public int diferenciaFechas(String fecha1, String fecha2) throws ParseException{
          
      DateFormat formatter;
      formatter = new SimpleDateFormat("dd/MM/yyyy");
      FechaHora fecha = new FechaHora();
      // you can change format of date
      
       
      java.util.Date date = formatter.parse(fecha1);
      java.util.Date date2 = formatter.parse(fecha2);
      java.sql.Timestamp timeStampDate1 = new Timestamp(date.getTime());
      java.sql.Timestamp timeStampDate2= new Timestamp(date2.getTime());


      
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

      java.util.Date fechaInicial=dateFormat.parse(timeStampDate1.toString());
      java.util.Date fechaFinal=dateFormat.parse(timeStampDate2.toString() );
      int dias=(int) ((fechaFinal.getTime()-fechaInicial.getTime())/86400000);
      return dias;
      
    }
    
    public boolean validarFecha(String fecha)  throws ParseException {

            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
            formatoFecha.setLenient(false);
            formatoFecha.parse(fecha);


        return true;

    }
}
