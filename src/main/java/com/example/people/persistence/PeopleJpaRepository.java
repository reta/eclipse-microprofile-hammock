package com.example.people.persistence;

import java.util.Collection;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.deltaspike.jpa.api.entitymanager.EntityManagerConfig;
import org.apache.deltaspike.jpa.api.transaction.Transactional;

import com.example.people.config.PeopleDb;

@ApplicationScoped
@EntityManagerConfig(qualifier = PeopleDb.class)
public class PeopleJpaRepository implements PeopleRepository {
    @Inject @PeopleDb private EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public Optional<PersonEntity> findByEmail(String email) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
    
        final CriteriaQuery<PersonEntity> query = cb.createQuery(PersonEntity.class);
        final Root<PersonEntity> root = query.from(PersonEntity.class);
        query.where(cb.equal(root.get(PersonEntity_.email), email));
        
        try {
            final PersonEntity entity = em.createQuery(query).getSingleResult();
            return Optional.of(entity);
        } catch (final NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public PersonEntity saveOrUpdate(String email, String firstName, String lastName) {
        final PersonEntity entity = new PersonEntity(email, firstName, lastName);
        em.persist(entity);
        return entity;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<PersonEntity> findAll() {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<PersonEntity> query = cb.createQuery(PersonEntity.class);
        query.from(PersonEntity.class);
        return em.createQuery(query).getResultList();
    }

    @Override
    @Transactional
    public Optional<PersonEntity> deleteByEmail(String email) {
        return findByEmail(email)
            .map(entity -> {
                em.remove(entity);
                return entity;
            });
    }
}
