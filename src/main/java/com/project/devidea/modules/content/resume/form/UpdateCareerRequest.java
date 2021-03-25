package com.project.devidea.modules.content.resume.form;

import com.project.devidea.api.Request;
import com.project.devidea.modules.tagzone.tag.Tag;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class UpdateCareerRequest extends Request {

    @NotEmpty
    private String companyName;
    @NotEmpty
    private String duty;
    @NotNull
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean present;
    private Set<Tag> tags;
    private String detail;
    private String url;

    @Builder
    public UpdateCareerRequest(@NotEmpty String companyName, @NotEmpty String duty, @NotNull LocalDate startDate, LocalDate endDate, boolean present, Set<Tag> tags, String detail, String url) {
        this.companyName = companyName;
        this.duty = duty;
        this.startDate = startDate;
        this.endDate = endDate;
        this.present = present;
        this.tags = tags;
        this.detail = detail;
        this.url = url;
    }
}
