package ai.sapper.migration.DataMigration.common.output;

import lombok.Data;

import java.util.List;

@Data
public class TablePojo {
	
	private  String tableId;
	private String tableName;
    private String tableType;
    private List<?> headers;
    private List<RowPojo> rows;


}
