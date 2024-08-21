package ai.sapper.migration.DataMigration.common.output;

import lombok.Data;

import java.util.List;

@Data
public class RowPojo {

	private String subsection;
	private List<TableEntity> columns;
	private String standardLabel;
}
