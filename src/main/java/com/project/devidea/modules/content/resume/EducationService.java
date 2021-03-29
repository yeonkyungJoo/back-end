package com.project.devidea.modules.content.resume;

import com.project.devidea.modules.content.resume.form.UpdateEducationRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class EducationService {

    private final EducationRepository educationRepository;
    private final ModelMapper modelMapper;

    public Long save(Education education) {
        return educationRepository.save(education).getId();
    }

    public void updateEducation(UpdateEducationRequest request, Education education) {
        modelMapper.map(request, education);
    }

    public void deleteEducation(Resume resume, Education education) {
        education.setResume(null);
        resume.getEducations().remove(education);
        educationRepository.delete(education);
    }
}
