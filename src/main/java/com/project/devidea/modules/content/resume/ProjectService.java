package com.project.devidea.modules.content.resume;

import com.project.devidea.modules.content.resume.form.UpdateProjectRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ModelMapper modelMapper;

    public Long save(Project project) {
        return projectRepository.save(project).getId();
    }

    public void updateProject(UpdateProjectRequest request, Project project) {
        modelMapper.map(request, project);
    }

    public void deleteProject(Resume resume, Project project) {
        project.setResume(null);
        resume.getProjects().remove(project);
        projectRepository.delete(project);
    }
}
