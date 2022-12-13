package com.springSecurity.jwt.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.invoke.MethodHandles;
import java.util.Optional;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    Long id;

    String username;

    String Password;

    String Role;

    public User() {
    }

    public User( String username, String password, String role) {
        this.username = username;
        Password = password;
        Role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", Password='" + Password + '\'' +
                ", Role='" + Role + '\'' +
                '}';
    }


}
