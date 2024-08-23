//package ai.sapper.migration.DataMigration.model.postgres;
//
//import ai.sapper.migration.DataMigration.common.postgres.BaseEntity;
//import ai.sapper.migration.DataMigration.constants.ConfigLevel;
//import ai.sapper.migration.DataMigration.constants.ConfigType;
//import ai.sapper.migration.DataMigration.convertor.ReferenceTypeConverter;
//import jakarta.persistence.*;
//import jakarta.persistence.Entity;
//import lombok.*;
//import org.hibernate.annotations.ColumnTransformer;
//import org.springframework.data.annotation.CreatedBy;
//import org.springframework.data.annotation.CreatedDate;
//import org.springframework.data.annotation.LastModifiedBy;
//import org.springframework.data.annotation.LastModifiedDate;
//
//import java.time.ZonedDateTime;
//import java.util.Map;
//
//@Getter
//@Setter
//@Builder
//@AllArgsConstructor(access = AccessLevel.PACKAGE)
//@NoArgsConstructor(access = AccessLevel.PUBLIC)
//@Entity
//@Table(name = "config")
//public class Config {
//    /**
//     *
//     */
//    private static final long serialVersionUID = 1L;
//    @Id
//    private String id;
//    @Enumerated(EnumType.STRING)
//    @Column(name = "type")
//    private ConfigType type;
//    @Enumerated(EnumType.STRING)
//    @Column(name = "level")
//    private ConfigLevel level;
//    @Column(name = "status")
//    private boolean status;
//    @Column(name = "user_id")
//    private String userId;
//    @Convert(converter = ReferenceTypeConverter.class)
//    @Column(columnDefinition = "jsonb")
//    @ColumnTransformer(write = "?::jsonb")
//    private Map<String, ?> meta;
//    @CreatedBy
//    private String createdBy;
//    @CreatedDate
//    private ZonedDateTime createdDate;
//    @LastModifiedBy
//    private String lastModifiedBy;
//    @LastModifiedDate
//    private ZonedDateTime lastModifiedDate = ZonedDateTime.now();
//}
