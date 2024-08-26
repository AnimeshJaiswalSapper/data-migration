package ai.sapper.migration.DataMigration.model.postgres;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "export_request")
@Slf4j
@Builder
@Component("export_request")
public class Status {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "file_path")
    private String taskId;

    @Column(name = "requested_by")
    private String username;

    @Column(name = "status")
    private String status;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "type")
    private String type;


    public Status convert(Object mongoDocument) {
        try {
            if (mongoDocument instanceof ai.sapper.migration.DataMigration.model.mongo.Status mongoStatus) {

                Status caseDoc = Status.builder()
                        .id(mongoStatus.getId())
                        .taskId(mongoStatus.getTaskId())
                        .username(mongoStatus.getUsername())
                        .status(mongoStatus.getStatus())
                        .build();

                return caseDoc;
            }
        } catch (Exception e) {
            log.error("Error converting MongoDB document: {}", e.getMessage(), e);
            throw new RuntimeException("Conversion failed", e);
        }
        return null;
    }

}
