package com.project.devidea.modules.content.resume;

import com.project.devidea.infra.config.security.LoginUser;
import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.content.resume.form.CreateProjectRequest;
import com.project.devidea.modules.content.resume.form.UpdateProjectRequest;
import com.project.devidea.modules.tagzone.tag.Tag;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/resume/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ResumeRepository resumeRepository;
    private final ProjectRepository projectRepository;

    /**
     * Project 전체 조회
     */
    @GetMapping("/")
    public ResponseEntity getProjects(@AuthenticationPrincipal LoginUser loginUser) {

        if (loginUser == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        Account account = loginUser.getAccount();
        Resume resume = resumeRepository.findByAccountId(account.getId());
        if(resume == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        List<Project> projects = resume.getProjects();
        List<Object> collect = projects.stream()
                .map(project -> new ProjectDto(project))
                .collect(Collectors.toList());
        return new ResponseEntity(collect, HttpStatus.OK);
    }

    /**
     * Project 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity getProject(@AuthenticationPrincipal LoginUser loginUser,
                                     @PathVariable("id") Long projectId) {

        if (loginUser == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        Optional<Project> project = projectRepository.findById(projectId);

    }

    /**
     * Project 등록
     */
    @PostMapping("/")
    public ResponseEntity newProject(@AuthenticationPrincipal LoginUser loginUser,
                                     @RequestBody @Valid CreateProjectRequest request, Errors errors) {

    }

    /**
     * Project 수정
     */
    @PostMapping("/{id}/edit")
    public ResponseEntity editProject(@AuthenticationPrincipal LoginUser loginUser,
                                      @PathVariable("id") Long projectId,
                                      @RequestBody @Valid UpdateProjectRequest request, Errors errors) {

    }

    /**
     * Project 삭제
     */
    @PostMapping("/{id}/delete")
    public ResponseEntity deleteProject(@AuthenticationPrincipal LoginUser loginUser,
                                        @PathVariable("id") Long projectId) {

    }

    @Data
    public class ProjectDto {

        private String projectName;
        private LocalDate startDate;
        private LocalDate endDate;
        private String shortDescription;
        private Set<Tag> tags;
        private String description;
        private String url;
        private boolean open;

        public ProjectDto(Project project) {
            this.projectName = project.getProjectName();
            this.startDate = project.getStartDate();
            this.endDate = project.getEndDate();
            this.shortDescription = project.getShortDescription();
            this.tags = project.getTags();
            this.description = project.getDescription();
            this.url = project.getUrl();
            this.open = project.isOpen();
        }
    }
}
