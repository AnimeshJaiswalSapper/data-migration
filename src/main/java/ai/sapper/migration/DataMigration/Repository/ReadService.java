package ai.sapper.migration.DataMigration.Repository;

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

    public  <T,V> List<T> findDocumentsSorted(Class<T> modelClass, String collectionName, String sortByField, boolean ascending, V lastProcessedDate) {
        Query query = new Query();
        Sort.Direction direction = ascending ? Sort.Direction.ASC : Sort.Direction.DESC;
        query.with(Sort.by(direction, sortByField));

        if (lastProcessedDate != null) {
            query.addCriteria(Criteria.where("createdDate").gt(lastProcessedDate));
        }

        if (batch > 0) {
            query.limit(batch);
        }

        return mongoTemplate.find(query, modelClass, collectionName);
    }

    public void updateLastProcessedDate(String collectionName, Date newLastProcessedDate) {
        Query query = new Query(Criteria.where("collectionName").is(collectionName));
        Update update = new Update().set("lastProcessedDate", newLastProcessedDate);

        mongoTemplate.updateFirst(query, update, DataMigration.class);
    }
}
