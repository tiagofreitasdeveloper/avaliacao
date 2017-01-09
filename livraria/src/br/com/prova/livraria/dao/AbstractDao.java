package br.com.prova.livraria.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

public class AbstractDao<T> {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public void persist(T entity){
		try {
			em.persist(entity);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Transactional
	public void merge(T  entity) {
		try {
			em.merge(entity);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	@Transactional
	public void delete(T entity){
		try {
			em.remove(entity);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public T findById(T entity){
		return (T) em.find(entity.getClass(), entity);
	}
	
	public List<T> findAll(Class<?> clazz){
		Query query = em.createQuery("from " + clazz.getName());
		return query.getResultList();
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}
	
}
