package org.eclipse.jakarta.dao;

import java.util.List;

import jakarta.enterprise.context.RequestScoped;
import jakarta.transaction.Transactional;
import org.eclipse.jakarta.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;


@RequestScoped
public class UserDao {
    @PersistenceContext(unitName = "jpa-unit")
    private EntityManager em;

    @Transactional
    public void addUser(User user) {
        em.persist(user);
    }

    public User getUserByUsername(String username) {
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
        query.setParameter("username", username);
        List<User> resultList = query.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    public List<User> getAllUsers() {
        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }


}
