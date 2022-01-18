package br.com.adnavsystens.connection;

import java.util.List;

import javax.persistence.EntityManager;

public class GenericDAO<E> {

	private static EntityManager manager =  HibernateUtils.getEntityManager();
	
	/* Métodos CRUD (Create, Return, Update, Delete)*/
		
	public E salvar(E entity) {
		manager.getTransaction().begin();
		E obj = manager.merge(entity);
		manager.getTransaction().commit();
		return obj;
	}
	
	@SuppressWarnings("unchecked")
	public E pesquisar(E entity) {
		Object id = HibernateUtils.getPK(entity);
		return (E) manager.find(entity.getClass(), id);
	}
	
	@SuppressWarnings("unchecked")
	public List<E> pesquisarTodos(Class<E> entityClass) {
		return (List<E>) manager.createQuery("from " + entityClass.getSimpleName())
				.getResultList();
	}
		
	public void excluir(E Entity) {
		manager.getTransaction().begin();
		manager.remove(manager.find(Entity.getClass(), (Object) HibernateUtils.getPK(Entity)));
		manager.getTransaction().commit();
	}
	
	public EntityManager getEntityManager() {
		return manager;
	}
	
	
}
