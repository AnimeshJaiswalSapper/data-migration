package ai.sapper.migration.DataMigration.s3migration.utils;

import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.Configuration;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TokenUtil {

    @Value("${keycloak.auth-server-url}")
    private String url;
    @Value("${keycloak.realm}")
    private String realm;
    @Value("${keycloak.clientId}")
    private String clientId;
    @Value("${keycloak.auth.userId}")
    private String userId;
    @Value("${keycloak.auth.password}")
    private String password;

    private static String BEARER = "Bearer ";

    public String getToken() {
        Map<String, Object> clientDetails = new HashMap<>();
        clientDetails.put("secret", "test");
        System.out.println(url);
        Configuration configuration = new Configuration(url, realm, clientId, clientDetails, null);
        AuthzClient authzClient = AuthzClient.create(configuration);
        AccessTokenResponse accessTokenResponse = authzClient.obtainAccessToken(userId, password);
        String token = accessTokenResponse.getToken();
        return BEARER + token;
    }

}
