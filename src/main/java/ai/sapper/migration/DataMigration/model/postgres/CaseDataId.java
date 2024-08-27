package ai.sapper.migration.DataMigration.model.postgres;

import ai.sapper.migration.DataMigration.constants.SpDocumentType;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "@class"
)
public class CaseDataId implements Serializable {
    @Column(
            name = "case_id"
    )
    private String id;

    @Column(
            name = "type"
    )
    private String type;

    public CaseDataId() {
    }

    public CaseDataId(@NonNull String id, SpDocumentType type) {
        if (id == null) {
            throw new NullPointerException("id is marked non-null but is null");
        } else {
            this.id = id;
            if(type!=null){
                this.type = type.name();
            }else{
                this.type=null;
            }

        }
    }
}
