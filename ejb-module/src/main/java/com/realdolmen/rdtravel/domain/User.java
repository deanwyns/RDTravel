package com.realdolmen.rdtravel.domain;

import com.realdolmen.rdtravel.util.PasswordHash;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by JSTAX29 on 2/10/2015.
 * A user of the system.
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NamedQueries(
        @NamedQuery(name = User.FIND_BY_EMAIL, query = "SELECT u FROM User u WHERE u.email = :email")
)
public abstract class User implements Serializable {
    public static final String FIND_BY_EMAIL = "User.findByEmail";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(length = 50, nullable = false)
    @Size(max = 50, min = 1)
    private String firstName;

    @NotNull
    @Column(length = 50, nullable = false)
    @Size(max = 50, min = 1)
    private String lastName;

    @NotNull
    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull
    @Column(length = 102, nullable = false)
    private String password;

    @Transient
    @Size(min = 6, max = 50)
    private String plainPassword;

    @Version
    private long version;

    protected User() {
    }

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        setPassword(password);
    }

    public Long getId() {
        return id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
        setPassword(plainPassword);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        try {
            this.password = (password != null ? PasswordHash.createHash(password) : null);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }
}
