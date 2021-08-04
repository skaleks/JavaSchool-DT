package com.magenta.crud;

import com.magenta.myexception.DatabaseException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;

public abstract class AbstractDao<T> {

    private final Class<T> entity;


    @SuppressWarnings("unchecked")
    public AbstractDao() {
        this.entity = (Class<T>) ((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession(){
        return sessionFactory.getCurrentSession();
    }

    public T findById(int id) throws DatabaseException {
        return getSession().find(entity,id);
    }

    public void create(T entity) {
        getSession().persist(entity);
    }

    public void update(T entity) {
        getSession().update(entity);
    }

    public void delete(T entity) {
        getSession().delete(entity);
    }
}
