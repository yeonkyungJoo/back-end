package com.project.devidea.modules.content.resume.education;

import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.content.mentoring.AbstractService;
import com.project.devidea.modules.content.mentoring.exception.InvalidInputException;
import com.project.devidea.modules.content.mentoring.exception.NotFoundException;
import com.project.devidea.modules.content.resume.Resume;
import com.project.devidea.modules.content.resume.ResumeRepository;
import com.project.devidea.modules.content.resume.form.education.CreateEducationRequest;
import com.project.devidea.modules.content.resume.form.education.EducationRequest;
import com.project.devidea.modules.content.resume.form.education.UpdateEducationRequest;
import com.project.devidea.modules.tagzone.tag.TagRepository;
import com.project.devidea.modules.tagzone.zone.ZoneRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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

    private void validateEducationRequest(EducationRequest request) {

        LocalDate entranceDate = LocalDate.parse(request.getEntranceDate());
        LocalDate graduationDate = null;
        if (StringUtils.hasLength(request.getGraduationDate())) {
            graduationDate = LocalDate.parse(request.getGraduationDate());
        }

        if (entranceDate.isAfter(LocalDate.now())) {
            throw new InvalidInputException();
        }

        if (graduationDate != null && !graduationDate.isAfter(entranceDate)) {
            throw new InvalidInputException();
        }
    }

    public Long createEducation(Account account, CreateEducationRequest request) {

        validateEducationRequest(request);
        Resume resume = resumeRepository.findByAccountId(account.getId());
        if (resume == null) {
            throw new NotFoundException("이력서가 존재하지 않습니다.");
        }

        Education education = Education.createEducation(resume,
                request.getSchoolName(),
                request.getMajor(),
                LocalDate.parse(request.getEntranceDate()),
                StringUtils.hasLength(request.getGraduationDate()) ? LocalDate.parse(request.getGraduationDate()) : null,
                request.getScore(),
                request.getDegree());
        return educationRepository.save(education).getId();
    }

    public void updateEducation(Long educationId, UpdateEducationRequest request) {

        validateEducationRequest(request);
        Education education = educationRepository.findById(educationId)
                .orElseThrow(() -> new NotFoundException());

        education.setSchoolName(request.getSchoolName());
        education.setMajor(request.getMajor());
        education.setEntranceDate(LocalDate.parse(request.getEntranceDate()));
        education.setGraduationDate(StringUtils.hasLength(request.getGraduationDate()) ? LocalDate.parse(request.getGraduationDate()) : null);
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
