package ai.sapper.migration.DataMigration.common;

import java.util.List;

public interface PageBaseStructure {
    String getId();

    List<Object> getPdfSegmentList();

    List<Object> getModified_pdfSegmentUIList();

    void setModified_pdfSegmentUIList(List<Object> var1);

    String getTemplateName();

    void setTemplateName(String var1);

    int getPgNo();

    String getFileName();
}