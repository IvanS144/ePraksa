package org.unib.etf.epraksa.config;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

public class ModelMapperConfig {
    @Bean
    public ModelMapper getModelMapper()
    {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setAmbiguityIgnored(true);
        return mapper;
    }
}
