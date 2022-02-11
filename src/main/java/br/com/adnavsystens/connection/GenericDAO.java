package br.com.adnavsystens.connection;

import java.util.List;

import javax.persistence.EntityManager;

public class GenericDAO<E> {

	private static EntityManager manager =  HibernateUtils.getEntityManager();
	
	/* Métodos CRUD (Create, Return, Update, Delete)*/
		
	public E salvar(E entity) throws Exception {
		manager.getTransaction().begin();
		E obj = manager.merge(entity);
		manager.getTransaction().commit();
		return obj;
	}
	
	@SuppressWarnings("unchecked")
	public E pesquisar(E entity) {
		Object id = HibernateUtils.getPK(entity);
		manager.getTransaction().begin();
		E e = (E) manager.find(entity.getClass(), id);
		manager.getTransaction().commit();
		return e;
	}
	
	@SuppressWarnings("unchecked")
	public List<E> pesquisarTodos(Class<E> entityClass) {
		manager.getTransaction().begin();
		List<E> list =  (List<E>) manager.createQuery("from " + entityClass.getSimpleName()).getResultList();
		manager.getTransaction().commit();
		return list;
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
