package ai.sapper.migration.DataMigration.model.postgres;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity
@Table(name = "cm_documents")
//@IdClass(DocumentId.class)
public class Document {

    @Id
    @Column(name = "doc_id")
    private String docId;

    @Id
    @Column(name = "collection")
    private String collection ;

    @Column(name = "parent_doc_id")
    private String parentDocId;

    @Column(name = "case_id")
    private String caseId;

    @Column(name = "doc_name")
    private String docName;

    @Column(name = "doc_source_path", length = 1024)
    private String docSourcePath;

    @Column(name = "doc_state")
    private String docState;

    @Column(name = "doc_type")
    private String docType;

    @Column(name = "mime_type")
    private String mimeType;

    @Column(name = "uri", length = 2048)
    private String uri;

    @Column(name = "document_count")
    private int documentCount = 0;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "time_created")
    private Long timeCreated;

    @Column(name = "time_updated")
    private Long timeUpdated;

    @Column(name = "passkey")
    private String passkey;

    @Column(name = "error", columnDefinition = "TEXT"   )
    private String error;

    @Column(name = "properties", columnDefinition = "TEXT")
    private String properties;

    @Column(name = "version")
    private int version;
}

