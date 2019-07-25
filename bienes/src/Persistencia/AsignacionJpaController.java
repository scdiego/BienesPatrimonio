package Persistencia;

import Negocio.Asignacion;
import Negocio.Bien;
import Negocio.Responsable;
import Negocio.Sector;
import Negocio.Usuario;
import Negocio.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.persistence.criteria.*;

/**
 *
 * @author desarrollo
 */
public class AsignacionJpaController implements Serializable {

    public AsignacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public AsignacionJpaController() {
        this.emf = Persistence.createEntityManagerFactory("patromonioPU");
    }
    
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Asignacion asignacion, Usuario user, Bien bien) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(asignacion);
            
            StoredProcedureQuery sp = em.createStoredProcedureQuery("SP_AUDITOR");
            sp.registerStoredProcedureParameter("usuario", Integer.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("operacion", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("bien", Integer.class, ParameterMode.IN);

            int id = user.getId();
            int nro = bien.getNroInventario();
            sp.setParameter("usuario", id);
            String ope = "CREACION Asignacion";          
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

    public void edit(Asignacion asignacion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            asignacion = em.merge(asignacion);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = asignacion.getId();
                if (findAsignacion(id) == null) {
                    throw new NonexistentEntityException("The asignacion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Asignacion asignacion;
            try {
                asignacion = em.getReference(Asignacion.class, id);
                asignacion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The asignacion with id " + id + " no longer exists.", enfe);
            }
            em.remove(asignacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Asignacion> findAsignacionEntities() {
        return findAsignacionEntities(true, -1, -1);
    }

    public List<Asignacion> findAsignacionEntities(int maxResults, int firstResult) {
        return findAsignacionEntities(false, maxResults, firstResult);
    }

    private List<Asignacion> findAsignacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            em.getEntityManagerFactory().getCache().evictAll();
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Asignacion.class));
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

    public Asignacion findAsignacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Asignacion.class, id);
        } finally {
            em.close();
        }
    }

    public Responsable findResponsableByNroInventario(Integer nroInventario) throws NoResultException {
      EntityManager em = getEntityManager();
      em.getEntityManagerFactory().getCache().evictAll();
        Query query = em.createQuery("SELECT a FROM Asignacion a WHERE a.bien.nroInventario =:nroInventario").setParameter("nroInventario", nroInventario);
        
            Asignacion asignacion = (Asignacion) query.getSingleResult();
            return asignacion.getResponsable();
        
    }
    
    public Sector findSectorByNroInventario(Integer nroInventario) throws NoResultException{
        EntityManager em = getEntityManager();
        em.getEntityManagerFactory().getCache().evictAll();
        Query query = em.createQuery("SELECT a FROM Asignacion a WHERE a.bien.nroInventario =:nroInventario").setParameter("nroInventario", nroInventario);
        
            Asignacion asignacion = (Asignacion) query.getSingleResult();
            return asignacion.getResponsable().getSector();
        
    }
    
    public int getAsignacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Asignacion> rt = cq.from(Asignacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Bien> findAsignacionesByResponsable(Responsable responsable) {
        EntityManager em = getEntityManager(); 
        em.getEntityManagerFactory().getCache().evictAll();
        String q = "SELECT a.bien"
                + " FROM Asignacion a"
                + " WHERE a.responsable = :responsable"
                + " AND a.fechaHasta IS NULL";
        Query query = em.createQuery(q)
                .setParameter("responsable", responsable);
        List<Bien> list = query.getResultList();
        return list;        
    }
    
    public List<Bien> findAsignacionesByResponsable(Responsable responsable,boolean baja) {
        EntityManager em = getEntityManager(); 
        em.getEntityManagerFactory().getCache().evictAll();
        String q = "";
        if(baja){
            q = "SELECT a.bien"
                + " FROM Asignacion a"
                + " WHERE a.responsable = :responsable"
                + " AND a.fechaHasta IS NULL and a.bien.debaja = true";
        }else{
        q = "SELECT a.bien"
                + " FROM Asignacion a"
                + " WHERE a.responsable = :responsable"
                + " AND a.fechaHasta IS NULL";    
        }
            
        
        Query query = em.createQuery(q)
                .setParameter("responsable", responsable);
        List<Bien> list = query.getResultList();
        return list;        
    }
    
    public List<Bien> findBienBySector(Sector sector){
        EntityManager em = getEntityManager(); 
        em.getEntityManagerFactory().getCache().evictAll();
        String q = "SELECT a.bien"
                + " FROM Asignacion a"
                + " WHERE a.responsable.sector = :sector"
                + " AND a.fechaHasta IS NULL";
        Query query = em.createQuery(q)
                      .setParameter("sector", sector);
        List<Bien> list = query.getResultList();
        return list;
        
    }
    
        public List<Bien> findBienBySector(Sector sector,boolean baja){
        EntityManager em = getEntityManager(); 
        em.getEntityManagerFactory().getCache().evictAll();
        String q = "";
        if(baja){
            q = "SELECT a.bien"
                + " FROM Asignacion a"
                + " WHERE a.responsable.sector = :sector"
                + " AND a.fechaHasta IS NULL and a.bien.debaja = true";
        }else{
            q = "SELECT a.bien"
                + " FROM Asignacion a"
                + " WHERE a.responsable.sector = :sector"
                + " AND a.fechaHasta IS NULL";
        }
        
        Query query = em.createQuery(q)
                      .setParameter("sector", sector);
        List<Bien> list = query.getResultList();
        return list;
        
    }
    
    public Asignacion findAsignacionByNroInventario(int nroInventario) throws NoResultException{
      EntityManager em = getEntityManager();
      em.getEntityManagerFactory().getCache().evictAll();
      Query query = em.createQuery(
        "SELECT a FROM Asignacion a, Bien b WHERE a.bien_id = b.id and b.nroInventario = :nroInventario AND a.FECHAHASTA IS NULL")
        .setParameter("nroInventario", nroInventario);
      Asignacion asignacion = (Asignacion) query.getSingleResult();
      return asignacion;
      
      
    }
    
    public Asignacion findAsignacionByIdBien(int idBien){
        Asignacion asignacion = new Asignacion();
        try{
            EntityManager em = getEntityManager();
            em.getEntityManagerFactory().getCache().evictAll();
            Query query = em.createQuery("SELECT a FROM Asignacion a WHERE a.bien.id = :idBien AND a.fechaHasta is null ")
            .setParameter("idBien", idBien);
           // query.setMaxResults(1);
            asignacion = (Asignacion) query.getSingleResult();
           
        }catch(NoResultException e) {
            return null;
        }catch (NonUniqueResultException e){
            asignacion=null;
        }
        return asignacion;
    }
    
    public boolean existeAsignacion(Bien unBien, Responsable unResponsable){
        EntityManager em = getEntityManager();
        boolean salida = false;
        em.getEntityManagerFactory().getCache().evictAll();
        Query query = em.createQuery("SELECT a FROM Asignacion a WHERE a.bien = :bien AND a.responsable = :responsable").setParameter("bien", unBien).setParameter("responsable", unResponsable);
        List<Asignacion> asignaciones = query.getResultList();
        if(asignaciones.size() > 0){
           salida = true; 
        }
        
        return salida;
        
    }
    
}
