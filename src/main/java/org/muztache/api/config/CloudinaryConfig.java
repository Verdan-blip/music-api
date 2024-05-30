package org.muztache.api.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Value("${cloudinary.api-key}")
    private String apiKey;

    @Value("${cloudinary.api-secret}")
    private String apiSecret;

    @Value("${cloudinary.cloud-name}")
    private String cloudName;

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public Cloudinary cloudinary() {
        Map<String, String> configMap = new HashMap<>();
        configMap.put("cloud_name", cloudName);
        configMap.put("api_key", apiKey);
        configMap.put("api_secret", apiSecret);
        return new Cloudinary(configMap);
    }
}
