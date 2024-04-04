package chatAPP_database;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import chatAPP_CommontPart.Log4j2.Log4j2;



@NoRepositoryBean
public interface CustomJpaRepository<T,ID> extends JpaRepository<T,ID> {

	/** Metod return saved entity, aling by primaryKey
	 * @throws RunTimeException EntityNotFoundException if message was not found with assign primaryKey */
	default T findByPrimaryKey(ID primaryKey)  {
		Optional<T> entity=this.findById(primaryKey);
		if(entity.isEmpty())throw new EntityNotFoundException();

		return entity.get();
	}

	default T InsertOrIgnore(T entity,ID primaryKey) {
		try {
			this.save(entity);
			return entity;
		} catch (DataIntegrityViolationException e) {
			Log4j2.log.warn(Log4j2.MarkerLog.Database.getMarker(),"Ignore inserting operation, entity has been exist "+e);
			return this.findById(primaryKey).get();
		}

	}


}
