package ai.sapper.migration.DataMigration.common.postgres;

import lombok.Getter;

@Getter
public enum ESpIntakeCaseState {

    Unknown,
    Draft,
    New,
    Ready,

    OCRPending,
    OCRFailed,
    OCRDone,
    PO_PENDING,
    ERP_FAILED,
    PO_FAILED,
    PO_LOADED,
    IDSPending,
    IDSFailed,
    IDSDone,

    ExtractionPending,
    Reinstated,

    ExtractionFailed,

    ExtractionDone,

    UDPPending,
    UDPFailed,
    UDPDone,
    UDPUpdateInProgress,
    UDPUpdated,
    NormalizationPending,

    NormalizationFailed,
    NormalizationDone,

    QueuedForReview,
    QAChecksPending,
    QAChecksDone,
    QAChecksFailed,

    Approved,

    OutputPending,
    OutputFailed,
    OutputDone,

    Submitted,
    Rejected,
    Error,
    InvalidFileName,
    DecryptionFailed,

    Deleted,
    Accepted,
    SPLITTED,

    MergePending,
    MergeFailed,
    MergeDone,

    UPLOAD_IN_PROGRESS,
    UPLOADED,
    UPLOAD_FAILED,

    POSTPROCESSING_IN_PROGRESS,
    POSTPROCESSING_SUCCESS,
    POSTPROCESSING_FAILED;


    public static ESpIntakeCaseState parse(String value){
        for (ESpIntakeCaseState state : ESpIntakeCaseState.values()) {
            if (state.name().compareToIgnoreCase(value) == 0) {
                return state;
            }
        }
        return null;
    }
}
