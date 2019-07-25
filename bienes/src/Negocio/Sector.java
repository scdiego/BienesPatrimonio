/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import java.io.Serializable;
import javax.persistence.*;

@Entity
public class Sector implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;
    @Basic
    private String nombre;
    @Basic
    private String estado;
    @Basic
    private boolean baja;

    public Sector() {
    }

    public Sector(String nombre) {
        this.nombre = nombre;
        this.estado = "Activo";
        this.baja = false;
    }

    public Sector(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.estado = "Activo";
        this.baja = false;
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
    
    public void darBaja(){
        this.estado = "Baja";
        this.setBaja(true);
    }
    
    @Override
    public String toString() {
        return this.getNombre();
    }
    
}
