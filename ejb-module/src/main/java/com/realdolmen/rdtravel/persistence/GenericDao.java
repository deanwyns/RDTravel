package com.realdolmen.rdtravel.persistence;

import java.io.Serializable;
import java.util.List;

/**
 * Created by JSTAX29 on 4/10/2015.
 * A generic DAO for CRUD operations.
 */
public interface GenericDao<T, PK extends Serializable> extends Serializable {
    /**
     * Persist the newInstance object into database
     */
    T create(T newInstance);

    /**
     * Retrieve an object that was previously persisted to the database using
     * the indicated id as primary key
     */
    T read(PK id);

    /**
     * Save changes made to a persistent object.
     */
    T update(T transientObject);

    /**
     * Remove an object from persistent storage in the database
     */
    void delete(T persistentObject);

    /**
     * Return all the occurrences of the given object.
     */
    List<T> findAll();
}