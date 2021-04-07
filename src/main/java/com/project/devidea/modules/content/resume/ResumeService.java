package com.project.devidea.modules.content.resume;

import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.content.mentoring.exception.AlreadyExistException;
import com.project.devidea.modules.content.mentoring.exception.NotFoundException;
import com.project.devidea.modules.content.resume.form.CreateResumeRequest;
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

    public Long createResume(Account account, CreateResumeRequest request) {

        if (resumeRepository.findByAccountId(account.getId()) != null) {
            throw new AlreadyExistException("이력서가 이미 존재합니다.");
        }

        Resume resume = Resume.createResume(account,
                request.getPhoneNumber(),
                request.getGithub(),
                request.getBlog());
        return resumeRepository.save(resume).getId();
    }

    public void updateResume(Account account, UpdateResumeRequest request) {

        Resume resume = resumeRepository.findByAccountId(account.getId());
        if (resume == null) {
            throw new NotFoundException("이력서가 존재하지 않습니다.");
        }
        resume.setPhoneNumber(request.getPhoneNumber());
        resume.setGithub(request.getGithub());
        resume.setBlog(request.getBlog());
    }

    public void deleteResume(Account account) {

        Resume resume = resumeRepository.findByAccountId(account.getId());
        if (resume == null) {
            throw new NotFoundException("이력서가 존재하지 않습니다.");
        }

        resumeRepository.delete(resume);
    }
}
