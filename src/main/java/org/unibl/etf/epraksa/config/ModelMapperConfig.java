package org.unibl.etf.epraksa.config;


import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.unibl.etf.epraksa.model.entities.WorkDairyEntry;
import org.unibl.etf.epraksa.model.requests.WorkDiaryEntryRequest;

import java.time.LocalTime;

@Component
public class ModelMapperConfig {

    Converter<String, LocalTime> strToTimeConverter= ctx-> LocalTime.parse(ctx.getSource());
    @Bean
    public ModelMapper getModelMapper()
    {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setAmbiguityIgnored(true);
        mapper.createTypeMap(WorkDiaryEntryRequest.class, WorkDairyEntry.class)
                .addMappings(map -> map.using(strToTimeConverter).map(WorkDiaryEntryRequest::getFromTime, WorkDairyEntry::setFrom))
                .addMappings(map -> map.using(strToTimeConverter)
                        .map(WorkDiaryEntryRequest::getFromTime, WorkDairyEntry::setFrom));
        return mapper;
    }
}
