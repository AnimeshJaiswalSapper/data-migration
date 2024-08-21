package ai.sapper.migration.DataMigration.common.output;

import lombok.Data;

import java.util.List;

@Data
public class TableEntity {

	private String _id;
	private String row;
	private String column;
	private String rowSpan;
	private String columnSpan;
	private String columnName;
	private List<SegmentPojo> segmentList;
	
}
