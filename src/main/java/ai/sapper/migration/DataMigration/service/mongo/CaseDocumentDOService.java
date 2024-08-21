//package ai.sapper.migration.DataMigration.service.mongo;
//
//import ai.sapper.migration.DataMigration.Repository.mongo.CaseDocumentRepository;
//import ai.sapper.migration.DataMigration.Repository.mongo.DataMigrationRepository;
//import ai.sapper.migration.DataMigration.Repository.ReadService;
//import ai.sapper.migration.DataMigration.common.Migration;
//import ai.sapper.migration.DataMigration.constants.CaseType;
//import ai.sapper.migration.DataMigration.model.mongo.CaseDocumentDO;
//import ai.sapper.migration.DataMigration.model.mongo.DataMigration;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.Instant;
//import java.time.LocalDateTime;
//import java.time.OffsetDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@Slf4j
//public class CaseDocumentDOService implements Migration {
//
//    @Autowired
//    CaseDocumentRepository caseDocumentRepository;
//
//    @Autowired
//    DataMigrationRepository dataMigrationRepository;
//
//    @Autowired
//    ReadService readService;
//
////    @Override
////    public void migrate() {
////        log.info("Inside migrate method");
//////        Optional<CaseDocumentDO> optionalCaseDocumentDO = caseDocumentRepository.findByCaseIdAndType("C2308-2516", CaseType.valueOf("NORMALIZATION"));
////        DataMigration dataMigration = dataMigrationRepository.findByCollectionName("CaseDocument");
////        String id = dataMigration.getLastProcessedId();
////        List<CaseDocumentDO> caseDocumentDOList;
////        if(id == null){
////            caseDocumentDOList = caseDocumentRepository.findByIdAfterOrderByCreatedDateAsc(id);
////        }
////        List<CaseDocumentDO> caseDocumentDOList = caseDocumentRepository.findByIdAfterOrderByCreatedDateAsc(id);
//////        if (optionalCaseDocumentDO.isEmpty()) {
//////            log.info("Empty caseDocument got");
//////            return;
//////        }
//////        CaseDocumentDO caseDocumentDO = optionalCaseDocumentDO.get();
//////        log.info("Case Document got is {}",caseDocumentDO);
////        log.info("Outside migrate method");
////    }
//
//    private Date convertStringtoDate(String lastProcessedId) {
//
//        // Parse the string to OffsetDateTime
//        OffsetDateTime odt = OffsetDateTime.parse(lastProcessedId, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
//
//        // Convert to Instant
//        Instant instant = odt.toInstant();
//
//        // Convert to Date
//        Date date = Date.from(instant);
//
//        return date;
//    }
//
//    @Override
//    public void migrate() {
//       List<CaseDocumentDO> caseDocumentDOList=  readService.findDocumentsSorted( CaseDocumentDO.class,
//                "case_document",
//                "createdDate",
//                true,
//                10);
//       int size = caseDocumentDOList.size();
//       log.info("Case document got : {}", caseDocumentDOList);
//       log.info("Case document got of size : {}", size);
//    }
//}
