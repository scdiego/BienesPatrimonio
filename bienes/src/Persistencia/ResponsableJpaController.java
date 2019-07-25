
package Persistencia;

import Negocio.Responsable;
import Negocio.Sector;
import Persistencia.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author desarrollo
 */
public class ResponsableJpaController implements Serializable {

    public ResponsableJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public ResponsableJpaController() {
        emf = Persistence.createEntityManagerFactory("patromonioPU");
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Responsable responsable) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(responsable);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public Responsable findResponsableByDNI(String dni) {
      EntityManager em = getEntityManager();
      Query query = em.createQuery(
        "SELECT r FROM Responsable r WHERE r.dni= :dni")
        .setParameter("dni", dni);
      try {
          return (Responsable) query.getSingleResult();
      } catch(NoResultException e) {
          return null;
      }
    }
    
    public Responsable findResponsableByNombre(String unNombre){
              EntityManager em = getEntityManager();
      Query query = em.createQuery(
        "SELECT r FROM Responsable r WHERE r.nombre= :nombre")
        .setParameter("nombre", unNombre);
      try {
          return (Responsable) query.getSingleResult();
      } catch(NoResultException e) {
          return null;
      }
    }
    
    public Responsable ObtenerResponsalble(String unNombre,String unNombreSector){
        SectorJpaController daoSector = new SectorJpaController();
        Responsable salida = null;
        Sector unSector = null; 
        salida = this.findResponsableByNombre(unNombre);
        unSector = daoSector.obtenerSector(unNombreSector);
        if(salida == null){
            salida.setBaja(false);
            salida.setNombre(unNombre);
            salida.setSector(unSector);
            this.create(salida);
        }
        return salida;
        
    }

    public void edit(Responsable responsable) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            responsable = em.merge(responsable);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = responsable.getId();
                if (findResponsable(id) == null) {
                    throw new NonexistentEntityException("The responsable with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void update(Responsable responsible, Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        String dni = responsible.getDni();
        String estado = responsible.getEstado();
        String nombre = responsible.getNombre();
        boolean baja = responsible.getBaja();
        em = getEntityManager();
        Responsable newResponsible = (Responsable) em.find(Responsable.class ,id);
        try {
            em.getTransaction().begin();
            newResponsible.setBaja(true);
            newResponsible.setDni(dni);
            newResponsible.setEstado(estado);
            newResponsible.setNombre(nombre);
            em.getTransaction().commit();
        }
        catch(Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                id = responsible.getId();
                if (findResponsable(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
                }
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Responsable user, Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            try {
                Responsable newResponsable = (Responsable) em.find(Responsable.class ,id);
                newResponsable.setBaja(true);
                em.getTransaction().commit();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }

        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Responsable> findResponsableEntities() {
        return findResponsableEntities(true, -1, -1);
    }

    public List<Responsable> findResponsableEntities(int maxResults, int firstResult) {
        return findResponsableEntities(false, maxResults, firstResult);
    }

    private List<Responsable> findResponsableEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Responsable.class));
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

    public Responsable findResponsable(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Responsable.class, id);
        } finally {
            em.close();
        }
    }

    public int getResponsableCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Responsable> rt = cq.from(Responsable.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
 
    public List<Responsable> findResponsablesBySector(Sector sector) {
        EntityManager em = getEntityManager();
        String q = "SELECT r FROM Responsable r WHERE r.sector = :sector";
        Query query = em.createQuery(q).setParameter("sector", sector);
        return query.getResultList();    
    }
    
}