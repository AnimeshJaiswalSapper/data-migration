//package ai.sapper.migration.DataMigration.service.mongo;
//
//import ai.sapper.migration.DataMigration.Repository.mongo.COALabelRepository;
//import ai.sapper.migration.DataMigration.Repository.ReadService;
//import ai.sapper.migration.DataMigration.common.Migration;
//import ai.sapper.migration.DataMigration.model.mongo.COALabel;
//import ai.sapper.migration.DataMigration.model.mongo.CaseDocumentDO;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@Slf4j
//public class COALabelService implements Migration {
//    @Autowired
//    COALabelRepository coaLabelRepository;
//
//    @Autowired
//    ReadService readService;
//
////    @Override
////    public void migrate() {
////        log.info("Inside Migrate method");
////        List<COALabel> coaLabel = coaLabelRepository.findByName("Less Vacancy Loss");
////        log.info("coa label got : {}",coaLabel);
////        log.info("Outside Migrate method");
////    }
//
//    @Override
//    public void migrate() {
//        List<COALabel> coaLabelList=  readService.findDocumentsSorted( COALabel.class,
//                "cOALabel",
//                "createdDate",
//                true,
//                10);
//        int size = coaLabelList.size();
//        log.info("coa label got : {}", coaLabelList);
//        log.info("coa label got of size : {}", size);
//    }
//}
