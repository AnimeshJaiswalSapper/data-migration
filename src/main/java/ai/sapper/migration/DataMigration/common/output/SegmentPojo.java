package ai.sapper.migration.DataMigration.common.output;

import lombok.Data;

@Data
public class SegmentPojo {
	
	private String _id;
	private String segmentId;
    private float x1;
    private float y1;
    private float x2;
    private float y2;
    private float pageWidth;
    private float pageHeight;
    private String value;
    private Integer pageNo;
    private Float confidence;
    private String fileName;
	

}
