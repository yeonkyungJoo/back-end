package com.project.devidea.modules.content.study.form;

import lombok.Data;

@Data
public class OpenRecruitForm {
    boolean open;
    boolean recruiting;
    public OpenRecruitForm(boolean open, boolean recruiting) {
        this.open=open;
        this.recruiting=recruiting;
    }
}
