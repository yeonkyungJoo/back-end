package com.project.devidea.modules.content.study.modelmapper;

import com.project.devidea.infra.config.CustomModelMapper;
import com.project.devidea.modules.content.study.Study;
import com.project.devidea.modules.content.study.apply.StudyApply;
import com.project.devidea.modules.content.study.apply.StudyApplyForm;
import com.project.devidea.modules.content.study.form.StudyDetailForm;
import com.project.devidea.modules.content.study.form.StudyListForm;
import com.project.devidea.modules.content.study.form.StudyMakingForm;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class StudyMapperImpl implements StudyMapper {

    @Bean
    public ModelMapper getStudyListMapper() {
        ModelMapper modelMapper = CustomModelMapper.getModelMapper();
        modelMapper.createTypeMap(Study.class, StudyListForm.class)
                .addMapping(Study::getTags, StudyListForm::setTags)
                .addMapping(Study::getLocation, StudyListForm::setLocations);
        return modelMapper;
    }

    @Bean
    public ModelMapper getStudyApplyMapper() {
        // 매핑 전략 설정
        ModelMapper modelMapper = CustomModelMapper.getModelMapper();
        modelMapper.createTypeMap(StudyApply.class, StudyApplyForm.class)
                .addMapping(StudyApply::getAccount, StudyApplyForm::setUserName)
                .addMapping(StudyApply::getStudy, StudyApplyForm::setStudy);
        return modelMapper;
    }

    @Bean
    public ModelMapper getStudyMakingMapper() {
        ModelMapper modelMapper = CustomModelMapper.getModelMapper();
        modelMapper.createTypeMap(StudyMakingForm.class, Study.class);
        return modelMapper;
    }

    @Bean
    public ModelMapper getStudyDetailMapper() {
        ModelMapper modelMapper = CustomModelMapper.getModelMapper();
        modelMapper.createTypeMap(Study.class, StudyDetailForm.class)
                .addMapping(Study::getTags, StudyDetailForm::setTags)
                .addMapping(Study::getLocation, StudyDetailForm::setLocations);
        return modelMapper;
    }

}
