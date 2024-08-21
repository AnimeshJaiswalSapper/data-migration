package ai.sapper.migration.DataMigration.common.output;

import lombok.Data;

import java.util.List;

@Data
public class FileOutput {
	
	private String _id;
	private List<FormPojo> form;
	private List<TablePojo> table;

}
