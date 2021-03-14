package com.project.devidea.modules.content.study.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

public interface StudyMapper {

    public ModelMapper StudyList();
    public ModelMapper StudyApply();
    public ModelMapper StudyApplyList();
    public ModelMapper StudyMaking();
    public ModelMapper StudyDetail();
}
