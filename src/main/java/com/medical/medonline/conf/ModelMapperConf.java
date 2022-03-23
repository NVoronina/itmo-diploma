package com.medical.medonline.conf;

import com.medical.medonline.converter.CustomConverter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConf {

    @Bean
    public ModelMapper modelMapper(){
        return  new ModelMapper();
    }
    @Bean
    public CustomConverter creatorConverter(ModelMapper modelMapper) {
        return new CustomConverter(modelMapper);
    }
}
