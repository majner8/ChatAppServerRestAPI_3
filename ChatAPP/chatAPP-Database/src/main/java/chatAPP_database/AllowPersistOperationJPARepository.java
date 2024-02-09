package chatAPP_database;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

public interface AllowPersistOperationJPARepository<T> {

	public void persist(T entityToPersist);
	
	@Component
	public static class AllowPersistOperationJPARepositoryImplemet<T> implements AllowPersistOperationJPARepository<T>{

		@PersistenceContext
		private EntityManager manager;
		@Override
		public void persist(T entityToPersist) {
			
			this.manager.persist(entityToPersist);
		}
		
	}
}
