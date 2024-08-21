package ai.sapper.migration.DataMigration.model.mongo;

import lombok.Builder;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.Map;

@Data
@Builder
public class AuditDetail implements Serializable {

    private Map<String, String> previous;

    private Map<String, String> current;

    @Nullable
    private Map<String, String> attributes;

}
