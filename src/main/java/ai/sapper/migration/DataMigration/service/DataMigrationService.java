package ai.sapper.migration.DataMigration.service;

import ai.sapper.migration.DataMigration.Repository.mongo.DataMigrationRepository;
import ai.sapper.migration.DataMigration.service.mongo.ReadService;
import ai.sapper.migration.DataMigration.model.mongo.COALabel;
import ai.sapper.migration.DataMigration.model.mongo.DataMigration;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class DataMigrationService {

        private static final List<String> models = List.of("Case","CaseDocumentDO","COALabel","COA","CaseMerge", "SapperRule","Status","AuditEntity","AuditSnapshot","AuditSnapshotOriginal","Config");
//    private static final List<String> models = List.of("COALabel");
//    private static final List<String> models = List.of("ActiveLoans","BaseOutput","DatabaseSequence","DataMineReport","IngestionAudit","IngestionConfiguration","RuleRuntimeData","TemplateMapping");



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
                log.info("---------STARTED DATA MIGRATION------");

                    Object serviceObj = applicationContext.getBean(Class.forName(classPath));

                    DataMigration dataMigration = dataMigrationRepository.findByCollectionName(service);

                    Date lastProcessedDate = null;
                    String lastProcessedId = null;

                    if(dataMigration == null){
                        System.out.println("Its null");
                    }else{
                        lastProcessedDate = dataMigration.getLastProcessedDate();
                        lastProcessedId = dataMigration.getLastProcessedId();
                        System.out.println(dataMigration.getCollectionName());
                        System.out.println(dataMigration.getLastProcessedId());
                        System.out.println(dataMigration.getFailedIds());
                    }


                    Method read = serviceObj.getClass().getMethod("read", Date.class,String.class);
//                    Method castList = serviceObj.getClass().getMethod("castList", List.class);
                    List<Object> caseList = (List<Object>) read.invoke(serviceObj,lastProcessedDate,lastProcessedId);
                    System.out.println("caseList size = "+ caseList.size());
                    List<COALabel> caseListNew = (List<COALabel>) castList.invoke(serviceObj,caseList);
                    if(caseListNew.size() ==0){
                        System.out.println("NO DATA IN LIST");
                        return;
                    }
                    if(dataMigration == null){
                        dataMigrationRepository.save(DataMigration.builder().collectionName(service).lastProcessedId(caseListNew.get(caseListNew.size()-1).getId())
                                .lastProcessedDate(caseListNew.get(caseListNew.size()-1).getCreatedDate())
                                .failedIds(new ArrayList<>())
                                .build());
                    }
                    readService.updateLastProcessedDate(service, caseListNew.get(caseListNew.size()-1).getCreatedDate());
                if(caseList.size()>0){
                    for(COALabel c : caseListNew){
                        log.info("case id {} name: {}",c.getId(), c.getName());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
