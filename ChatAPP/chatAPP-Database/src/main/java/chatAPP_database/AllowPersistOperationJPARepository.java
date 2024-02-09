package chatAPP_database;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

public interface AllowPersistOperationJPARepository<T> {

	public void persist(T entityToPersist);
	
	@Repository
	@Transactional
	public static class AllowPersistOperationJPARepositoryImpl<T> implements AllowPersistOperationJPARepository<T>{

		@PersistenceContext
		private EntityManager manager;
		@Override
		public void persist(T entityToPersist) {
			
			this.manager.persist(entityToPersist);
		}
		
	}
}
