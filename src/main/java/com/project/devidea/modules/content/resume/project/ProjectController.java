package com.project.devidea.modules.content.resume.project;

import com.project.devidea.infra.config.security.CurrentUser;
import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.content.mentoring.exception.NotFoundException;
import com.project.devidea.modules.content.resume.Resume;
import com.project.devidea.modules.content.resume.ResumeRepository;
import com.project.devidea.modules.content.resume.form.project.CreateProjectRequest;
import com.project.devidea.modules.content.resume.form.project.UpdateProjectRequest;
import com.project.devidea.modules.content.resume.validator.ProjectRequestValidator;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/resume/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ResumeRepository resumeRepository;
    private final ProjectRepository projectRepository;
    private final ProjectService projectService;
    private final ProjectRequestValidator projectRequestValidator;

    @InitBinder("request")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(projectRequestValidator);
    }

    /**
     * Project 전체 조회
     */
    @GetMapping("/")
    public ResponseEntity getProjects(@CurrentUser Account account) {

        if (account == null) {
            throw new AccessDeniedException("Access is Denied");
        }

        Resume resume = resumeRepository.findByAccountId(account.getId());
        if (resume == null) {
            throw new NotFoundException("이력서가 존재하지 않습니다.");
        }
        List<Object> collect = resume.getProjects().stream()
                .map(project -> new ProjectDto(project))
                .collect(Collectors.toList());
        return new ResponseEntity(collect, HttpStatus.OK);
    }

    /**
     * Project 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity getProject(@CurrentUser Account account,
                                     @PathVariable("id") Long projectId) {
        if (account == null) {
            throw new AccessDeniedException("Access is Denied");
        }

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException());
        return new ResponseEntity(new ProjectDto(project), HttpStatus.OK);
    }

    /**
     * Project 등록
     */
    @PostMapping("/")
    public ResponseEntity newProject(@CurrentUser Account account,
                                     @RequestBody @Valid CreateProjectRequest request) {
        if (account == null) {
            throw new AccessDeniedException("Access is Denied");
        }

        Long projectId = projectService.createProject(account, request);
        return new ResponseEntity(projectId, HttpStatus.CREATED);
    }

    /**
     * Project 수정
     */
    @PostMapping("/{id}/edit")
    public ResponseEntity editProject(@CurrentUser Account account, @PathVariable("id") Long projectId,
                                      @RequestBody @Valid UpdateProjectRequest request) {
        if (account == null) {
            throw new AccessDeniedException("Access is Denied");
        }
        projectService.updateProject(projectId, request);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Project 삭제
     */
    @PostMapping("/{id}/delete")
    public ResponseEntity deleteProject(@CurrentUser Account account,
                                        @PathVariable("id") Long projectId) {
        if (account == null) {
            throw new AccessDeniedException("Access is Denied");
        }
        projectService.deleteProject(projectId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Data
    public class ProjectDto {

        private Long id;
        private String projectName;
        private String startDate;
        private String endDate;
        private String shortDescription;
        private Set<String> tags;
        private String description;
        private String url;
        private boolean open;

        public ProjectDto(Project project) {
            this.id = project.getId();
            this.projectName = project.getProjectName();
            this.startDate = project.getStartDate().toString();
            this.endDate = project.getEndDate().toString();
            this.shortDescription = project.getShortDescription();
            this.tags = project.getTags().stream()
                    .map(tag -> tag.toString()).collect(Collectors.toSet());
            this.description = project.getDescription();
            this.url = project.getUrl();
            this.open = project.isOpen();
        }
    }
}
