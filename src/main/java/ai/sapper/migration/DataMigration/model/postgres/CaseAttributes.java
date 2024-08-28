package ai.sapper.migration.DataMigration.model.postgres;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class CaseAttributes {
    @Column(name = "financial_indicator")
    public String financialIndicator;

    @Column(name = "database")
    public String database;

    @Column(name = "dateType")
    public String dateType;

    @Column(name = "document_type")
    public String documentType;

    @Column(name = "purpose")
    public String purpose;

    @Column(name = "start_date")
    public long startDate;

    @Column(name = "end_date")
    public long endDate;

    @Column(name = "frequency_type")
    public String frequencyType;

    @Column(name = "language")
    public String language;
}
