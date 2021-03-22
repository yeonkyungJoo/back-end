package com.project.devidea.modules.content.study.form;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OpenRecruitForm {
    boolean open;
    boolean recruiting;
    public OpenRecruitForm(boolean open, boolean recruiting) {
        this.open=open;
        this.recruiting=recruiting;
    }
}
