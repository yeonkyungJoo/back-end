package com.project.devidea.infra.config.modelmapper;

import com.project.devidea.modules.content.study.Study;
import com.project.devidea.modules.content.study.form.StudyListForm;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NameTokenizers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class CustomModelMapper {

    @Bean(name="StudyMapper")
    public ModelMapper StudyMapper() {
        // 매핑 전략 설정
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setDestinationNameTokenizer(NameTokenizers.UNDERSCORE)
                .setSourceNameTokenizer(NameTokenizers.UNDERSCORE);
        modelMapper.createTypeMap(Study.class, StudyListForm.class)
                .addMapping(Study::getId, StudyListForm::setId)
                .addMapping(Study::getTags, StudyListForm::setTags)
                .addMapping(Study::getLocation, StudyListForm::setLocations);
        return modelMapper;
    }
}
