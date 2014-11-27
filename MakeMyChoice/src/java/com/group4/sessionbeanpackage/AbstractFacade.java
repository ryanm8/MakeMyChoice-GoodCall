/*
 * Created by Brian Green on 2014.11.18  * 
 * Copyright © 2014 Brian Green. All rights reserved. * 
 */
package com.group4.sessionbeanpackage;

import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Brian
 */
public abstract class AbstractFacade<T> {
    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }
    
    /**
     * Executes an arbitrary query to the table associated with the Facade.
     * Replaces one variable in the query with the given value.
     * 
     * @param query String representing the desired db query
     * @param varName The name of the variable to be replaced in the query
     * @param varValue The value with which to replace the variable
     * @return The list of values returned by the db query
     */
    public List<T> findByQueryOneParam(String query, String varName, Object varValue) {
        return getEntityManager().createQuery(query).setParameter(varName, varValue).getResultList();
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
}
