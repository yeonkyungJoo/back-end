package com.project.devidea.modules.content.study.form;

import com.project.devidea.modules.content.study.Study;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class StudyMakingForm extends StudyBaseForm implements Serializable {
    private String question;
    private boolean open; //open할건지
    @Override
    public Study toStudy(){
        Study study=super.toStudy();
        study.setQuestion(question);
        study.setOpen(open);
        return study;
    }
}
