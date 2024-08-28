package ai.sapper.migration.DataMigration.convertor;

import ai.sapper.migration.DataMigration.common.postgres.ESpIntakeCaseState;
import ai.sapper.migration.DataMigration.constants.CaseStatus;

public class CaseStatusConverter {

    public static ESpIntakeCaseState convert(CaseStatus caseStatus) {
        switch (caseStatus) {
            case DRAFT:
                return ESpIntakeCaseState.Draft;

            case UPLOAD_IN_PROGRESS:
                return ESpIntakeCaseState.UPLOAD_IN_PROGRESS;
            case UPLOADED:
                return ESpIntakeCaseState.UPLOADED;
            case UPLOAD_FAILED:
                return ESpIntakeCaseState.UPLOAD_FAILED;

            case PARSING_IN_PROGRESS:
                return ESpIntakeCaseState.IDSPending;
            case PARSING_SUCCESS:
                return ESpIntakeCaseState.IDSDone;
            case PARSING_FAILED:
                return ESpIntakeCaseState.IDSFailed;

            case EXTRACTION_IN_PROGRESS:
                return ESpIntakeCaseState.ExtractionPending;
            case EXTRACTION_SUCCESS:
                return ESpIntakeCaseState.ExtractionDone;
            case EXTRACTION_FAILED:
                return ESpIntakeCaseState.ExtractionFailed;

            case POSTPROCESSING_IN_PROGRESS:
                return ESpIntakeCaseState.POSTPROCESSING_IN_PROGRESS;
            case POSTPROCESSING_SUCCESS:
                return ESpIntakeCaseState.POSTPROCESSING_SUCCESS;
            case POSTPROCESSING_FAILED:
                return ESpIntakeCaseState.POSTPROCESSING_FAILED;

            case NORMALIZATION_IN_PROGRESS:
                return ESpIntakeCaseState.NormalizationPending;
            case NORMALIZATION_SUCCESS:
                return ESpIntakeCaseState.NormalizationDone;
            case NORMALIZATION_FAILED:
                return ESpIntakeCaseState.NormalizationFailed;

            case FAILED:
                return ESpIntakeCaseState.Error;

            case READY_TO_SUBMIT:
                return ESpIntakeCaseState.Approved;
            case REJECTED:
                return ESpIntakeCaseState.Rejected;
            case SUBMITTED:
                return ESpIntakeCaseState.Submitted;

            case QA_CHECK_FAILED:
                return ESpIntakeCaseState.QAChecksFailed;

            case OCR_FAILED:
                return ESpIntakeCaseState.OCRFailed;
            case OCR_IN_PROGRESS:
                return ESpIntakeCaseState.OCRPending;
            case OCR_SUCCESS:
                return ESpIntakeCaseState.OCRDone;

            case MERGE_IN_PROGRESS:
                return ESpIntakeCaseState.MergePending;
            case MERGE_FAILED:
                return ESpIntakeCaseState.MergeFailed;
            case MERGED_SUCCESS:
                return ESpIntakeCaseState.MergeDone;

            default:
                return ESpIntakeCaseState.Unknown;
        }
    }
}

