//package ai.sapper.migration.DataMigration.model.postgres;
//
//import jakarta.persistence.*;
//import jakarta.persistence.Entity;
//import lombok.*;
//import org.springframework.data.annotation.Id;
//
//import java.io.Serializable;
//import java.util.UUID;
//
//@Getter
//@Setter
//@Builder
//@AllArgsConstructor(access = AccessLevel.PACKAGE)
//@Entity
//@Table(name = "active_loans", indexes = {
//        @Index(name = "loan_collateral_database_idx", columnList = "loan_number, collateral_id, database")
//})
//public class ActiveLoans implements Serializable {
//
//    @Id
//    @Column(name = "id")
//    private String id;
//
//    @Column(name = "loan_number", nullable = false)
//    private String loanNumber;
//
//    @Column(name = "collateral_id")
//    private String collateralId;
//
//    @Column(name = "database")
//    private String database;
//
//    @Column(name = "osar_required")
//    private String osarRequired;
//
//    @Column(name = "start_date")
//    private String startDate;
//
//    @Column(name = "end_date")
//    private String endDate;
//
//    public ActiveLoans() {
//        this.id = UUID.randomUUID().toString();
//    }
//
//    public ActiveLoans(@NonNull String id) {
//        if (id == null) {
//            throw new NullPointerException("id is marked non-null but is null");
//        } else {
//            this.id = id;
//        }
//    }
//
//}
//
