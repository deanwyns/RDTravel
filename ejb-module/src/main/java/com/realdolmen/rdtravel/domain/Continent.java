package com.realdolmen.rdtravel.domain;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created by DWSAX40 on 6/10/2015.
 */
@Entity
@NamedQueries(
        @NamedQuery(name = Continent.FIND_ALL, query = "SELECT c FROM Continent c ORDER BY c.name")
)
public class Continent implements Serializable {
    public static final String FIND_ALL = "Continent.findAll";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    @Column(nullable = false)
    @Size(min = 1, max = 255)
    private String name;

    @Valid
    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @NotNull
    @Size(min = 1)
    private Set<Country> countries = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Country> getCountries() {
        return countries;
    }

    public void setCountries(Set<Country> countries) {
        this.countries = countries;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Continent continent = (Continent) o;
        return Objects.equals(id, continent.id) &&
                Objects.equals(name, continent.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}