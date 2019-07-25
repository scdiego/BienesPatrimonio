/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Negocio.Auditoria;
import Negocio.Bien;
import Negocio.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author diego
 */
public class AuditoriaJpaController  implements Serializable{
    
    private EntityManagerFactory emf = null;
    
    public AuditoriaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public AuditoriaJpaController() {
        emf = Persistence.createEntityManagerFactory("patromonioPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public void create(Auditoria auditoria) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(auditoria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public List<Auditoria> findAuditoriaEntities() {
        return findAuditoriaEntities(true, -1, -1);
    }

    public List<Auditoria> findAuditoriaEntities(int maxResults, int firstResult) {
        return findAuditoriaEntities(false, maxResults, firstResult);
    }

    public List<Auditoria> findAuditoriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Auditoria.class));
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
    
    public List<Auditoria> findAuditoriaByUsuario(Usuario usuario) {
        EntityManager em = getEntityManager();
        String q = "SELECT a FROM Auditoria a WHERE a.usuario = :usuario";
        Query query = em.createQuery(q).setParameter("usuario", usuario);
        return query.getResultList();    
    }
    
    public List<Auditoria> findAuditoriaByBien(Bien bien){
       EntityManager em = getEntityManager();
       String q = "SELECT a FROM Auditoria a WHERE a.bien = :bien";
       Query query = em.createQuery(q).setParameter("bien", bien);
       return query.getResultList();
               
    }

}
