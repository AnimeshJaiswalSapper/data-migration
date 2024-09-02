package ai.sapper.migration.DataMigration.Repository.postgres;


import ai.sapper.migration.DataMigration.constants.ConfigType;
import ai.sapper.migration.DataMigration.model.postgres.Case;
import ai.sapper.migration.DataMigration.model.postgres.CaseDocumentDO;
import ai.sapper.migration.DataMigration.model.postgres.CaseMerge;
import ai.sapper.migration.DataMigration.model.postgres.Config;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class PostgresRepository {

    @PersistenceContext
    private EntityManager entityManager;


    public void save(Object object) {
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


    public void update(Object object) {
        entityManager.merge(object);
    }


    public void saveOrUpdateRuleRuntimeData(CaseDocumentDO caseDocumentDO) throws Exception {
        try {

            CaseDocumentDO existingData = findCaseDocumentByIdAndType(caseDocumentDO.getId().getId(), caseDocumentDO.getId().getType());

            if (existingData != null) {
                if (caseDocumentDO.getVersion() > existingData.getVersion()) {
                    existingData.setVersion(caseDocumentDO.getVersion());
                    existingData.setData(caseDocumentDO.getData());
                    existingData.setCreatedTime(caseDocumentDO.getCreatedTime());
                    existingData.setUpdatedTime(caseDocumentDO.getUpdatedTime());
                    update(existingData);
                    log.info("Updated CaseDocument of type 'QaCheckOutput' with the latest version. CaseDataId: {}", existingData.getId().getId());
                }
            } else {
                save(caseDocumentDO);
            }
        } catch (Exception e) {
            log.error("Error saving or updating RuleRuntimeData: with caseId : [{}] exception: [{}]", caseDocumentDO.getId().getId(), e.getMessage(), e);
            throw new RuntimeException("Save or update failed", e);
        }
    }


    public void saveOrUpdateCaseDocumentDO(CaseDocumentDO caseDocumentDO) {
        try {
            CaseDocumentDO existingData = findCaseDocumentByIdAndType(caseDocumentDO.getId().getId(), caseDocumentDO.getId().getType());

            if (existingData != null) {
                if (caseDocumentDO.getCreatedTime() > existingData.getCreatedTime()) {
                    existingData.setVersion(caseDocumentDO.getVersion());
                    existingData.setData(caseDocumentDO.getData());
                    existingData.setCreatedTime(caseDocumentDO.getCreatedTime());
                    existingData.setUpdatedTime(caseDocumentDO.getUpdatedTime());
                    existingData.setCreatedBy(caseDocumentDO.getCreatedBy());
                    existingData.setLastModifiedBy(caseDocumentDO.getLastModifiedBy());
                    update(existingData);
                    log.info("Updated CaseDocument of type [{}] with the latest createdDate. CaseDataId: {}", caseDocumentDO.getId().getType(), existingData.getId().getId());

                }
            } else {
                save(caseDocumentDO);
            }
        } catch (Exception e) {
            log.error("Error saving or updating CaseDocumentDO with caseId : [{}] exception : [{}]", caseDocumentDO.getId().getId(), e.getMessage(), e);
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

    public void saveOrUpdateCaseMerge(CaseMerge caseMerge) {
        try {
            String id = caseMerge.getOldCaseId();
            Case caseObj = findCaseById(id);
            if (caseObj == null) {
                log.info("Case not found for merging. Old Case ID: [{}], Merge Case ID: [{}]", caseMerge.getOldCaseId(), caseMerge.getMergeCaseId());
                return;
            }
            if (caseObj.getMergedCaseIds() != null) {
                List<String> mergeCaseIdList = caseObj.getMergedCaseIds();
                mergeCaseIdList.add(caseMerge.getMergeCaseId());
                update(caseObj);
                log.info("Added [{}] in the mergeCaseIdList of case id : [{}]", caseMerge.getMergeCaseId(), id);
            } else {
                List<String> mergeCaseIdList = new ArrayList<>();
                mergeCaseIdList.add(caseMerge.getMergeCaseId());
                caseObj.setMergedCaseIds(mergeCaseIdList);
                update(caseObj);
            }
        } catch (Exception e) {
            log.error("Error saving or updating CaseMerge: {}", e.getMessage(), e);
            throw new RuntimeException("Save or update failed", e);
        }
    }

    public Case findCaseById(String id) {
        try {
            String query = "SELECT c FROM Case c WHERE c.id = :id";
            return entityManager.createQuery(query, Case.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}
