package ai.sapper.migration.DataMigration.Repository.postgres;


import ai.sapper.migration.DataMigration.constants.ConfigType;
import ai.sapper.migration.DataMigration.model.postgres.CaseDocumentDO;
import ai.sapper.migration.DataMigration.model.postgres.Config;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PostgresRepository {

    @PersistenceContext
    private EntityManager entityManager;


    public void save(Object object){
        entityManager.persist(object);
    }


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


    public void updateConfig(Object object) {
        entityManager.merge(object);
    }



    public void saveOrUpdateRuleRuntimeData(CaseDocumentDO caseDocumentDO) throws Exception {
        try {

            CaseDocumentDO existingData = findCaseDocumentByIdAndType(caseDocumentDO.getId().getId(),caseDocumentDO.getId().getType());

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



    public void saveOrUpdateCaseDocumentDO(CaseDocumentDO caseDocumentDO) {
        try {
            CaseDocumentDO existingData = findCaseDocumentByIdAndType(caseDocumentDO.getId().getId(),caseDocumentDO.getId().getType());

            if (existingData != null) {
                if (caseDocumentDO.getCreatedTime() > existingData.getCreatedTime()) {
                    existingData.setVersion(caseDocumentDO.getVersion());
                    existingData.setData(caseDocumentDO.getData());
                    existingData.setCreatedTime(caseDocumentDO.getCreatedTime());
                    existingData.setUpdatedTime(caseDocumentDO.getUpdatedTime());
                    existingData.setCreatedBy(caseDocumentDO.getCreatedBy());
                    existingData.setLastModifiedBy(caseDocumentDO.getLastModifiedBy());
                    entityManager.merge(existingData);
                }
            } else {
                entityManager.persist(caseDocumentDO);
            }
        } catch (Exception e) {
            log.error("Error saving or updating CaseDocumentDO: {}", e.getMessage(), e);
            throw new RuntimeException("Save or update failed", e);
        }
    }


    public CaseDocumentDO findCaseDocumentByIdAndType(String id, String type) {
        try {
            String query = "SELECT c FROM CaseDocumentDO c WHERE c.id.id = :id AND c.id.type = :type";
            return entityManager.createQuery(query, CaseDocumentDO.class)
                    .setParameter("id", id)
                    .setParameter("type", type)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }


}
