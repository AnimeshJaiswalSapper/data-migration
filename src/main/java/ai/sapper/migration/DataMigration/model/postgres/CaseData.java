//package ai.sapper.migration.DataMigration.model.postgres;
//
//import ai.sapper.migration.DataMigration.convertor.JsonbConverter;
//import com.fasterxml.jackson.annotation.JsonTypeInfo;
//import com.unifiedframework.model.block.CaseDocument;
//import jakarta.persistence.*;
//import jakarta.persistence.Entity;
//import lombok.*;
//import lombok.extern.slf4j.Slf4j;
//import org.hibernate.annotations.ColumnTransformer;
//import org.springframework.stereotype.Component;
//
//@Getter
//@Setter
//@Builder
//@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY,
//        property = "@class")
//@Entity
//@Table(name = "cm_udp_extraction_data")
//@Slf4j
//@Component("cm_udp_extraction_data")
//@AllArgsConstructor(access = AccessLevel.PACKAGE)
//@NoArgsConstructor(access = AccessLevel.PUBLIC)
//public class CaseData {
//    @EmbeddedId
//    private CaseDataId id;
//    @Column(name = "extraction_json" , columnDefinition = "jsonb")
//    @Convert(converter = JsonbConverter.class)
//    @ColumnTransformer(write = "?::jsonb")
//    private CaseDocument data;
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
//
//
//
//}
