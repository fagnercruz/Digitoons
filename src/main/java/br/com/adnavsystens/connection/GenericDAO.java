package br.com.adnavsystens.connection;

import java.util.List;

import javax.persistence.EntityManager;

public class GenericDAO<E> {

	private static EntityManager manager =  HibernateUtils.getEntityManager();
	
	/* Métodos CRUD (Create, Return, Update, Delete)*/
		
	public E salvar(E entity) throws Exception {
		if(!manager.getTransaction().isActive()) {
			manager.getTransaction().begin();
		}
		E obj = manager.merge(entity);
		manager.getTransaction().commit();
		return obj;
	}
	
	/**
	 * Retorna o Objeto pesquisado pelo seu PK
	 * @return O Objeto pesquisado ou NULL
	 * @param
	 * */
	@SuppressWarnings("unchecked")
	public E pesquisar(E entity) {
		Object id = HibernateUtils.getPK(entity);
		if(!manager.getTransaction().isActive()) {
			manager.getTransaction().begin();
		}
		E e = (E) manager.find(entity.getClass(), id);
		manager.getTransaction().commit();
		return e;
	}
	
	@SuppressWarnings("unchecked")
	public E pesquisarSQL(E entity) {
		Object id = HibernateUtils.getPK(entity);
		if(!manager.getTransaction().isActive()) {
			manager.getTransaction().begin();
		}
		E e = (E) manager.createQuery("from " + entity.getClass().getSimpleName() + " where id = " + id.toString()).getSingleResult();
		manager.getTransaction().commit();
		return e;
	}
	
	@SuppressWarnings("unchecked")
	public List<E> pesquisarTodos(Class<E> entityClass) {
		if(!manager.getTransaction().isActive()) {
			manager.getTransaction().begin();
		}
		List<E> list =  (List<E>) manager.createQuery("from " + entityClass.getSimpleName() + " order by id asc").getResultList();
		manager.getTransaction().commit();
		return list;
	}
		
	
	/**
	 * Método para remoção em cascata de uma entidade e seus relacionamentos
	 * 
	 * @return Entidade excluída ou null para erro
	 * @param
	 * @author Fagner Cruz
	 * 
	 */
	@SuppressWarnings("unchecked")
	public E excluir(E Entity) {
		try {
			if(!manager.getTransaction().isActive()) {
				manager.getTransaction().begin();
			}
			E e = (E) manager.find(Entity.getClass(), (Object) HibernateUtils.getPK(Entity));
			manager.remove(e);
			manager.getTransaction().commit();
			return e;
		} catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
			return null;
		} 
	}
	
	public EntityManager getEntityManager() {
		return manager;
	}

	public static boolean isTransacaoAtiva() {
		return manager.getTransaction().isActive();
	}
	
}
