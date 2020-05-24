package com.autoplag.config;


import com.autoplag.util.FileSystemWriter;
import com.autoplag.util.preprocessing.NonEnglishRemovalStrategy;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    @Bean
    public FileSystemWriter fileSystemWriter() {
        return new FileSystemWriter(new NonEnglishRemovalStrategy());
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
