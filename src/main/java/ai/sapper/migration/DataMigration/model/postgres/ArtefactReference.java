package ai.sapper.migration.DataMigration.model.postgres;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
@Entity
@Table(name = "cm_artefact_reference")
public class ArtefactReference implements Serializable {

    @Id
    @Column(name = "case_id")
    private String caseId;

    @Id
    @Column(name = "collection")
    private String collection;

    @Id
    @Column(name = "doc_id")
    private String docId;

    @Column(name = "artefact_type")
    private String artefactType;
}

