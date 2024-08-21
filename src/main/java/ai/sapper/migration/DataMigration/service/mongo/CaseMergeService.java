//package ai.sapper.migration.DataMigration.service.mongo;
//
//import ai.sapper.migration.DataMigration.Repository.mongo.CaseMergeRepository;
//import ai.sapper.migration.DataMigration.Repository.ReadService;
//import ai.sapper.migration.DataMigration.common.Migration;
//import ai.sapper.migration.DataMigration.constants.CaseType;
//import ai.sapper.migration.DataMigration.model.mongo.Case;
//import ai.sapper.migration.DataMigration.model.mongo.CaseDocumentDO;
//import ai.sapper.migration.DataMigration.model.mongo.CaseMerge;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@Slf4j
//public class CaseMergeService implements Migration {
//
//    @Autowired
//    CaseMergeRepository caseMergeRepository;
//
//    @Autowired
//    ReadService readService;
//
//
////    @Override
////    public void migrate() {
////        log.info("Inside migrate method");
////        List<CaseMerge> caseMerges = caseMergeRepository.findByMergeCaseId("C2401-3500");
////        log.info("Got CASE MERGE : {}", caseMerges.get(0));
////        log.info("Case Merge Size : {}", caseMerges.size());
////        log.info("Outside migrate method");
////
////    }
//
//    @Override
//    public void migrate() {
//        List<CaseMerge> caseMergeList=  readService.findDocumentsSorted( CaseMerge.class,
//                "caseMerge",
//                "createdDate",
//                true,
//                10);
//        int size = caseMergeList.size();
//        log.info("Case Merge got : {}", caseMergeList);
//        log.info("Case Merge got of size: {}", size);
//    }
//}
