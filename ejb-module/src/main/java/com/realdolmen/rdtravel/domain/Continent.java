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
@NamedQueries({
        @NamedQuery(name = Continent.FIND_ALL, query = "SELECT c FROM Continent c ORDER BY c.name"),
        @NamedQuery(name = Continent.FIND_BY_ISO2, query = "SELECT c FROM Continent c WHERE c.ISO2 = :iso ORDER BY c.name")
})
public class Continent implements Serializable {
    public static final String FIND_ALL = "Continent.findAll";
    public static final String FIND_BY_ISO2 = "Continent.findByISO2";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    @Column(nullable = false)
    @Size(min = 1, max = 255)
    private String name;

    @NotNull
    @Column(nullable = false, length = 2)
    @Size(min = 2, max = 2)
    private String ISO2;

    @Valid
    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @NotNull
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

    public String getISO2() {
        return ISO2;
    }

    public void setISO2(String ISO2) {
        this.ISO2 = ISO2;
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
