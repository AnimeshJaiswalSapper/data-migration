package ai.sapper.migration.DataMigration;

import ai.sapper.migration.DataMigration.Repository.CaseRepository;
import ai.sapper.migration.DataMigration.common.Migration;
import ai.sapper.migration.DataMigration.model.Case;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class DataMigration {

    private static final Map<String, String> classPathMap = new HashMap<>();

    static {
        classPathMap.put("Case", "ai.sapper.migration.DataMigration.model.Case");
    }

    @Autowired
    private ApplicationContext applicationContext;


    @PostConstruct
    public void  start(){
        for (Map.Entry<String, String> entry : classPathMap.entrySet()) {
            String className = entry.getKey();
            String classPath = entry.getValue();

            try {
                    Object caseObj = applicationContext.getBean(Class.forName(classPath));
                    log.info("Inside PostConstruct method");
                    Method migrate = caseObj.getClass().getMethod("migrate");
                    migrate.invoke(caseObj);
                    log.info("Outside PostConstruct method");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
