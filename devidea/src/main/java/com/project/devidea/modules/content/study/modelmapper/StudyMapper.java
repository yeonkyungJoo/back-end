package com.project.devidea.modules.content.study.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

public interface StudyMapper {

    public ModelMapper getStudyListMapper();
    public ModelMapper getStudyApplyMapper();
    public ModelMapper getStudyMakingMapper();
    public ModelMapper getStudyDetailMapper();
}
