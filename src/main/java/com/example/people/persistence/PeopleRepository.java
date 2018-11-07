package com.example.people.persistence;

import java.util.Collection;
import java.util.Optional;

public interface PeopleRepository {
    Optional<PersonEntity> findByEmail(String email);
    PersonEntity saveOrUpdate(String email, String firstName, String lastName);
    Collection<PersonEntity> findAll();
    Optional<PersonEntity> deleteByEmail(String email);
}
