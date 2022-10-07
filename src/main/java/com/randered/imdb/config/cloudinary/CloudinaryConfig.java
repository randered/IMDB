package com.randered.imdb.config.cloudinary;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Value("da0vsli58")
    private String cloudName;

    @Value("572576397362646")
    private String apiKey;

    @Value("p8y74iFVm0OyQ3XNgigeeSs-Y4w")
    private String apiSecret;

    @Bean
    public Cloudinary cloudinary() {
        Cloudinary cloudinary;
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", cloudName);
        config.put("api_key", apiKey);
        config.put("api_secret", apiSecret);
        cloudinary = new Cloudinary(config);
        return cloudinary;
    }
}
