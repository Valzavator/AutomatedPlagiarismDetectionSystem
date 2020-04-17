package com.gmail.maxsvynarchuk.config;


import com.gmail.maxsvynarchuk.util.FileSystemWriter;
import com.gmail.maxsvynarchuk.util.preprocessing.CyrillicRemovalStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    @Bean
    public FileSystemWriter fileSystemWriter() {
        return new FileSystemWriter(new CyrillicRemovalStrategy());
    }
}