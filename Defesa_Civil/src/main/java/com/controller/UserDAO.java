package com.controller;

import com.model.User;
import util.JPAUtil;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public class UserDAO {

    public boolean registerUser(User user) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    public User loginUser(String username, String password) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String query = "SELECT u FROM User u WHERE u.username = :username AND u.password = :password";
            return em.createQuery(query, User.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
}
