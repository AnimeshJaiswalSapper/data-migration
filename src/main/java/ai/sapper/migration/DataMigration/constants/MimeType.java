package ai.sapper.migration.DataMigration.constants;

public enum MimeType {
    APPLICATION_PDF("application/pdf"),
    APPLICATION_XLS("application/vnd.ms-excel"),
    TEXT_CSV("text/csv"),
    TEXT_PLAIN("text/plain"),
    APPLICATION_XLSX("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

    private String name;

    private MimeType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
