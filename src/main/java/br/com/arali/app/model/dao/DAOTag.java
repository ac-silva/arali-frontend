package br.com.arali.app.model.dao;

import br.com.arali.app.model.Deck;
import br.com.arali.app.model.Tag;
import br.com.arali.app.util.DAO;
import br.com.arali.app.util.EntityFactory;
import org.hibernate.Session;
import javax.persistence.EntityManager;
import javax.xml.bind.JAXBException;
import java.sql.SQLException;
import java.util.List;

public class DAOTag implements DAO<Tag> {
    @Override
    public Tag insert(Tag obj) throws SQLException, ClassNotFoundException, JAXBException {
        EntityManager em = EntityFactory.getInstance().createEntityManager();
        em.getTransaction().begin();
        em.persist(obj);
        em.getTransaction().commit();
        boolean result = em.getTransaction().getRollbackOnly();
        if(result) em.getTransaction().rollback();
        em.close();
        EntityFactory.close();
        return obj;
    }

    @Override
    public boolean edit(Tag obj) throws SQLException, ClassNotFoundException, JAXBException {
        EntityManager em = EntityFactory.getInstance().createEntityManager();
        em.getTransaction().begin();
        em.merge(obj);
        em.getTransaction().commit();
        boolean result = em.getTransaction().getRollbackOnly();
        if(result) em.getTransaction().rollback();
        em.close();
        EntityFactory.close();
        return !result;
    }

    @Override
    public Tag find(Long id) throws SQLException, ClassNotFoundException, JAXBException {
        EntityManager em = EntityFactory.getInstance().createEntityManager();
        Tag tag = em.find(Tag.class, id);
        em.close();
        return tag;
    }

    @Override
    public List<Tag> findAll() throws SQLException, ClassNotFoundException, JAXBException {
        Session session  = (Session) EntityFactory.getInstance().createEntityManager().getDelegate();
        List<Tag> tags = session.createCriteria(Tag.class).list();
        session.close();
        return tags;
    }

    @Override
    public boolean delete(Long id) throws SQLException, ClassNotFoundException, JAXBException {
        EntityManager em = EntityFactory.getInstance().createEntityManager();
        em.getTransaction().begin();
        em.remove(em.getReference(Tag.class, id));
        em.getTransaction().commit();
        boolean result = em.getTransaction().getRollbackOnly();
        if(result) em.getTransaction().rollback();
        em.close();
        EntityFactory.close();
        return !result;
    }
}
