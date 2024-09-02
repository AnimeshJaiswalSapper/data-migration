package ai.sapper.migration.DataMigration.model.mongo;

import ai.sapper.migration.DataMigration.service.mongo.ReadService;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;

import static ai.sapper.migration.DataMigration.constants.Collections.*;

@Document("OriginalPDFData")
@ToString
@Component
@CompoundIndexes({@CompoundIndex(name = "projectId", def = "{'projectId':1}", unique = false),
        @CompoundIndex(name = "documentId", def = "{'documentId':1}", unique = false),
        @CompoundIndex(name = "runId", def = "{'runId':1}", unique = false),
        @CompoundIndex(name = "projectId_documentId", def = "{'projectId':1,'documentId':1}", unique = false),
        @CompoundIndex(name = "documentId_pageNo", def = "{'pageNo':1,'documentId':1}", unique = true)})
@Getter
@Setter
public class OriginalPDFData implements Serializable {

    @Id
    private String _id;

    private String documentId;

    private String projectId;

    private String fileName;

    // Page Wise PDFSegment
    private byte[] pageWisePdfSegmentMapByte;

    @Transient
    private List<Object> pageWisePdfSegmentList;

    // Page Wise PDFSegmentUI
    private byte[] pdfSegmentUIByte;

    @Transient
    private List<Object> pdfSegmentUIList;

    private Long pageNo;

    private Set<String> category = new LinkedHashSet<String>();

    private String runId;// It is the caseId provide by the client

    private String featureVector;

    private double pageWidth;
    private double pageHeight;
    private float xScale;
    private float yScale;
    private float maxX;
    private float maxY;
    private float minX;
    private float minY;

    private int totalPages;

    private Set<String> modified_category = new LinkedHashSet<String>();

    // Modified Page Wise PDFSegmentUI
    private byte[] modified_pdfSegmentUIByte;

    // Modified Page Wise PDFSegmentUI
    @Transient
    private List<Object> modified_pdfSegmentUIList;

    private byte[] bufferedImage;

    private String imagePath;

    @Transient
    private Map<String, Set<String>> projectWiseTemplateMap = new LinkedHashMap<String, Set<String>>();
    private byte[] projectWiseTemplatByte;


    @Autowired
    ReadService readService;

    public List<OriginalPDFData> read(Date lastProcessedDate, String lastProcessedId) {
        return readService.findDocumentsSortedIds(OriginalPDFData.class,
                "OriginalPDFData"
        );
    }

    public List<OriginalPDFData> readByCaseId(String caseId) {
        return readService.findDocuments(OriginalPDFData.class,
                "OriginalPDFData",
                PAGE,
                caseId,
                RUN_ID
        );
    }
}

