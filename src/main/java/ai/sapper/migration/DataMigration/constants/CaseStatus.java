package ai.sapper.migration.DataMigration.constants;

import java.io.Serializable;

public enum CaseStatus implements Serializable {
    DRAFT, UPLOAD_IN_PROGRESS, UPLOADED, UPLOAD_FAILED, PARSING_IN_PROGRESS, PARSING_SUCCESS, PARSING_FAILED,
    EXTRACTION_IN_PROGRESS, EXTRACTION_SUCCESS, EXTRACTION_FAILED, POSTPROCESSING_IN_PROGRESS, POSTPROCESSING_SUCCESS,
    POSTPROCESSING_FAILED, NORMALIZATION_IN_PROGRESS, NORMALIZATION_SUCCESS, NORMALIZATION_FAILED, FAILED,
    READY_TO_SUBMIT, REJECTED, SUBMITTED, QA_CHECK_FAILED, OCR_FAILED, OCR_IN_PROGRESS, OCR_SUCCESS,MERGE_IN_PROGRESS,MERGE_FAILED,MERGED_SUCCESS
}
