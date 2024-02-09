package chatAPP_database;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

public interface AllowPersistOperationJPARepository<T> {

	public void persist(T entityToPersist);
	
	
	public static class AllowPersistOperationJPARepositoryImplemet<T> implements AllowPersistOperationJPARepository<T>{

		//@PersistenceContext
	//	private EntityManager manager;
		@Override
		public void persist(T entityToPersist) {
			
//			this.manager.persist(entityToPersist);
		}
		
	}
}
