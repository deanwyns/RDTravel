package com.realdolmen.rdtravel.domain;

import javax.persistence.*;

/**
 * Created by JSTAX29 on 6/10/2015.
 */
@Entity
public class Partner {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(nullable = false)
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
