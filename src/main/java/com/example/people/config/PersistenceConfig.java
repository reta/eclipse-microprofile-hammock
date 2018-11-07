package com.example.people.config;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.apache.deltaspike.jpa.api.transaction.TransactionScoped;

@ApplicationScoped
public class PersistenceConfig {
    @PersistenceUnit(unitName = "peopledb")
    private EntityManagerFactory entityManagerFactory;

    @Produces @PeopleDb @TransactionScoped
    public EntityManager create() {
        return this.entityManagerFactory.createEntityManager();
    }

    public void dispose(@Disposes @PeopleDb EntityManager entityManager) {
        if (entityManager.isOpen()) {
            entityManager.close();
        }
    }
}