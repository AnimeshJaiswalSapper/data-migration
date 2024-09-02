package ai.sapper.migration.DataMigration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class DataMigrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataMigrationApplication.class, args);
	}

}
