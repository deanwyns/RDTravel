package com.realdolmen.rdtravel.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by DWSAX40 on 6/10/2015.
 */
@Entity
@NamedQueries(
        @NamedQuery(name = Country.FIND_ALL, query = "SELECT c FROM Country c ORDER BY c.name")
)
public class Country implements Serializable {
    public static final String FIND_ALL = "Country.findAll";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    @Column(nullable = false)
    @Size(min = 1, max = 255)
    private String name;

    @NotNull
    @Column(nullable = false)
    @Size(min = 2, max = 2)
    private String ISO2;

    @NotNull
    @Column(nullable = false)
    @Size(min = 3, max = 3)
    private String ISO3;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return Objects.equals(id, country.id) &&
                Objects.equals(name, country.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
