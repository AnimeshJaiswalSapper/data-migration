package ai.sapper.migration.DataMigration.Repository.postgres;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class PostgresRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void save(Object object){
        entityManager.persist(object);
    }


}
