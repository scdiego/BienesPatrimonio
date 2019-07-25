package Persistencia;

import Negocio.Bien;
import Negocio.Responsable;
import Negocio.Usuario;
import Persistencia.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.Persistence;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author desarrollo
 */
public class BienJpaController implements Serializable {
    private EntityManagerFactory emf = null;
    private String descripcion;
    private String descripcionBaja;
    private Date fechaAlta;
    private Date fechaBaja;
    private String nroActa;
    private String nroExpedienteAlta;
    private String nroExpedienteBaja;
    private Integer nroInventario;
    private String resolucionAlta;
    private String resolucionBaja;
    private BigDecimal valor;
    private String codigo;
    private String estado;
    private boolean debaja;
    private String tipoBaja;

       
    public BienJpaController() {
        emf = Persistence.createEntityManagerFactory("patromonioPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public void create(Bien bien,Usuario user) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(bien);
 
            StoredProcedureQuery sp = em.createStoredProcedureQuery("SP_AUDITOR");
            sp.registerStoredProcedureParameter("usuario", Integer.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("operacion", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("bien", Integer.class, ParameterMode.IN);

            int id = user.getId();
            int nro = bien.getNroInventario();
            sp.setParameter("usuario", id);
            String ope = "CREACION BIEN";          
            sp.setParameter("operacion", ope);
            sp.setParameter("bien", nro);
            sp.execute();
            
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    private void setMyAttributes(Bien bien) {
        this.descripcion = bien.getDescripcion();
        this.descripcionBaja = bien.getDescripcionBaja();
        this.fechaAlta = bien.getFechaAlta();
        this.fechaBaja = bien.getFechaBaja();
        this.nroActa = bien.getNroActa();
        this.nroExpedienteAlta = bien.getNroExpedienteAlta();
        this.nroExpedienteBaja = bien.getNroExpedienteBaja();
        this.nroInventario = bien.getNroInventario();
        this.resolucionAlta = bien.getResolucionAlta();
        this.resolucionBaja = bien.getResolucionBaja();
        this.valor = bien.getValor();
        this.codigo = bien.getCodigo();
        this.estado = bien.getEstado();
        this.debaja = bien.isDebaja();
        this.tipoBaja = bien.getTipoBaja();
    }
    
    private void setNewBienAttributes(Bien newBien) {
        newBien.setDescripcion(descripcion);
        newBien.setDescripcionBaja(descripcionBaja);
        newBien.setFechaAlta(fechaAlta);
        newBien.setFechaBaja(fechaBaja);
        newBien.setNroActa(nroActa);
        newBien.setNroExpedienteAlta(nroExpedienteAlta);
        newBien.setNroExpedienteBaja(nroExpedienteBaja);
        newBien.setNroInventario(nroInventario);
        newBien.setResolucionAlta(resolucionAlta);
        newBien.setResolucionBaja(resolucionBaja);
        newBien.setValor(valor);
        newBien.setCodigo(codigo);
        newBien.setEstado(estado);
        newBien.setDebaja(debaja);
        newBien.setTipoBaja(tipoBaja);
    }
    public void baja(Bien bien, Integer id, Usuario user) throws NonexistentEntityException {
        EntityManager em = null;
        this.setMyAttributes(bien);
        em = getEntityManager();
        Bien newBien = (Bien) em.find(Bien.class ,id);
        try {
            em.getTransaction().begin();
            setNewBienAttributes(newBien);
            // aca la auditoria
            
            StoredProcedureQuery sp = em.createStoredProcedureQuery("SP_AUDITOR");
            sp.registerStoredProcedureParameter("usuario", Integer.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("operacion", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("bien", Integer.class, ParameterMode.IN);

            int userid = user.getId();
            int nro = bien.getNroInventario();
            sp.setParameter("usuario", userid);
            String ope = "Baja BIEN";          
            sp.setParameter("operacion", ope);
            sp.setParameter("bien", nro);
            sp.execute();
            
            
            em.getTransaction().commit();
        }
        catch(Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                id = bien.getId();
                if (findBien(id) == null) {
                    throw new NonexistentEntityException("El Bien con id " + id + " no longer exists.");
                }
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public void update(Bien bien, Integer id, Usuario user) throws NonexistentEntityException {
        EntityManager em = null;
        this.setMyAttributes(bien);
        em = getEntityManager();
        Bien newBien = (Bien) em.find(Bien.class ,id);
        try {
            em.getTransaction().begin();
            setNewBienAttributes(newBien);
            // aca la auditoria
            
            StoredProcedureQuery sp = em.createStoredProcedureQuery("SP_AUDITOR");
            sp.registerStoredProcedureParameter("usuario", Integer.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("operacion", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("bien", Integer.class, ParameterMode.IN);

            int userid = user.getId();
            int nro = bien.getNroInventario();
            sp.setParameter("usuario", userid);
            String ope = "MODIFICACION BIEN";          
            sp.setParameter("operacion", ope);
            sp.setParameter("bien", nro);
            sp.execute();
            
            
            em.getTransaction().commit();
        }
        catch(Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                id = bien.getId();
                if (findBien(id) == null) {
                    throw new NonexistentEntityException("El Bien con id " + id + " no longer exists.");
                }
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public void destroy(Bien bien, Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        Date fechaBaja = bien.getFechaBaja();
        String resolucionBaja = bien.getResolucionBaja();
        String nroActa = bien.getNroActa();
        em = getEntityManager();
        Bien newBien = (Bien) em.find(Bien.class ,id);
        try {
            em.getTransaction().begin();
            newBien.setFechaBaja(fechaBaja);
            newBien.setResolucionBaja(resolucionBaja);
            newBien.setNroActa(nroActa);
            em.getTransaction().commit();
        }
        catch(Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                id = bien.getId();
                if (findBien(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
                }
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public List<Bien> findBienEntities() {
        return findBienEntities(true, -1, -1);
    }

    public List<Bien> findBienEntities(int maxResults, int firstResult) {
        return findBienEntities(false, maxResults, firstResult);
    }

    private List<Bien> findBienEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            em.getEntityManagerFactory().getCache().evictAll();
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Bien.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Bien findBien(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Bien.class, id);
        } finally {
            em.close();
        }
    }
    
    public Bien findBienNroInventario(Integer nro){
        Bien unBien = this.findBienByNroInventario(nro).get(0);
        return unBien;
    }
    
    public List<Bien> findBienesOrdenados(){
        EntityManager em = getEntityManager();
        em.getEntityManagerFactory().getCache().evictAll();
      Query query = em.createQuery(
        "SELECT b FROM Bien b ORDER BY b.nroInventario");
      List<Bien> bienes = query.getResultList();
      return bienes;
    }
    
    public List<Bien> findBienByNroInventario(Integer nroInventario) {
      EntityManager em = getEntityManager();
      em.getEntityManagerFactory().getCache().evictAll();
      Query query = em.createQuery(
        "SELECT b FROM Bien b WHERE b.nroInventario = :nroInventario ")
        .setParameter("nroInventario", nroInventario);
      List<Bien> bienes = query.getResultList();
      return bienes;
    }
    
    public List<Bien> findBienByNroInventario(Integer nroInventario,boolean baja) {
      EntityManager em = getEntityManager();
      em.getEntityManagerFactory().getCache().evictAll();
      String q = "";
      Query query ;
      if(baja){
          q = "SELECT b FROM Bien b WHERE b.nroInventario = :nroInventario and b.debaja= true";
      }else{
          q = "SELECT b FROM Bien b WHERE b.nroInventario = :nroInventario";
      }
      query = em.createQuery(q)
        .setParameter("nroInventario", nroInventario);
      List<Bien> bienes = query.getResultList();
      return bienes;
    }
    public List<Bien> findBienByNroDesde(Integer desde,boolean baja){
      EntityManager em = getEntityManager();
      em.getEntityManagerFactory().getCache().evictAll();
      String q = "";
      if(baja){
        q = "SELECT b FROM Bien b WHERE b.nroInventario >= :desde and b.debaja = true ORDER BY b.nroInventario";
      }else{
        q =  "SELECT b FROM Bien b WHERE b.nroInventario >= :desde ORDER BY b.nroInventario";
      }
      Query query = em.createQuery(q)
        .setParameter("desde", desde);
      List<Bien> bienes = query.getResultList();
      return bienes;
    }
    
    public List<Bien> findBienByNroDesdeHasta(Integer desde, Integer hasta,boolean baja){
      EntityManager em = getEntityManager();
      em.getEntityManagerFactory().getCache().evictAll();
      String q = "";
      if(baja){
          q = "SELECT b FROM Bien b WHERE b.nroInventario >= :desde AND b.nroInventario <= :hasta  and b.debaja = true ORDER BY b.nroInventario";
      }else{
          q = "SELECT b FROM Bien b WHERE b.nroInventario >= :desde AND b.nroInventario <= :hasta  ORDER BY b.nroInventario";
      }
      Query query = em.createQuery(q)
        .setParameter("desde", desde)
        .setParameter("hasta", hasta);
      List<Bien> bienes = query.getResultList();
      return bienes;        
    }
    
    
    
    public List<Bien> findBienByNroDesde(Integer desde){
      EntityManager em = getEntityManager();
      em.getEntityManagerFactory().getCache().evictAll();
      Query query = em.createQuery(
        "SELECT b FROM Bien b WHERE b.nroInventario >= :desde ORDER BY b.nroInventario")
        .setParameter("desde", desde);
      List<Bien> bienes = query.getResultList();
      return bienes;
    }
    
    public List<Bien> findBienByNroDesdeHasta(Integer desde, Integer hasta){
      EntityManager em = getEntityManager();
      em.getEntityManagerFactory().getCache().evictAll();
      Query query = em.createQuery(
        "SELECT b FROM Bien b WHERE b.nroInventario >= :desde AND b.nroInventario <= :hasta  ORDER BY b.nroInventario")
        .setParameter("desde", desde)
        .setParameter("hasta", hasta);
      List<Bien> bienes = query.getResultList();
      return bienes;        
    }
    
    public List<Bien> findBienesByDescripcion(String descripcion) throws NoResultException {
      EntityManager em = getEntityManager();    
      em.getEntityManagerFactory().getCache().evictAll();
      String q = "SELECT b FROM Bien b WHERE  lower(b.descripcion) LIKE LOWER(concat('%', :descripcion,'%')) ORDER BY b.nroInventario";
      Query query = em.createQuery(q)
              .setParameter("descripcion", descripcion);
      List<Bien> bienes = query.getResultList();
      return bienes;
    }
    
    public List<Bien> findBienesByDescripcion(String descripcion,boolean baja) throws NoResultException {
      EntityManager em = getEntityManager();    
      em.getEntityManagerFactory().getCache().evictAll();
      
      String q;
      if(baja){
          q = "SELECT b FROM Bien b WHERE  b.debaja= true and lower(b.descripcion) LIKE LOWER(concat('%', :descripcion,'%')) ORDER BY b.nroInventario";
      }else{
          q = "SELECT b FROM Bien b WHERE lower(b.descripcion) LIKE LOWER(concat('%', :descripcion,'%')) ORDER BY b.nroInventario";
      }
      Query query = em.createQuery(q)
              .setParameter("descripcion", descripcion);
      List<Bien> bienes = query.getResultList();
      return bienes;
    }
    
    public List<Bien> findBienesByResponsable (String responsable){
      EntityManager em = getEntityManager();    
      em.getEntityManagerFactory().getCache().evictAll();
      String q = "SELECT b FROM Bien b, Asignacion a, Responsable r WHERE b.ID = a.bien and a.responsable_id = r.ID and r.nombre = :responsabe";
      Query query = em.createQuery(q)
        .setParameter("responsabe",responsable);
      List <Bien> bienes = query.getResultList();
      return bienes;
    }
    
    public List<Bien> findBienesByResponsable (String responsable,boolean baja){
      EntityManager em = getEntityManager();    
      em.getEntityManagerFactory().getCache().evictAll();
      String q; 
      if(baja){
        q = "SELECT b "
                + "FROM Asignacion a JOIN a.Bien b JOIN a.Responsable r"
                + "WHERE b.debaja = true and r.nombre = :responsabe";
      }else{
        q = "SELECT b "
                + "FROM Asignacion a JOIN a.Bien b JOIN a.Responsable r"
                + "WHERE r.nombre = :responsabe";          
      }
      
       
      
      Query query = em.createQuery(q)
        .setParameter("responsabe",responsable);
      List <Bien> bienes = query.getResultList();
      return bienes;
    }
    
    public List<Bien> findBienesBySector (String sector){
      EntityManager em = getEntityManager();    
      em.getEntityManagerFactory().getCache().evictAll();
      String q = "SELECT b FROM Bien b, Asignacion a, Responsable r, Sector s WHERE  a.bien_id = b.id and a.RESPONSABLE_ID = r.id and r.SECTOR_ID = s.id and s.nombre = :sector";
      Query query = em.createQuery(q)
              .setParameter("sector", sector);
      List<Bien> bienes = query.getResultList();
      return bienes;
    }
    
    public List<Bien> findBienesByFecha(Date desde){
        EntityManager em = getEntityManager();
      em.getEntityManagerFactory().getCache().evictAll();
      Query query = em.createQuery(
        "SELECT b FROM Bien b WHERE b.fechaAlta >= :desde ORDER BY b.nroInventario")
        .setParameter("desde", desde);
      List<Bien> bienes = query.getResultList();
      return bienes;
    }
    
    public List<Bien> findBienesByFecha(Date desde, Date hasta){
                EntityManager em = getEntityManager();
      em.getEntityManagerFactory().getCache().evictAll();
      Query query = em.createQuery(
        "SELECT b FROM Bien b WHERE b.fechaAlta >= :desde and b.fechaAlta <= :hasta ORDER BY b.nroInventario")
        .setParameter("desde", desde)
        .setParameter("hasta", hasta)      ;
      List<Bien> bienes = query.getResultList();
      return bienes;
    }
    
    public List<Bien> findBienesByFecha(Date desde,boolean baja){
        EntityManager em = getEntityManager();
      em.getEntityManagerFactory().getCache().evictAll();
      String q = "";
      if(baja){
          q = "SELECT b FROM Bien b WHERE b.fechaAlta >= :desde and b.debaja = true ORDER BY b.nroInventario";
      }else{
          q = "SELECT b FROM Bien b WHERE b.fechaAlta >= :desde ORDER BY b.nroInventario";
      }
      Query query = em.createQuery(q )
        .setParameter("desde", desde);
      List<Bien> bienes = query.getResultList();
      return bienes;
    }
    
    public List<Bien> findBienesByFecha(Date desde, Date hasta,boolean baja){
                EntityManager em = getEntityManager();
      em.getEntityManagerFactory().getCache().evictAll();
      String q = "";
      if(baja){
          q = "SELECT b FROM Bien b WHERE b.fechaAlta >= :desde and b.fechaAlta <= :hasta and b.debaja = true ORDER BY b.nroInventario";
      }else{
          q = "SELECT b FROM Bien b WHERE b.fechaAlta >= :desde and b.fechaAlta <= :hasta ORDER BY b.nroInventario";
      }
      Query query = em.createQuery(q)
        .setParameter("desde", desde)
        .setParameter("hasta", hasta)      ;
      List<Bien> bienes = query.getResultList();
      return bienes;
    }
    
    public List<Bien> findBienesByFechaBaja(Date fecha){
      EntityManager em = getEntityManager();
      em.getEntityManagerFactory().getCache().evictAll();
      String q = "";
      q = "SELECT b FROM Bien b WHERE b.fechaBaja >= :fecha";
      Query query = em.createQuery(q)
        .setParameter("fecha", fecha);
      List<Bien> bienes = query.getResultList();
      return bienes;
        
    }

    public int getBienCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Bien> rt = cq.from(Bien.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Bien primerBien(){
       Query query;
       EntityManager em = getEntityManager();
       em.getEntityManagerFactory().getCache().evictAll();
       
       String q = "SELECT b FROM Bien b ORDER BY b.nroInventario ASC ";
       query = em.createQuery(q);
       query.setMaxResults(1);
       return (Bien) query.getSingleResult();
    }
    
    public Bien ultimoBien (){
       Query query;
       EntityManager em = getEntityManager();
       em.getEntityManagerFactory().getCache().evictAll();
       
       String q = "SELECT b FROM Bien b ORDER BY b.nroInventario DESC ";
        try{
            query = em.createQuery(q);
            query.setMaxResults(1);
            return (Bien) query.getSingleResult();
        } catch(NoResultException e) {
            return null;
        }
    }
    
    public Bien siguienteBien(int nro){
       EntityManager em = getEntityManager();
       em.getEntityManagerFactory().getCache().evictAll();
       
       String q = "SELECT b FROM Bien b WHERE b.nroInventario =: inv";
       Query query = em.createQuery(q)
              .setParameter("inv", nro + 1);
       return (Bien) query.getSingleResult();
    }
    public Bien anteriorBien(int nro){
       EntityManager em = getEntityManager();
       em.getEntityManagerFactory().getCache().evictAll();
       
       String q = "SELECT b FROM Bien b WHERE b.nroInventario =: inv";
       Query query = em.createQuery(q)
              .setParameter("inv", nro - 1);
       return (Bien) query.getSingleResult();
    }
    
}
