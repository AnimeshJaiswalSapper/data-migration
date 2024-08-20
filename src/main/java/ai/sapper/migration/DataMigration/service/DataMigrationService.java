package ai.sapper.migration.DataMigration.service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class DataMigrationService {

    private static final List<String> services = List.of("CaseService","CaseDocumentDOService");

    @Value("${class.path}")
    private String serviceClassPath;

    @Autowired
    private ApplicationContext applicationContext;


    @PostConstruct
    public void  start(){
        for(String service : services){
            String classPath = serviceClassPath + "." + service;
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
