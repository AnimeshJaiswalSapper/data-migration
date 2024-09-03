package ai.sapper.migration.DataMigration.service.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReadService {

    @Autowired
    @Qualifier("primaryMongoTemplate")
    private MongoTemplate mongoTemplate;

    @Autowired
    @Qualifier("secondaryMongoTemplate")
    private MongoTemplate secondaryMongoTemplate;

    @Value("${batch}")
    int batch;

    public <T> List<T> findDocumentsSorted(Class<T> modelClass, String collectionName, String sortByField,
                                           Date lastProcessedDate, String lastProcessedId, Boolean isDate) {
        Query query = new Query();
        query.with(Sort.by(Sort.Direction.ASC, sortByField));

        if (isDate && lastProcessedDate != null) {
            query.addCriteria(Criteria.where(sortByField).gt(lastProcessedDate));
        } else if (lastProcessedId != null) {
            query.addCriteria(Criteria.where(sortByField).gt(lastProcessedId));
        }

        if (batch > 0) {
            query.limit(batch);
        }

        return mongoTemplate.find(query, modelClass, collectionName);
    }

    public <T> List<T> findDocuments(Class<T> modelClass, String collectionName, String sortByField, String caseId, String caseType
            , String filter1, String filter2) {

        Query query = new Query();
        query.with(Sort.by(Sort.Direction.ASC, sortByField));

        if (caseType != null)
            query.addCriteria(Criteria.where(filter1).is(caseId).and(filter2).is(caseType));
        else
            query.addCriteria(Criteria.where(filter1).is(caseId));

        return mongoTemplate.find(query, modelClass, collectionName);
    }


    public <T> List<T> findDocuments(Class<T> modelClass, String collectionName, String sortByField, String caseId
            , String filter1) {

        Query query = new Query();
        query.with(Sort.by(Sort.Direction.ASC, sortByField));

        query.addCriteria(Criteria.where(filter1).is(caseId));

        return secondaryMongoTemplate.find(query, modelClass, collectionName);
    }

}
