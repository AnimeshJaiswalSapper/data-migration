package ai.sapper.migration.DataMigration.service.mongo;

import ai.sapper.migration.DataMigration.model.mongo.CaseDocumentDO;
import ai.sapper.migration.DataMigration.model.mongo.DataMigration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReadService {

    @Autowired
    private  MongoTemplate mongoTemplate;

    @Value("${batch}")
    int batch;

    public <T> List<T> findDocumentsSorted(Class<T> modelClass, String collectionName, String sortByField, boolean ascending,
                                           Date lastProcessedDate, String lastProcessedId, Boolean isDate) {
        Query query = new Query();
        Sort.Direction direction = ascending ? Sort.Direction.ASC : Sort.Direction.DESC;
        query.with(Sort.by(direction, sortByField));

        String dateField = "createdDate";

        if (isDate && lastProcessedDate != null) {
            boolean createdDateExists = mongoTemplate.exists(new Query(Criteria.where("createdDate").exists(true)), modelClass, collectionName);
            dateField = createdDateExists ? "createdDate" : "createdAt";
            query.addCriteria(Criteria.where(dateField).gt(lastProcessedDate));
        } else if (lastProcessedId != null) {
            query.addCriteria(Criteria.where("id").gt(lastProcessedId));
        }

        if (batch > 0) {
            query.limit(batch);
        }

        return mongoTemplate.find(query, modelClass, collectionName);
    }

}
