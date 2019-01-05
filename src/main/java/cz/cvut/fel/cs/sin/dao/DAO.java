package cz.cvut.fel.cs.sin.dao;

import java.util.List;

interface DAO<T> {

    List<T> list();

    T save(T entity);

    void delete(Object id);

    T find(Object id);

    T update(T entity);
}
