package ai.sapper.migration.DataMigration.service;

import ai.sapper.migration.DataMigration.Repository.mongo.DataMigrationRepository;
import ai.sapper.migration.DataMigration.Repository.ReadService;
import ai.sapper.migration.DataMigration.model.mongo.Case;
import ai.sapper.migration.DataMigration.model.mongo.DataMigration;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;
import java.util.List;

@Component
@Slf4j
public class DataMigrationService {
    private static final List<String> models = List.of("Case","CaseDocumentDO","COALabel","COA","CaseMerge", "SapperRule","Status","AuditEntity","AuditSnapshot","AuditSnapshotOriginal","Config");


    @Value("${class.path}")
    private String serviceClassPath;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    DataMigrationRepository dataMigrationRepository;

    @Autowired
    ReadService readService;


    @PostConstruct
    public void  start(){
        for(String service : models){
            String classPath = serviceClassPath + "." + service;
            try {
                    Object caseObj = applicationContext.getBean(Class.forName(classPath));
                    log.info("Inside PostConstruct method");
                    DataMigration dataMigration = dataMigrationRepository.findByCollectionName(service);
                    String lastProcessedId = null;
                    if(dataMigration == null){
                        System.out.println("Its null");
                    }else{
                        lastProcessedId = dataMigration.getLastProcessedId();
                        System.out.println("Its not null");
                        System.out.println(dataMigration.getCollectionName());
                        System.out.println(dataMigration.getLastProcessedId());
                        System.out.println(dataMigration.getFailedIds());
                    }
                    Method read = caseObj.getClass().getMethod("read", String.class);
//                    Method castList = caseObj.getClass().getMethod("castList", List.class);
                    List<Object> caseList = (List<Object>) read.invoke(caseObj,lastProcessedId);
//                    System.out.println("caseList size = "+ caseList.size());
//                    List<Case> caseListNew = (List<Case>) castList.invoke(caseObj,caseList);
//                    if(caseListNew.size() ==0){
//                        System.out.println("NO DATA IN LIST");
//                        return;
//                    }
//                    readService.updateLastProcessedId(service, caseListNew.get(caseListNew.size()-1).getId());
                if(caseList.size()>0){
                    log.info("Case list got : {}", caseList);
                    log.info("Got case from model : {}",caseList.get(0));
                    log.info("Outside PostConstruct method");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
