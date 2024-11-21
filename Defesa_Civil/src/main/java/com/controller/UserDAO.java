package com.controller;

import com.model.Endereco;
import com.model.User;
import util.JPAUtil;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public class UserDAO {
    private boolean validarUser(User user) {
        if (user.getUsername() == null || user.getUsername().trim().length() < 2) {
            System.err.println("Nome inválido. Deve conter pelo menos 2 caracteres.");
            return false;
        }
        if (user.getUsername().trim().isEmpty()) {
            System.err.println("O nome de usuário é obrigatório.");
            return false;
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            System.err.println("A senha é obrigatória.");
            return false;
        }
        if (user.getPassword() .length() <= 6) {
            System.err.println("A senha deve possuir no minimo de 6 caracteres.");
            return false;
        }

        return false;
    };
    private boolean isUsernameEmUso(String username, EntityManager em) {
        String consulta = "SELECT COUNT(u) FROM User u WHERE u.username = :username";
        Long count = em.createQuery(consulta, Long.class)
                .setParameter("username", username)
                .getSingleResult();
        return count > 0;
    }

    private boolean isSenhaEmUso(String senha, EntityManager em) {
        String consulta = "SELECT COUNT(u) FROM User u WHERE u.password = :senha";
        Long count = em.createQuery(consulta, Long.class)
                .setParameter("senha", senha)
                .getSingleResult();
        return count > 0;
    }

    public boolean registerUser(User user) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            validarUser(user);

            if (isUsernameEmUso(user.getUsername(), em)) {
                System.err.println("Nome de usuário já está em uso.");
            }
            if (isSenhaEmUso(user.getPassword(), em)) {
                System.err.println("Senha invalida");
                return false;
            }

            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            return true;
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            em.getTransaction().rollback();
            return false;
        } catch (Exception e) {
            System.err.println("Ocorreu um erro ao cadastrar o usuário.");
            em.getTransaction().rollback();
            return false;
        } finally {
            em.close();
        }
    }

    public User loginUser(String username, String password) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String consulta = "SELECT u FROM User u WHERE u.username = :username";
            User usuario = em.createQuery(consulta, User.class)
                    .setParameter("username", username)
                    .getSingleResult();


            if (usuario.getPassword().equals(password)) {
                return usuario;
            } else {
                return null;
            }
        } catch (NoResultException e) {

            return null;
        } finally {
            em.close();
        }
    }
}