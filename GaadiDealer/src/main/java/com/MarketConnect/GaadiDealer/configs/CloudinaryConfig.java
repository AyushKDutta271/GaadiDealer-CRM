package com.MarketConnect.GaadiDealer.configs;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary()
    {
        Map<String,String> cloudinaryConfig=new HashMap<>();

        cloudinaryConfig.put("cloud_name",System.getenv("CLOUDINARY_CLOUD_NAME"));
        cloudinaryConfig.put("api_key",System.getenv("CLOUDINARY_API_KEY"));
        cloudinaryConfig.put("api_secret",System.getenv("CLOUDINARY_API_SECRET"));
        cloudinaryConfig.put("secure","true");
        return new Cloudinary(cloudinaryConfig);
    }
}


