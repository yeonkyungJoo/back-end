package com.project.devidea.modules.content.resume.education;

import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.content.mentoring.AbstractService;
import com.project.devidea.modules.content.mentoring.exception.NotFoundException;
import com.project.devidea.modules.content.resume.Resume;
import com.project.devidea.modules.content.resume.ResumeRepository;
import com.project.devidea.modules.content.resume.education.Education;
import com.project.devidea.modules.content.resume.education.EducationRepository;
import com.project.devidea.modules.content.resume.form.education.CreateEducationRequest;
import com.project.devidea.modules.content.resume.form.education.UpdateEducationRequest;
import com.project.devidea.modules.tagzone.tag.TagRepository;
import com.project.devidea.modules.tagzone.zone.ZoneRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
public class EducationService extends AbstractService {

    private final ResumeRepository resumeRepository;
    private final EducationRepository educationRepository;

    protected EducationService(TagRepository tagRepository, ZoneRepository zoneRepository, ResumeRepository resumeRepository, EducationRepository educationRepository) {
        super(tagRepository, zoneRepository);
        this.resumeRepository = resumeRepository;
        this.educationRepository = educationRepository;
    }

    public Long createEducation(Account account, CreateEducationRequest request) {

        Resume resume = resumeRepository.findByAccountId(account.getId());
        if (resume == null) {
            throw new NotFoundException("이력서가 존재하지 않습니다.");
        }

        Education education = Education.createEducation(resume,
                request.getSchoolName(),
                request.getMajor(),
                LocalDate.parse(request.getEntranceDate()),
                LocalDate.parse(request.getGraduationDate()),
                request.getScore(),
                request.getDegree());
        return educationRepository.save(education).getId();
    }

    public void updateEducation(Long educationId, UpdateEducationRequest request) {

        Education education = educationRepository.findById(educationId)
                .orElseThrow(() -> new NotFoundException());

        education.setSchoolName(request.getSchoolName());
        education.setMajor(request.getMajor());
        education.setEntranceDate(LocalDate.parse(request.getEntranceDate()));
        education.setGraduationDate(LocalDate.parse(request.getGraduationDate()));
        education.setScore(request.getScore());
        education.setDegree(request.getDegree());
    }

    public void deleteEducation(Long educationId) {

        Education education = educationRepository.findById(educationId)
                .orElseThrow(() -> new NotFoundException());
        Resume resume = education.getResume();

        education.setResume(null);
        resume.getEducations().remove(education);
        educationRepository.delete(education);
    }
}
