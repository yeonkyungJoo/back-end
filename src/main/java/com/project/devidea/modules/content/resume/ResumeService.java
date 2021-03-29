package com.project.devidea.modules.content.resume;

import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.content.resume.form.UpdateResumeRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final ModelMapper modelMapper;

    public Long createResume(Resume resume, Account account) {
        resume.setAccount(account);
        resumeRepository.save(resume);
        return resume.getId();
    }

    public void updateResume(UpdateResumeRequest request, Resume resume) {
        modelMapper.map(request, resume);
    }

    public void deleteResume(Resume resume) {
        resume.setAccount(null);
        resumeRepository.delete(resume);
    }
}
