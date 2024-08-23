//package ai.sapper.migration.DataMigration.model.postgres;
//
//import ai.sapper.migration.DataMigration.service.mongo.ReadService;
//import jakarta.persistence.*;
//import jakarta.persistence.Entity;
//import lombok.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.index.Indexed;
//import org.springframework.data.mongodb.core.mapping.Document;
//import org.springframework.stereotype.Component;
//
//import java.io.Serial;
//import java.io.Serializable;
//import java.util.Date;
//import java.util.List;
//import java.util.UUID;
//
//import static ai.sapper.migration.DataMigration.constants.Collections.ID;
//
//@Getter
//@Setter
//@Builder
//@AllArgsConstructor(access = AccessLevel.PACKAGE)
//@Entity
//@Table(name = "data_mine_report")
//public class DataMineReport implements Serializable {
//
//    @Id
//    @Column(name = "id")
//    private String id;
//
//    @Column(name = "database")
//    private String database;
//
//    @Column(name = "collateral_id")
//    private String collateralId;
//
//    @Column(name = "loan_number")
//    private String loanNumber;
//
//    @Column(name = "prop_seq")
//    private String propSeq;
//
//    @Column(name = "prop_type")
//    private String propType;
//
//    @Column(name = "prop_desc1")
//    private String propDesc1;
//
//    @Column(name = "form_type")
//    private String formType;
//
//    @Column(name = "stmt_freq")
//    private String stmtFreq;
//
//    @Column(name = "stmt_purpose")
//    private String stmtPurpose;
//
//    @Column(name = "stmt_date")
//    private String stmtDate;
//
//    @Column(name = "period_begin")
//    private String periodBegin;
//
//    @Column(name = "period_end")
//    private String periodEnd;
//
//    @Column(name = "annualized")
//    private String annualized;
//
//    @Column(name = "data_type")
//    private String dataType;
//
//    public DataMineReport() {
//        this.id = UUID.randomUUID().toString();
//    }
//
//    public DataMineReport(@NonNull String id) {
//        if (id == null) {
//            throw new NullPointerException("id is marked non-null but is null");
//        } else {
//            this.id = id;
//        }
//    }
//
//}
