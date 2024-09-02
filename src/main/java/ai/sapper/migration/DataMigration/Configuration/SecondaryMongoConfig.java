package ai.sapper.migration.DataMigration.Configuration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;


@Configuration
public class SecondaryMongoConfig extends AbstractMongoClientConfiguration {

    @Value("${spring.data.mongodb.uri.secondary}")
    private String secondaryUri;


    @Override
    protected String getDatabaseName() {
        return "ids";
    }

    @Bean
    public MongoTemplate secondaryMongoTemplate() {
        return new MongoTemplate(MongoClients.create(secondaryUri), getDatabaseName());
    }

    @Override
    public MongoClientSettings mongoClientSettings() {
        return MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(secondaryUri))
                .build();
    }
}
