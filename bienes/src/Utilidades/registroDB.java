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
public class registroDB {
    private int nro;
    private String nombreCampo;
    private String valorCampo;   

    public registroDB(int nro, String nombreCampo, String valorCampo) {
        this.nro = nro;
        this.nombreCampo = nombreCampo;
        this.valorCampo = valorCampo;
    }
    
    public registroDB(){
        
    }

    public int getNro() {
        return nro;
    }

    public void setNro(int nro) {
        this.nro = nro;
    }

    public String getNombreCampo() {
        return nombreCampo;
    }

    public void setNombreCampo(String nombreCampo) {
        this.nombreCampo = nombreCampo;
    }

    public String getValorCampo() {
        return valorCampo;
    }

    public void setValorCampo(String valorCampo) {
        this.valorCampo = valorCampo;
    }
    
    
    
}


