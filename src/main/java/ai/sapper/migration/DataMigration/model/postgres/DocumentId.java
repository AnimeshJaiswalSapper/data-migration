package ai.sapper.migration.DataMigration.model.postgres;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class DocumentId implements Serializable {

    private String docId;
    private String collection;

    public DocumentId() {
    }

    public DocumentId(String docId, String collection) {
        this.docId = docId;
        this.collection = collection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocumentId that = (DocumentId) o;
        return Objects.equals(docId, that.docId) && Objects.equals(collection, that.collection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(docId, collection);
    }
}
