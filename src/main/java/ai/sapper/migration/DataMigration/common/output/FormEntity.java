package ai.sapper.migration.DataMigration.common.output;

import lombok.Data;

import java.util.List;

@Data
public class FormEntity {
	private Long entityId;
	private String entityLabel;
	
	private List<SegmentPojo> segmentList;

}
