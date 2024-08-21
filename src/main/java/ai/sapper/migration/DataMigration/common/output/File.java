package ai.sapper.migration.DataMigration.common.output;

import lombok.Data;

@Data
public class File {
	
	private String fileName;
	
	private String documentId;
	private String category;
	
	private Integer docTracker;
	
	private  int pageNo;
    private FileOutput output;

	

}
