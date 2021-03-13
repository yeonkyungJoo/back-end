package com.project.devidea.infra.config;

import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.NameTokenizers;
@Getter
public class CustomModelMapper {

    public static ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setDestinationNameTokenizer(NameTokenizers.UNDERSCORE)
                .setSourceNameTokenizer(NameTokenizers.UNDERSCORE);
        return modelMapper;
    }

}
