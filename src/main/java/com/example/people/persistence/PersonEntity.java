package com.example.people.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "people")
public class PersonEntity {
    @Id
    @Column(length = 256) 
    private String email;

    @Column(nullable = false, length = 256, name = "first_name")
    private String firstName;

    @Column(nullable = false, length = 256, name = "last_name")
    private String lastName;

    @Version
    private Long version;
    
    protected PersonEntity() {
    }
    
    public PersonEntity(String email) {
        setEmail(email);
    }

    public PersonEntity(String email, final String firstName, final String lastName) {
        this(email);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }
    
    protected void setEmail(String email) {
        this.email = email;
    }
    
    protected Long getVersion() {
        return version;
    }
    
    protected void setVersion(Long version) {
        this.version = version;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
