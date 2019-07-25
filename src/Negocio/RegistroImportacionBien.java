/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

/**
 *
 * @author diego
 */
public class RegistroImportacionBien {
    private String codigo;
    private String nroInventario;
    private String descripcion;
    private String fecha;
    private String nroActa;
    private String valor;
    private String area;
    private String responsable;

    public RegistroImportacionBien(String codigo, String nroInventario, String descripcion, String fecha, String nroActa, String valor, String area, String responsable) {
        this.codigo = codigo;
        this.nroInventario = nroInventario;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.nroActa = nroActa;
        this.valor = valor;
        this.area = area;
        this.responsable = responsable;
    }

    public RegistroImportacionBien() {
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNroInventario() {
        return nroInventario;
    }

    public void setNroInventario(String nroInventario) {
        this.nroInventario = nroInventario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNroActa() {
        return nroActa;
    }

    public void setNroActa(String nroActa) {
        this.nroActa = nroActa;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }
    
     

    
}
