package com.realdolmen.rdtravel.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by JSTAX29 on 6/10/2015.
 */
@Entity
public class Partner {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @NotNull
    @Column(nullable = false, unique = true)
    @Size(min = 1, max = 255)
    private String name;

    @Version
    private long version;

    public Partner() {
    }

    public Partner(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
