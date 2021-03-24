package com.project.devidea.modules.content.resume;

import com.project.devidea.modules.content.resume.form.UpdateCareerRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CareerService {

    private final ModelMapper modelMapper;
    private final CareerRepository careerRepository;

    public void updateCareer(UpdateCareerRequest request, Career career) {
        modelMapper.map(request, career);
    }

    public void deleteCareer(Resume resume, Career career) {
        career.setResume(null);
        resume.getCareers().remove(career);
        careerRepository.delete(career);
    }

}
