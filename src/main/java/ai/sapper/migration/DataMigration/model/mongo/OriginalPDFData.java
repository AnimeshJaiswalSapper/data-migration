package ai.sapper.migration.DataMigration.model.mongo;

import ai.sapper.migration.DataMigration.common.PageBaseStructure;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.*;

@Document("OriginalPDFData")
@CompoundIndexes({ @CompoundIndex(name = "projectId", def = "{'projectId':1}", unique = false),
        @CompoundIndex(name = "documentId", def = "{'documentId':1}", unique = false),
        @CompoundIndex(name = "runId", def = "{'runId':1}", unique = false),
        @CompoundIndex(name = "projectId_documentId", def = "{'projectId':1,'documentId':1}", unique = false),
        @CompoundIndex(name = "documentId_pageNo", def = "{'pageNo':1,'documentId':1}", unique = true)})
public class OriginalPDFData implements PageBaseStructure, Serializable {

    private static final long serialVersionUID = 1L;

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

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public byte[] getBufferedImage() {
        return bufferedImage;
    }

    public void setBufferedImage(byte[] bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    public double getPageWidth() {
        return pageWidth;
    }

    public void setPageWidth(double pageWidth) {
        this.pageWidth = pageWidth;
    }

    public double getPageHeight() {
        return pageHeight;
    }

    public void setPageHeight(double pageHeight) {
        this.pageHeight = pageHeight;
    }

    public float getxScale() {
        return xScale;
    }

    public void setxScale(float xScale) {
        this.xScale = xScale;
    }

    public float getyScale() {
        return yScale;
    }

    public void setyScale(float yScale) {
        this.yScale = yScale;
    }

    public float getMaxX() {
        return maxX;
    }

    public void setMaxX(float maxX) {
        this.maxX = maxX;
    }

    public float getMaxY() {
        return maxY;
    }

    public void setMaxY(float maxY) {
        this.maxY = maxY;
    }

    public float getMinX() {
        return minX;
    }

    public void setMinX(float minX) {
        this.minX = minX;
    }

    public float getMinY() {
        return minY;
    }

    public void setMinY(float minY) {
        this.minY = minY;
    }

    public Set<String> getModified_category() {
        return modified_category;
    }

    public void setModified_category(Set<String> modified_category) {
        this.modified_category = modified_category;
    }

    public byte[] getModified_pdfSegmentUIByte() {
        return modified_pdfSegmentUIByte;
    }

    public void setModified_pdfSegmentUIByte(byte[] modified_pdfSegmentUIByte) {
        this.modified_pdfSegmentUIByte = modified_pdfSegmentUIByte;
    }

    public String get_id() {
        return _id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public byte[] getPageWisePdfSegmentMapByte() {
        return pageWisePdfSegmentMapByte;
    }

    public void setPageWisePdfSegmentMapByte(byte[] pageWisePdfSegmentMapByte) {
        this.pageWisePdfSegmentMapByte = pageWisePdfSegmentMapByte;
    }

    public Long getPageNo() {
        return pageNo;
    }

    public void setPageNo(Long pageNo) {
        this.pageNo = pageNo;
    }

    public Set<String> getCategory() {
        return category;
    }

    public void setCategory(Set<String> category) {
        this.category = category;
    }

    public String getRunId() {
        return runId;
    }

    public void setRunId(String runId) {
        this.runId = runId;
    }

    public List<Object> getPageWisePdfSegmentList() {
        return pageWisePdfSegmentList;
    }

    public void setPageWisePdfSegmentList(List<Object> pageWisePdfSegmentList) {
        this.pageWisePdfSegmentList = pageWisePdfSegmentList;
    }

    public byte[] getPdfSegmentUIByte() {
        return pdfSegmentUIByte;
    }

    public void setPdfSegmentUIByte(byte[] pdfSegmentUIByte) {
        this.pdfSegmentUIByte = pdfSegmentUIByte;
    }

    public List<Object> getPdfSegmentUIList() {
        return pdfSegmentUIList;
    }

    public void setPdfSegmentUIList(List<Object> pdfSegmentUIList) {
        this.pdfSegmentUIList = pdfSegmentUIList;
    }

    public String getFeatureVector() {
        return featureVector;
    }

    public void setFeatureVector(String featureVector) {
        this.featureVector = featureVector;
    }

    public List<Object> getModified_pdfSegmentUIList() {
        return modified_pdfSegmentUIList;
    }

    public void setModified_pdfSegmentUIList(List<Object> modified_pdfSegmentUIList) {
        this.modified_pdfSegmentUIList = modified_pdfSegmentUIList;
    }

    @Override
    public String toString() {
        return "OriginalPDFData [_id = " + _id + ", documentId = " + documentId + ", projectId = " + projectId
                + ",fileName = " + fileName + ",pageNo=" + pageNo + ", category=" + category + ", runId=" + runId + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((_id == null) ? 0 : _id.hashCode());
        result = prime * result + ((documentId == null) ? 0 : documentId.hashCode());
        result = prime * result + ((fileName == null) ? 0 : fileName.hashCode());
        result = prime * result + Arrays.hashCode(pageWisePdfSegmentMapByte);
        result = prime * result + ((projectId == null) ? 0 : projectId.hashCode());
        result = prime * result + ((pageNo == null) ? 0 : pageNo.hashCode());
        ;
        result = prime * result + ((runId == null) ? 0 : runId.hashCode());
        result = prime * result + ((category == null) ? 0 : category.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OriginalPDFData other = (OriginalPDFData) obj;
        if (_id == null) {
            if (other._id != null)
                return false;
        } else if (!_id.equals(other._id))
            return false;
        if (documentId == null) {
            if (other.documentId != null)
                return false;
        } else if (!documentId.equals(other.documentId))
            return false;
        if (fileName == null) {
            if (other.fileName != null)
                return false;
        } else if (!fileName.equals(other.fileName))
            return false;
        if (!Arrays.equals(pageWisePdfSegmentMapByte, other.pageWisePdfSegmentMapByte))
            return false;
        if (projectId == null) {
            if (other.projectId != null)
                return false;
        } else if (!projectId.equals(other.projectId))
            return false;
        return true;
    }

    public Map<String, Set<String>> getProjectWiseTemplateMap() {
        return projectWiseTemplateMap;
    }

    public void setProjectWiseTemplateMap(Map<String, Set<String>> projectWiseTemplateMap) {
        this.projectWiseTemplateMap = projectWiseTemplateMap;
    }

    public byte[] getProjectWiseTemplatByte() {
        return projectWiseTemplatByte;
    }

    public void setProjectWiseTemplatByte(byte[] projectWiseTemplatByte) {
        this.projectWiseTemplatByte = projectWiseTemplatByte;
    }

    public String getId() {
        return String.valueOf(this.get_id());
    }

    public List<Object> getPdfSegmentList() {
        return this.pdfSegmentUIList;
    }

    public String getTemplateName() {
        return this.category.iterator().next();
    }

    public void setTemplateName(String templateName) {
        this.category.add(templateName);
    }

    public int getPgNo() {
        return this.pageNo.intValue();
    }
}

