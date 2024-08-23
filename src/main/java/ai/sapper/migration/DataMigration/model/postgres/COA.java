//package ai.sapper.migration.DataMigration.model.postgres;
//
//import ai.sapper.migration.DataMigration.common.postgres.BaseEntity;
//import ai.sapper.migration.DataMigration.constants.Status;
//import com.fasterxml.jackson.annotation.JsonTypeInfo;
//import jakarta.persistence.*;
//import jakarta.persistence.Entity;
//import lombok.*;
//import lombok.experimental.SuperBuilder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.time.ZoneId;
//import java.time.ZonedDateTime;
//import java.util.Date;
//import java.util.UUID;
//
//@Getter
//@Setter
//@JsonTypeInfo(
//        use = JsonTypeInfo.Id.CLASS,
//        include = JsonTypeInfo.As.PROPERTY,
//        property = "@class"
//)
//@AllArgsConstructor(access = AccessLevel.PACKAGE)
//@NoArgsConstructor(access = AccessLevel.PUBLIC)
//@Entity
//@Table(name = "coa")
//@Builder
//@Component("coa_postgres")
//public class COA extends BaseEntity {
//    @Id
//    @Column(name = "coa_id")
//    protected String coaId;
//
//    @Column(name = "name")
//    private String name;
//
//    @Column(name = "status")
//    @Enumerated(EnumType.STRING)
//    private Status status;
//
//    public COA() {
//        this.coaId = UUID.randomUUID().toString();
//    }
//
//    public COA(@NonNull String id) {
//        if (id == null) {
//            throw new NullPointerException("id is marked non-null but is null");
//        } else {
//            this.coaId = id;
//        }
//    }
//}
