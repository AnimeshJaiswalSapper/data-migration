//package ai.sapper.migration.DataMigration.model.postgres;
//
//import com.fasterxml.jackson.annotation.JsonTypeInfo;
//import jakarta.persistence.*;
//import jakarta.persistence.Entity;
//import lombok.Getter;
//import lombok.Setter;
//import org.hibernate.annotations.ColumnTransformer;
//
//@Getter
//@Setter
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
//}
