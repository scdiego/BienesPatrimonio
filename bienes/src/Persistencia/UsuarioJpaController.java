/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Persistencia;

import Negocio.Usuario;
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
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public UsuarioJpaController() {
        this.emf = Persistence.createEntityManagerFactory("patromonioPU");
    }

    public void create(Usuario usuario) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void update(Usuario user, Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        String nombre = user.getNombre();
        String pass = user.getPass();
        String rol = user.getRol();
        String username = user.getUsername();
        String lastAccess = user.getLastacces();
        em = getEntityManager();
        Usuario newUser = (Usuario) em.find(Usuario.class ,id);
        try {
            em.getTransaction().begin();
            newUser.setNombre(nombre);
            newUser.setRol(rol);
            newUser.setUsername(username);
            newUser.setLastacces(lastAccess);
            newUser.setPass(pass);
            em.getTransaction().commit();
        }
        catch(Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                id = user.getId();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
                }
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

   public void destroy(Usuario user, Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            try {
                Usuario newUser = (Usuario) em.find(Usuario.class ,id);
                newUser.setBaja(true);
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

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
 
    public Usuario findUsuarioByUserName(String unUserName) throws NoResultException{
        EntityManager em = getEntityManager();
        Query query;
        query = em.createQuery(
                "SELECT u FROM Usuario u WHERE u.username  = :username")
                .setParameter("username", unUserName);
        //try{
        return (Usuario) query.getSingleResult();            
        
    }
    
    
    
    /**
     * public Responsable findResponsableByNombre(String unNombre){
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
     */
}