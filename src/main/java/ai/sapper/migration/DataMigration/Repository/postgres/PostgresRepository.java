package ai.sapper.migration.DataMigration.Repository.postgres;


import ai.sapper.migration.DataMigration.constants.ConfigType;
import ai.sapper.migration.DataMigration.model.postgres.CaseDocumentDO;
import ai.sapper.migration.DataMigration.model.postgres.Config;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PostgresRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void save(Object object){
        entityManager.persist(object);
    }

    @Transactional
    public Config findConfigByType(ConfigType type) {
        try {
            String query = "SELECT c FROM Config c WHERE c.type = :type AND c.userId = :userId";
            return entityManager.createQuery(query, Config.class)
                    .setParameter("type", type)
                    .setParameter("userId", "CASE_COMPANY")
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional
    public void updateConfig(Object object) {
        entityManager.merge(object);
    }

    @Transactional
    public void saveOrUpdateRuleRuntimeData(CaseDocumentDO caseDocumentDO) {
        try {

            CaseDocumentDO existingData = findCaseDocumentById(caseDocumentDO.getId().getId());

            if (existingData != null) {
                if (caseDocumentDO.getVersion() > existingData.getVersion()) {
                    existingData.setVersion(caseDocumentDO.getVersion());
                    existingData.setData(caseDocumentDO.getData());
                    existingData.setCreatedTime(caseDocumentDO.getCreatedTime());
                    existingData.setUpdatedTime(caseDocumentDO.getUpdatedTime());
                    entityManager.merge(existingData);
                }
            } else {
                entityManager.persist(caseDocumentDO);
            }
        } catch (Exception e) {
            log.error("Error saving or updating RuleRuntimeData: {}", e.getMessage(), e);
            throw new RuntimeException("Save or update failed", e);
        }
    }

    @Transactional
    public CaseDocumentDO findCaseDocumentById(String id) {
        try {
            String query = "SELECT c FROM CaseDocumentDO c WHERE c.id.id = :id AND c.id.type = :type";
            return entityManager.createQuery(query, CaseDocumentDO.class)
                    .setParameter("id", id)
                    .setParameter("type", "QaCheckOutput")
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
