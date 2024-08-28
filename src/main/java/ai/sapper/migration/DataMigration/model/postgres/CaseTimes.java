package ai.sapper.migration.DataMigration.model.postgres;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CaseTimes {
//    @Column(name = "system_process_time_start")
    public Long systemProcessTimeStart;

//    @Column(name = "system_process_time_end")
    public Long systemProcessTimeEnd;

//    @Column(name = "system_process_time_in_seconds")
    public Long systemProcessTimeInSeconds;

//    @Column(name = "touch_time_start")
    public Long touchTimeStart;

//    @Column(name = "touch_time_end")
    public Long touchTimeEnd;

//    @Column(name = "touch_time_in_seconds")
    public Long touchTimeInSeconds;

//    @Column(name = "process_time_start")
    public Long processTimeStart;

//    @Column(name = "process_time_end")
    public Long processTimeEnd;

//    @Column(name = "process_time_in_seconds")
    public Long processTimeInSeconds;
}
