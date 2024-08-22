package ai.sapper.migration.DataMigration.model.mongo;

import ai.sapper.migration.DataMigration.service.mongo.ReadService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.util.Date;
import java.util.List;


@Document("active_loans")
@CompoundIndexes({
        @CompoundIndex(name = "loan_collateral_database_idx", def = "{'loanNumber': 1, 'collateralId': 1, 'database': 1}")
})
@Component
@ToString
public class ActiveLoans {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @Indexed(unique = true)
    private String id;

    private String loanNumber;
    private String collateralId;
    private String database;
    private String osarRequired;
    private String startDate;
    private String endDate;

    @Autowired
    ReadService readService;


    public List<ActiveLoans> read(Date lastProcessedDate, String lastProcessedId) {
        return  readService.findDocumentsSorted(ActiveLoans.class,
                "active_loans",
                "id",
                true,
                lastProcessedDate,
                lastProcessedId,
                false
        );
    }
}
