package com.example.people.service;

import java.util.Collection;
import java.util.Optional;

import com.example.people.rest.Person;

public interface PeopleService {
    Optional<Person> findByEmail(String email);
    Person add(Person person);
    Collection<Person> getAll();
    Optional<Person> remove(String email);
}
