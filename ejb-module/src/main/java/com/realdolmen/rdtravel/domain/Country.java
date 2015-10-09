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
@NamedQueries({
        @NamedQuery(name = Country.FIND_ALL, query = "SELECT c FROM Country c ORDER BY c.name"),
        @NamedQuery(name = Country.FIND_BY_ISO2, query = "SELECT c FROM Country c WHERE c.ISO2 = :iso ORDER BY c.name"),
        @NamedQuery(name = Country.FIND_BY_ISO3, query = "SELECT c FROM Country c WHERE c.ISO3 = :iso ORDER BY c.name")
})
public class Country implements Serializable {
    public static final String FIND_ALL = "Country.findAll";
    public static final String FIND_BY_ISO2 = "Country.findByISO2";
    public static final String FIND_BY_ISO3 = "Country.findByISO3";

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

    @NotNull
    @Column(nullable = false, length = 3)
    @Size(min = 3, max = 3)
    private String ISO3;

    public Country() {
    }

    public Country(String name, String ISO2, String ISO3) {
        this.name = name;
        this.ISO2 = ISO2;
        this.ISO3 = ISO3;
    }


    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getISO2() {
        return ISO2;
    }

    public void setISO2(String ISO2) {
        this.ISO2 = ISO2;
    }

    public String getISO3() {
        return ISO3;
    }

    public void setISO3(String ISO3) {
        this.ISO3 = ISO3;
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
