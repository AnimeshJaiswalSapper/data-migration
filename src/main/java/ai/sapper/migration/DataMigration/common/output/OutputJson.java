package ai.sapper.migration.DataMigration.common.output;

import lombok.Data;

import java.util.List;

@Data
public class OutputJson {

	private String _id;
	private String runName;
	private String project;
	private List<File> files;
	
}
