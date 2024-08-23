//package ai.sapper.migration.DataMigration.model.postgres;
//
//import ai.sapper.migration.DataMigration.constants.SpDocumentType;
//import com.fasterxml.jackson.annotation.JsonTypeInfo;
//import jakarta.persistence.*;
//import jakarta.persistence.Entity;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.Setter;
//import org.hibernate.annotations.ColumnTransformer;
//
//import java.time.ZoneId;
//import java.time.ZonedDateTime;
//import java.util.Date;
//
//@Getter
//@Setter
//@Builder
//@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY,
//        property = "@class")
//@Entity
//@Table(name = "cm_udp_extraction_data")
//public class CaseData {
//    @EmbeddedId
//    private CaseDataId id;
//    @Column(name = "extraction_json" , columnDefinition = "jsonb")
//    @ColumnTransformer(write = "?::jsonb")
//    private String data;
//    @Column(
//            name = "time_created"
//    )
//    private long createdTime;
//    @Column(
//            name = "time_updated"
//    )
//    private long updatedTime;
//    @Column(
//            name = "version"
//    )
//    @Version
//    private Integer version;
//
//    public CaseData convert(Object mongoDocument) {
//        try {
//            if (mongoDocument instanceof ai.sapper.migration.DataMigration.model.mongo.CaseDocumentDO mongoCaseData) {
//
//                CaseData config = CaseData.builder()
//                        .id(new CaseDataId(mongoCaseData.getCaseId(), SpDocumentType.fromCaseType(mongoCaseData.getType())))
//                        .data(String.valueOf(mongoCaseData.getCaseDocument()))
//                        .createdTime(mongoCaseData.get())
//                        .updatedTime(mongoCaseData.isStatus())
//                        .version(mongoCaseData.getUserId())
//                        .build();
//
//                config.setCreatedBy(mongoConfig.getCreatedBy());
//                config.setCreatedDate(convertToZonedDateTime(mongoConfig.getCreatedDate()));
//                config.setLastModifiedBy(mongoConfig.getLastModifiedBy());
//                config.setLastModifiedDate(convertToZonedDateTime(mongoConfig.getLastModifiedDate()));
//
//                return config;
//            }
//        }  catch (Exception e) {
//            log.error("Error converting Config document: {}", e.getMessage(), e);
//            throw e;
//        }
//        return null;
//    }
//
//
//    private ZonedDateTime convertToZonedDateTime(Date date) {
//        if (date != null) {
//            return ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
//        }
//        return null;
//    }
//}
