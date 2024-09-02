package ai.sapper.migration.DataMigration.model.postgres;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CaseTimes {

    public Long systemProcessTimeStart;

    public Long systemProcessTimeEnd;


    public Long systemProcessTimeInSeconds;

    public Long touchTimeStart;

    public Long touchTimeEnd;

    public Long touchTimeInSeconds;

    public Long processTimeStart;

    public Long processTimeEnd;

    public Long processTimeInSeconds;
}
