package ai.sapper.migration.DataMigration.model.mongo;


public enum DocumentType {
    PDF,
    ZIP,
    CSV,
    TSV,
    PSV,
    TXT,
    XLS,
    XLSX,
    FDV;

    private DocumentType() {
    }
}
