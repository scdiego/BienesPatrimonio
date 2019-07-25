package Negocio;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table
public class Asignacion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;
    @OneToOne
    private Responsable responsable;
    @OneToOne
    private Bien bien;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaDesde;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaHasta;
    
    private Responsable subResponsable;

    public Asignacion() {
    }

    public Asignacion(Responsable responsable, Bien bien, Date fechaDesde, Responsable subResponsable) {
        this.responsable = responsable;
        this.bien = bien;
        this.fechaDesde = fechaDesde;
        this.subResponsable = subResponsable;
    }

    public Asignacion(Integer id, Responsable responsable, Bien bien, Sector sector, Date fechaDesde, Responsable subResponsable) {
        this.id = id;
        this.responsable = responsable;
        this.bien = bien;
        this.fechaDesde = fechaDesde;
        this.subResponsable = subResponsable;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Responsable getResponsable() {
        return responsable;
    }

    public void setResponsable(Responsable responsable) {
        this.responsable = responsable;
    }

    public Bien getBien() {
        return bien;
    }

    public void setBien(Bien bien) {
        this.bien = bien;
    }

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public Responsable getSubResponsable() {
        return subResponsable;
    }

    public void setSubResponsable(Responsable subResponsable) {
        this.subResponsable = subResponsable;
    }
    

    @Override
    public String toString() {
        return "Asignacion{" + "id=" + id + ", responsable=" + responsable + ", "
                + "bien=" + bien + ", fechaDesde="
                + fechaDesde + ", fechaHasta=" + fechaHasta + '}';
    }
    
    
    
}
