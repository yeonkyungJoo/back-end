package com.project.devidea.modules.content.resume.project;

import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.content.mentoring.AbstractService;
import com.project.devidea.modules.content.mentoring.exception.NotFoundException;
import com.project.devidea.modules.content.resume.Resume;
import com.project.devidea.modules.content.resume.ResumeRepository;
import com.project.devidea.modules.content.resume.form.project.CreateProjectRequest;
import com.project.devidea.modules.content.resume.form.project.UpdateProjectRequest;
import com.project.devidea.modules.content.resume.project.Project;
import com.project.devidea.modules.content.resume.project.ProjectRepository;
import com.project.devidea.modules.tagzone.tag.TagRepository;
import com.project.devidea.modules.tagzone.zone.ZoneRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@Transactional
public class ProjectService extends AbstractService {

    private final ProjectRepository projectRepository;
    private final ResumeRepository resumeRepository;

    protected ProjectService(TagRepository tagRepository, ZoneRepository zoneRepository, ProjectRepository projectRepository, ResumeRepository resumeRepository) {
        super(tagRepository, zoneRepository);
        this.projectRepository = projectRepository;
        this.resumeRepository = resumeRepository;
    }

    public Long createProject(Account account, CreateProjectRequest request) {

        Resume resume = resumeRepository.findByAccountId(account.getId());
        if (resume == null) {
            throw new NotFoundException("이력서가 존재하지 않습니다.");
        }

        Project project = Project.createProject(resume,
                request.getProjectName(),
                LocalDate.parse(request.getStartDate(), DateTimeFormatter.ISO_DATE),
                LocalDate.parse(request.getEndDate(), DateTimeFormatter.ISO_DATE),
                request.getShortDescription(),
                getTags(request.getTags()),
                request.getDescription(),
                request.getUrl(),
                request.isOpen());
        return projectRepository.save(project).getId();
    }

    public void updateProject(Long projectId, UpdateProjectRequest request) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException());

        project.setProjectName(request.getProjectName());
        project.setStartDate(LocalDate.parse(request.getStartDate()));
        project.setEndDate(LocalDate.parse(request.getEndDate()));
        project.setShortDescription(request.getShortDescription());
        project.setTags(getTags(request.getTags()));
        project.setDescription(request.getDescription());
        project.setUrl(request.getUrl());
        project.setOpen(request.isOpen());
    }

    public void deleteProject(Long projectId) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException());
        Resume resume = project.getResume();

        project.setResume(null);
        resume.getProjects().remove(project);
        projectRepository.delete(project);
    }
}
