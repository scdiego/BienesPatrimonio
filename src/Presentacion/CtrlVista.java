/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentacion;

import Negocio.Asignacion;
import java.util.Map;


/**
 *
 * @author diego
 */
import Negocio.Bien;
import Negocio.Inventario;
import Negocio.Responsable;
import Negocio.Sector;
import Negocio.Usuario;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
public class CtrlVista {
    
    private static Inventario unInventario;
     
    public CtrlVista() {
         unInventario = new Inventario();
     } 
    public static Map<Integer,Bien> listadeBienesActivos(){
         return unInventario.bienesActivos();
    }
    public static Map<Integer,Bien> listadeBienes(){
        return unInventario.getBienes();
    }
    
    public static Map<Integer,Responsable> listaDeResponsables(){
         return unInventario.getResponsables();
     }
    public static void borrarResponsable(Responsable unResponsable) throws Exception{
        try{
            unInventario.borrarResponsable(unResponsable);
        }catch(Exception ex){
            ex = new Exception();
        }
    }    
    public static void crearResponsable(String unNombre,String unDni,String unCargo,Sector unSector){
        unInventario.agregarResponsable(unNombre, unDni,unCargo,unSector);
    }
    public static void modificarResponsable(Responsable unResponsable,String unNombre, String unDni,String unCargo, Sector unSector){
        unInventario.modificarResponsable(unResponsable, unNombre, unDni,unCargo,unSector);
    }
    public Responsable obtenerResponsableDni(String unDni){
         return unInventario.responsableXDni(unDni);
    }
    
    
    public static Map<Integer,Sector> listaDeSectores(){
        return unInventario.getSectoresActivos();
    }
    public static void crearSector(String unNombre){
        unInventario.agregarSector(unNombre);
    }
    public static void modificarSector (Sector unSector, String unNombre){
        unInventario.modificarSector(unSector,unNombre);
    }
    /*public static void borrarSector(Sector unSector){
        unInventario.quitarSector(unSector);
    }*/
    /*
    public static void borrarBien(Bien unBien){
        unInventario.
    }
*/
    public Sector obtenerSectorNombre(String unNombre){
        return this.unInventario.obtenerSectorNombre(unNombre);
    }
    public Responsable obtenerResponsableNombre(String unNombre){
        return this.unInventario.responsableXNombre(unNombre);
    }
    
    /*public static void crearBien(String codigo, Integer nroInventario, String descripcion, Date fechaAlta, String nroActa, BigDecimal valor,  String nroExpedienteAlta, String resolucionAlta,Responsable responsable, Responsable subResponsable,Sector area){
        unInventario.nuevoBien(codigo,nroInventario, descripcion,fechaAlta, nroActa,valor, nroExpedienteAlta, resolucionAlta,responsable,subResponsable,area);
    }*/
    
    public static void bajaBien(Integer nroInventario,String nroExpediente, String resolucion,String descripcion,Date fechaBaja,String tipo){
        unInventario.bajaBien(nroInventario,nroExpediente,resolucion,descripcion,fechaBaja,tipo);
    }
    
    public static void modificarBien(Bien unBien,String codigo, Integer nroInventario, String descripcion, Date fechaAlta, String nroActa, BigDecimal valor,  String nroExpedienteAlta, String resolucionAlta){
        unInventario.modificarBien(unBien, codigo, Integer.MIN_VALUE, descripcion, fechaAlta, nroActa, BigDecimal.ZERO, nroExpedienteAlta, resolucionAlta);
    }
    public Bien anteriorNroInventario(Integer unId){
        return unInventario.anteriorNroInventario(unId);
    }
    public Bien siguienteNroInventario(Integer unId){
        return unInventario.siguienteNroInventario(unId);
    }
    public Bien primerNroDeInventario (){
        return unInventario.primerNroDeInventario();
    }
    public Bien ultimoNroDeInventario (){
        return unInventario.ultimoNroDeInventario();
    }
    public ArrayList<Bien> porNroInventario(Integer unNumero) throws NullPointerException{
        return unInventario.porNroInventario(unNumero);
    }
    public ArrayList<Bien> porDescripcion(String unaDescripcion)  throws NullPointerException{
        return unInventario.porDescripcion(unaDescripcion);
    }
    
    public ArrayList<Bien> porArea(String unNombreArea){
        return unInventario.porArea(unNombreArea);
    }
    
    public ArrayList<Bien> porResponsable(String unNombreResponsable){
        return unInventario.porResponsable(unNombreResponsable);
    }
    
    public void bajaUsuario(String unUserName){
        this.unInventario.bajaUsuario(unUserName);
    }
    public void nuevoUsuario(String nombre, String username, String pass, String rol){
        this.unInventario.agregarUsuario(this.unInventario.nuevoUsuario(nombre, username, pass, rol));
    }
    public void modificarUsuario(Usuario unUsuario,String unNombre,String unUserName,String unPass,String unRol){
        this.unInventario.modificarUsuario(unUsuario,unNombre,unUserName,unPass,unRol);
    }
    public Map<String,Usuario> listaDeUsuarios(){
        return this.unInventario.getUsuariosActivos();
    }
    public boolean validarUsuario(String userName,String pass){
        return this.unInventario.validarUsuario(userName, pass);
    }
    public Usuario obtenerUsuario(String unUserName){
        return this.unInventario.obtenerUsuario(unUserName);
    }
    
    /*public void asignarBien(Bien unBien,Responsable unResponsable,Responsable unSubResponsable,Sector unSector){
        this.unInventario.asignarBien(unBien, unResponsable,unSubResponsable, unSector);  
    }*/
    
    public String obtenerResponsableBien(Integer id){
        return this.unInventario.obtenerResponsableBien(id);
    }
    
    /*public String obtenerAreaBien(Integer id){
        return this.unInventario.obtenerAreaBien(id);
    }*/
    
    /*public String obtenerFechaAsignacion(Integer id){
        return this.unInventario.obtenerFechaAsignacion(id);
    }*/
    
    public ArrayList<Bien>buscar(int tipo){
        return this.unInventario.buscar( tipo);
    } 
    public ArrayList<Bien>buscar(Integer desde,int tipo){
         return this.unInventario.buscar(desde,tipo);
    }
    public ArrayList<Bien>buscar(Integer desde,Integer hasta,int tipo){
        return this.unInventario.buscar(desde,hasta,tipo);
    }
    public Bien BienPorNroDeInventario(Integer nro){
        return this.unInventario.BienPorNroDeInventario(nro);
    }
    public List<Asignacion> asignacionesPorResponsable(Responsable unResponsable){
        return this.unInventario.asignacionesPorResponsable(unResponsable);
    }
    public List<Bien> bienesAsignados(Responsable unResponsable){
        return this.unInventario.bienesAsignados(unResponsable);
    }
    public boolean existeAsignacion(Bien unBien, Responsable unResponsable){
        return this.unInventario.existeBienAsignado(unBien, unResponsable);
    }
}
