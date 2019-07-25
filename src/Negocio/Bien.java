
package Negocio;


import Persistencia.AsignacionJpaController;
import Utilidades.formatoBigDecimal;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NoResultException;

/**
 *
 * @author Usuario
 */
@Entity
public class Bien implements Serializable {


/**
 *
 * @author Usuario
 */
    /**
     * CODIGO	
     * Nº INV
     * DESCRIPCION DEL BIEN	
     * FECHA DE ALTA	
     * Nº DE ACTA DE RECEPCION	 
     * VALOR 	
     * DESTINO 	
     * SUB RESPONSABLE
     */
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Basic
    private String codigo;
    @Basic
    private Integer nroInventario;
    @Basic
    private String descripcion;
    @Basic
    private Date fechaAlta;
    @Basic
    private String nroActa;
    @Basic
    private BigDecimal valor;
    @Basic
    private String estado;
    @Basic
    private boolean debaja;
    @Basic
    private String nroExpedienteAlta;
    @Basic
    private String resolucionAlta;
    @Basic
    private String nroExpedienteBaja;
    @Basic
    private String resolucionBaja;
    @Basic
    private String descripcionBaja;
    @Basic
    private Date fechaBaja;
    @Basic
    private String tipoBaja;


    public Bien() {}

    public Bien(String codigo, Integer nroInventario, String descripcion, Date fechaAlta, String nroActa, BigDecimal valor,  String nroExpedienteAlta, String resolucionAlta) {
        this.codigo = codigo;
        this.nroInventario = nroInventario;
        this.descripcion = descripcion;
        this.fechaAlta = fechaAlta;
        this.nroActa = nroActa;
        this.valor = valor;
        this.estado = "Activo";
        this.nroExpedienteAlta = nroExpedienteAlta;
        this.resolucionAlta = resolucionAlta;
        this.debaja = false;
    }
   
    public Integer getId() {
        return this.id;
    }

    public Bien(String codigo, Integer nroInventario, String descripcion, Date fechaAlta, String nroActa, BigDecimal valor, String estado, boolean debaja, String nroExpedienteAlta, String resolucionAlta, String nroExpedienteBaja, String resolucionBaja, String descripcionBaja, Date fechaBaja, String tipoBaja) {
        this.codigo = codigo;
        this.nroInventario = nroInventario;
        this.descripcion = descripcion;
        this.fechaAlta = fechaAlta;
        this.nroActa = nroActa;
        this.valor = valor;
        this.estado = estado;
        this.debaja = debaja;
        this.nroExpedienteAlta = nroExpedienteAlta;
        this.resolucionAlta = resolucionAlta;
        this.nroExpedienteBaja = nroExpedienteBaja;
        this.resolucionBaja = resolucionBaja;
        this.descripcionBaja = descripcionBaja;
        this.fechaBaja = fechaBaja;
        this.tipoBaja = tipoBaja;
    }
    
    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public Integer getNroInventario() {
        return nroInventario;
    }
    public void setNroInventario(Integer nroInventario) {
        this.nroInventario = nroInventario;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public Date getFechaAlta() {
        return fechaAlta;
    }
    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }
    public String getNroActa() {
        return nroActa;
    }
    public void setNroActa(String nroActa) {
        this.nroActa = nroActa;
    }
    public BigDecimal getValor() {
        return valor;
    }
    /*public String getValor(){      
        return formatoBigDecimal.bigDecimalToString(this.valor);
    }
    */
    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public String getNroExpedienteAlta() {
        return nroExpedienteAlta;
    }
    public void setNroExpedienteAlta(String nroExpedienteAlta) {
        this.nroExpedienteAlta = nroExpedienteAlta;
    }
    public String getResolucionAlta() {
        return resolucionAlta;
    }
    public void setResolucionAlta(String resolucionAlta) {
        this.resolucionAlta = resolucionAlta;
    }
    public String getNroExpedienteBaja() {
        return nroExpedienteBaja;
    }
    public void setNroExpedienteBaja(String nroExpedienteBaja) {
        this.nroExpedienteBaja = nroExpedienteBaja;
    }
    public String getResolucionBaja() {
        return resolucionBaja;
    }
    public void setResolucionBaja(String resolucionBaja) {
        this.resolucionBaja = resolucionBaja;
    }
    public boolean isDebaja() {
        return debaja;
    }
    public void setDebaja(boolean debaja) {
        this.debaja = debaja;
    }
    public String getDescripcionBaja() {
        return descripcionBaja;
    }
    public void setDescripcionBaja(String descripcionBaja) {
        this.descripcionBaja = descripcionBaja;
    }
    public Date getFechaBaja() {
        return fechaBaja;
    }
    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public String getTipoBaja() {
        return tipoBaja;
    }

    public void setTipoBaja(String tipoBaja) {
        
        
        this.tipoBaja = tipoBaja;
    }
    
    
    
    /**
     * METODOS
     */
    
    public String estado(){
       String salida ;

       if(this.isDebaja()){
           salida = "BAJA";
       }else{
           salida = "BUENO";
       }    
       //Falta nuevo    
       return salida;
    }
    
    public void bajaBien(String nroExpediente,String resolucion,String descripcion,Date fechaBaja,String tipo){
        this.setEstado("BAJA");
        this.setNroExpedienteBaja(nroExpediente);
        this.setResolucionBaja(resolucion);
        this.setDescripcionBaja(descripcion);
        this.setFechaBaja(fechaBaja);
        this.tipoBaja = tipo;
        this.debaja = true;
        this.valor = new BigDecimal(1);
    }
        
    public void modificar(String codigo, Integer nroInventario, String descripcion, Date fechaAlta, String nroActa, BigDecimal valor,  String nroExpedienteAlta, String resolucionAlta){
        this.setCodigo(codigo);
        this.setNroInventario(nroInventario);
        this.setDescripcion(descripcion);
        this.setFechaAlta(fechaAlta);
        this.setNroActa(nroActa);
        this.setValor(valor);
        this.setNroExpedienteAlta(nroExpedienteAlta);
        this.setResolucionAlta(resolucionAlta);
    }    
    public boolean ContieneNombre(String unString){        
        //str1.toLowerCase().contains(str2.toLowerCase())
      return  this.getDescripcion().toLowerCase().contains(unString);
    }
    
    @Override
    public String toString() {
        String descrip;
        if(this.descripcion.length() > 15){
            descrip = this.getDescripcion().substring(0,15);
        }else{
            descrip = this.getDescripcion();
        }
        
        return this.getNroInventario().toString().concat(" --- ").concat(descrip);
    }
    
    public Sector sectorAsignado(){
        AsignacionJpaController asignacionDao = new AsignacionJpaController();
        try {
            return asignacionDao.findSectorByNroInventario(this.nroInventario);
        } catch (NoResultException e ){
            return null;
        }
    }
    
    public Responsable responsableAsignado(){
        AsignacionJpaController asignacionDao = new AsignacionJpaController();
        try {
            return asignacionDao.findResponsableByNroInventario(this.nroInventario);
        } catch (NoResultException e ){
            return null;
        }
    }
    
    public Responsable SubresponsableAsignado(){
        AsignacionJpaController asignacionDao = new AsignacionJpaController();
        try {
            return asignacionDao.findSubResponsableByNroInventario(this.nroInventario);
        } catch (NoResultException e ){
            return null;
        }
    }
}
