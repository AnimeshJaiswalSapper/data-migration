//package ai.sapper.migration.DataMigration.model.postgres;
//
//import ai.sapper.migration.DataMigration.common.postgres.BaseEntity;
//import ai.sapper.migration.DataMigration.constants.Status;
//import ai.sapper.migration.DataMigration.convertor.ConditionConvertor;
//import ai.sapper.migration.DataMigration.convertor.EntityConvertor;
//import com.fasterxml.jackson.annotation.JsonTypeInfo;
//import jakarta.persistence.*;
//import lombok.Data;
//import lombok.NonNull;
//
//import java.util.List;
//import java.util.Map;
//import java.util.UUID;
//
//@Data
//@JsonTypeInfo(
//        use = JsonTypeInfo.Id.CLASS,
//        include = JsonTypeInfo.As.PROPERTY,
//        property = "@class"
//)
//@jakarta.persistence.Entity
//@Table(name = "coa_label")
//public class COALabel extends BaseEntity {
//    @Id
//    @Column(name = "id")
//    private String id;
//
//    @ManyToOne(
//            cascade = {CascadeType.ALL},
//            fetch = FetchType.EAGER
//    )
//    @JoinColumn(
//            name = "coa_id",
//            referencedColumnName = "coa_id"
//    )
//    private COA coa;
//
//    @Column(name = "name")
//    private String name;// used
//
//    @Column(name = "expression")
//    private String expression; // its being used.
//
//    @Column(name = "expression_entities")
//    @Convert(converter = EntityConvertor.class)
//    private List<Entity> expressionEntities; // no usage found in CBRE and all fields are null in PG
//    @Column(name = "status")
//    @Enumerated(EnumType.STRING)
//    private Status status; //used
//    @Column(name = "condition_exp")
//    private String conditionExp;// no usage found in CBRE , it contains expression =
//
//    @Column(name = "condition")
//    @Convert(converter = ConditionConvertor.class)
//    private Map<String, Condition> condition; // used , but in capone it stores in different format
//    @Column(name = "parent_id")
//    private String parentId;// inside CBRE code we use it but on collection it's not present.
//
//    @Column(name = "mandatory")
//    private boolean mandatory; // used
//
//    @Column(name = "priority")
//    private int priority; // used
//
//    public COALabel() {
//        this.id = UUID.randomUUID().toString();
//    }
//
//    public COALabel(@NonNull String id) {
//        if (id == null) {
//            throw new NullPointerException("id is marked non-null but is null");
//        } else {
//            this.id = id;
//        }
//    }
//
//}
