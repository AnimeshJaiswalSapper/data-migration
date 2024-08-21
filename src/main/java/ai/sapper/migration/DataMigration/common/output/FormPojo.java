package ai.sapper.migration.DataMigration.common.output;

import lombok.Data;

import java.util.List;

@Data
public class FormPojo {

	private  Long formGroupLabelId;
	private String label;
    private List<FormEntity> formGroupEntityList;

}
