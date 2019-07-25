/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Comparadores;
import java.util.Comparator;
import Negocio.Bien;

/**
 *
 * @author Usuario
 */
public class BienComparador implements Comparator<Bien>{
    @Override
    public int compare(Bien b1, Bien b2){
        return (b1.getNroInventario() < b2.getNroInventario()) ? (-1) : 1;
                
    }
}
/**
public class CodAreaComparator implements Comparator<NumeroTelefonico> {

    @Override
    public int compare(NumeroTelefonico o1, NumeroTelefonico o2) {
        return (o1.getCodArea() < o2.getCodArea()) ? (-1) : 1;
    }
   
}
*/