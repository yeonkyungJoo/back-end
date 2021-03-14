package com.project.devidea.modules.content.study.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
public class StudyMakingForm extends StudyBaseForm{
    private String admin;
    private String question;
    private boolean open; //open할건지
}
