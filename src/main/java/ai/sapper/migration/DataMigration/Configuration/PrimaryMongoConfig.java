package ai.sapper.migration.DataMigration.Configuration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;


@Configuration
public class PrimaryMongoConfig extends AbstractMongoClientConfiguration {

    @Value("${spring.data.mongodb.uri.primary}")
    private String primaryUri;


    @Override
    protected String getDatabaseName() {
        return "spreading";
    }

    @Bean
    public MongoTemplate primaryMongoTemplate() {
        return new MongoTemplate(MongoClients.create(primaryUri), getDatabaseName());
    }

    @Override
    public MongoClientSettings mongoClientSettings() {
        return MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(primaryUri))
                .build();
    }
}
