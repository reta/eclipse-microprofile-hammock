package com.example.people.service;


import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.example.people.persistence.PeopleRepository;
import com.example.people.persistence.PersonEntity;
import com.example.people.rest.Person;

@ApplicationScoped
public class PeopleServiceImpl implements PeopleService {
    @Inject private PeopleRepository repository;

    @Override
    public Optional<Person> findByEmail(String email) {
        return repository
            .findByEmail(email)
            .map(this::toPerson);
    }

    @Override
    public Person add(Person person) {
        return toPerson(repository.saveOrUpdate(person.getEmail(), person.getFirstName(), person.getLastName()));
    }

    @Override
    public Collection<Person> getAll() {
        return repository
            .findAll()
            .stream()
            .map(this::toPerson)
            .collect(Collectors.toList());
    }

    @Override
    public Optional<Person> remove(String email) {
        return repository
            .deleteByEmail(email)
            .map(this::toPerson);
    }
    
    private Person toPerson(PersonEntity entity) {
        return new Person(entity.getEmail(), entity.getFirstName(), entity.getLastName());
    }
}
