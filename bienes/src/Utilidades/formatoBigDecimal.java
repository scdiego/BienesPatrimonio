/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

/**
 *
 * @author diego
 */
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
*
* @author Elio Rincón
* @email e1987ebrb@gmail.com
* @clase formatoBigDecimal
* @web 1987lineasdecodigo.sytes.net|calculosoftware.wordpress.com
*/
public class formatoBigDecimal{
/**
* Convierte un tipo de dato String a BigDecimal.
* Ideal para obtener el dato de un JTextField u otro componente y realizar las operaciones
* matemáticas sobre ese dato.
* @param num
* @return BigDecimal
*/
public static BigDecimal stringToBigDecimal(String num)
{
//se inicializa en 0
BigDecimal money = BigDecimal.ZERO;
//sino esta vacio entonces
if(!num.isEmpty())
{
/**
* primero elimina los puntos y luego remplaza las comas en puntos.
*/
String formatoValido = num.replace(".", "").replace(",", ".");
//System.out.println(formatoValido);
money = new BigDecimal(formatoValido);
}//if
return money;
}//metodo
/**
* Convierte un tipo de dato BigDecimal a String.
* Ideal para mostrar el dato BigDecimal en un JTextField u otro componente de texto.
* @param big
* @return String
*/
public static String bigDecimalToString(BigDecimal big)
{
    double datoDoubleD = 0;
    //se verifica que sean correctos los argumentos recibidos
    if(big != null)
    datoDoubleD = big.doubleValue();
    /**
    * Los # indican valores no obligatorios
    * Los 0 indican que si no hay valor se pondrá un cero
    */
    NumberFormat formatter = new DecimalFormat("#,#00.00");
    return formatter.format(datoDoubleD);
    }//metodo
}