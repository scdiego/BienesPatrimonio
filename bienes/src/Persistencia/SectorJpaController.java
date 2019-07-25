
package Persistencia;

import Negocio.Sector;
import Persistencia.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.persistence.criteria.*;

public class SectorJpaController implements Serializable {
    private EntityManagerFactory emf = null;
    
    public SectorJpaController() {
        this.emf = Persistence.createEntityManagerFactory("patromonioPU");

    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sector sector) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(sector);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Sector sector, Integer sectorId) throws NonexistentEntityException {
        EntityManager em = null;        
        em = getEntityManager();
        try {
            em.getTransaction().begin();
            Sector sectorBaja = (Sector) em.find(Sector.class , sectorId);
            sectorBaja.setBaja(true);
            em.getTransaction().commit();
        }
        catch(Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = sector.getId();
                if (findSector(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
                }
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }
        /*EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sector sector;
            try {
                sector = em.getReference(Sector.class, id);
                sector.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sector with id " + id + " no longer exists.", enfe);
            }
            em.remove(sector);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }*/
    }

    public List<Sector> findSectorEntities() {
        return findSectorEntities(true, -1, -1);
    }

    public List<Sector> findSectorEntities(int maxResults, int firstResult) {
        return findSectorEntities(false, maxResults, firstResult);
    }

    private List<Sector> findSectorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sector.class));
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

    public Sector findSector(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sector.class, id);
        } finally {
            em.close();
        }
    }
    
    public Sector findSectorByNombre(String unNombre){        
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT s FROM Sector s WHERE s.nombre= :nombre").setParameter("nombre", unNombre);
        try{
            return (Sector) query.getSingleResult();
        }catch(NoResultException e){
            return null;
        }
        
    }
    
    public Sector obtenerSector(String unNombre){
        Sector salida = null;
        salida = this.findSectorByNombre(unNombre);
        if(salida == null){
            salida.setBaja(false);
            salida.setNombre(unNombre);
            this.create(salida);
        }
        return salida;
    } 

    public int getSectorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sector> rt = cq.from(Sector.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public void update(Sector sector, Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        String nombre = sector.getNombre();
        em = getEntityManager();
        Sector newSector = (Sector) em.find(Sector.class ,id);
        try {
            em.getTransaction().begin();
            newSector.setNombre(nombre);
            em.getTransaction().commit();
        }
        catch(Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                id = sector.getId();
                if (findSector(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
                }
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
}
