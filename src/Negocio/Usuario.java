package Negocio;

import Persistencia.UsuarioJpaController;
import Persistencia.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import javax.persistence.*;

/**
 *
 * @author Usuario  ddd adadf a
 */
@Entity
public class Usuario implements Serializable {
    /**
     * Nombre
     * Username
     * Password
     * LastAcces
     * Rol
     */
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;
    @Basic
    private String nombre;
    @Basic
    private String username;
    @Basic
    private String pass;
    @Basic
    private String lastacces;
    @Basic
    private String rol;
    @Basic
    private boolean baja;
    
    
    /* CONSTRUCTORES */

    public Usuario() {
    }

    public Usuario(String nombre, String username, String pass, String rol) {
        this.nombre = nombre;
        this.username = username;
        this.pass =  getMD5(pass);
        this.rol = rol;
        this.baja = false;
    }

    public Usuario(Integer id, String nombre, String username, String pass, String lastacces, String rol,boolean baja) {
        this.id = id;
        this.nombre = nombre;
        this.username = username;
        this.pass = pass;
        this.lastacces = lastacces;
        this.rol = rol;
        this.baja = baja;
    }
    
    public Integer getId() {
        return id;
    }

    
    public boolean isBaja() {
        return baja;
    }

    /*SETTER GETTER */
    public void setBaja(boolean baja) {
        this.baja = baja;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getLastacces() {
        return lastacces;
    }

    public void setLastacces(String lastacces) {
        this.lastacces = lastacces;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
      
    /* METODOS */
    public void modificar(String unNombre,String unUserName,String unPass,String unRol){
        this.setNombre(unNombre);
        this.setUsername(unUserName);
        this.setPass(unPass);
        this.setRol(unRol);
    }
    public boolean validar(String unUsername, String unPass){
        boolean salida = false;
        if(!this.baja){
            if(unUsername == null ? this.getUsername() == null : unUsername.equals(this.getUsername())){
                if(unPass == null ? this.getPass() == null : getMD5(unPass).equals(getPass())){
                    salida = true;
                }
            }
        }else{
            salida = false;
        }
        
        return salida;
    }
    public void cambiarPass(String pass){
        UsuarioJpaController dao = new UsuarioJpaController();
        this.setPass(this.getMD5(pass));
        try{
            dao.update(this, this.id);
        }
        catch (NonexistentEntityException ex){
            //
        }
        
        
    }
    public  String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);

            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    public void setTimestamp(){
         Timestamp timestamp = new Timestamp(System.currentTimeMillis());
         this.setLastacces(timestamp.toString());
    }
    public void darBaja(){
        this.baja = true;
    }
    
    @Override
    public String toString() {
        return this.getNombre().concat(" -- ").concat(this.getUsername());
    }
    
    // Permisos
    public boolean habilitarEdicion(){
        boolean salida = false;
        //consulta
        // admin
        //usuario
        String rol = this.getRol();
        switch (rol){
            case "consulta":
                salida = false;
                break;
            case "usuario":
                salida = true;
                break;
            case "admin":
                salida = true;
                break;
            case "sys":
                salida = true;
                break;
        }
        
        return salida;
    }
    public boolean habilitarNuevo(){
                boolean salida = false;
        //consulta
        // admin
        //usuario
        String rol = this.getRol();
        switch (rol){
            case "consulta":
                salida = false;
                break;
            case "usuario":
                salida = true;
                break;
            case "admin":
                salida = true;
                break;
            case "sys":
                salida = true;
                break;

        }
        
        return salida;
    }
    public boolean habilitarBaja(){
                boolean salida = false;
        //consulta
        // admin
        //usuario
        String rol = this.getRol();
        switch (rol){
            case "consulta":
                salida = false;
                break;
            case "usuario":
                salida = true;
                break;
            case "admin":
                salida = true;
                break;
            case "sys":
                salida = true;
                break;

        }
        
        return salida;
    }
    public boolean habilitarGuardar(){
                boolean salida = false;
        //consulta
        // admin
        //usuario
        String rol = this.getRol();
        switch (rol){
            case "consulta":
                salida = false;
                break;
            case "usuario":
                salida = true;
                break;
            case "admin":
                salida = true;
                break;
        }
        
        return salida;
    }
    public boolean habilitarAuditoria(){
        boolean salida = false;
        switch (rol){
            case "consulta":
                salida = false;
                break;
            case "usuario":
                salida = false;
                break;
            case "admin":
                salida = true;
                break;
        }
        
        return salida;
    }
    
    public boolean habilitarUsuario(){
        boolean salida = false;
        switch (rol){
            case "consulta":
                salida = false;
                break;
            case "usuario":
                salida = false;
                break;
            case "admin":
                salida = true;
                break;
        }
        
        return salida;
    }

    
    
}