package ai.sapper.migration.DataMigration.constants;

public enum SpDocumentType {
    OCROutput,

    IDSOutput,
    ExtractionOutput,
    UDPJson,
    Output,

    NormalizationOutput,
    QaCheckOutput,

    OutputFlatFile,
    EncryptedOutputFile,

    CASE,
    CaseZIP,
    UDPLatest,

    ITR,
    FINANCIAL_STATEMENT;

    public static SpDocumentType fromCaseType(CaseType caseType) {
        switch (caseType) {
            case ITR:
                return ITR;
            case FINANCIAL_STATEMENT:
                return FINANCIAL_STATEMENT;
            case EXTRACTION:
                return UDPJson;
            case NORMALIZATION:
                return NormalizationOutput;
            default:
                throw new IllegalArgumentException("Unknown CaseType: " + caseType);
        }
    }
}
