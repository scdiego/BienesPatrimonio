package Negocio;

import java.io.Serializable;
import javax.persistence.*;

@Entity
public class Responsable implements Serializable{
    /**
    * Nombre
    * DNI
    */
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;
    @Basic
    private String nombre;
    @Basic
    private String dni;
    @Basic
    private String estado;
    @Basic
    private boolean baja;    
    @Basic
    private String cargo;
    @ManyToOne
    private Sector sector;

    public Responsable() {
    }

    public Responsable(Integer id,String nombre, String dni,String cargo,Sector unSector) {
        this.id = id;
        this.nombre = nombre;
        this.dni = dni;
        this.estado = "Activo";
        this.baja = false;
        this.cargo = cargo;
        this.sector = unSector;
    }
        public Responsable(String nombre, String dni,String cargo,Sector unSector) {
        
        this.nombre = nombre;
        this.dni = dni;
        this.estado = "Activo";
        this.baja = false;
        this.cargo = cargo;
        this.sector = unSector;
    }

    public Responsable(String nombre, String dni) {
        this.nombre = nombre;
        this.dni = dni; 
    }
    
    public Integer getId() {
        return id;
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

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean isBaja() {
        return baja;
    }

    public void setBaja(boolean baja) {
        this.baja = baja;
    }
    
    public boolean getBaja() {
        return baja;
    }

    public void darBaja(){
        this.setBaja(true);
        this.setEstado("Baja");
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Sector getSector() {
        return sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }
    
    
    public void modificarDatos(String unNombre, String unDni,String cargo, Sector unSector){
        this.setDni(unDni);
        this.setNombre(unNombre);
        this.setCargo(cargo);
        this.setSector(unSector);
    }
    
    @Override
    public String toString() {
        return this.getNombre();
    }
    
    @Override
    public boolean equals (Object obj) {
        if (obj instanceof Responsable) {
        Responsable tmpPersona = (Responsable) obj;
            if (this.nombre.equals(tmpPersona.nombre) && this.dni.equals(tmpPersona.dni)) {
                    return true; 
            } else { 
                    return false; 
            }

        } else { 
            return false; 
        }
    } 

}
