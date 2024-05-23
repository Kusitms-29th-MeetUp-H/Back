package com.kusitms29.backendH.infra.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource(value = "classpath:/prompt.properties", encoding = "UTF-8")
public class TranslateConfig {
    @Autowired
    private Environment env;

    public String getPromptValue(String key) {
        return env.getProperty(key);
    }
}

