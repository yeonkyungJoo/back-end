package com.project.devidea.modules.content.resume.career;

import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.content.mentoring.AbstractService;
import com.project.devidea.modules.content.mentoring.exception.NotFoundException;
import com.project.devidea.modules.content.resume.Resume;
import com.project.devidea.modules.content.resume.ResumeRepository;
import com.project.devidea.modules.content.resume.form.career.CreateCareerRequest;
import com.project.devidea.modules.content.resume.form.career.UpdateCareerRequest;
import com.project.devidea.modules.tagzone.tag.TagRepository;
import com.project.devidea.modules.tagzone.zone.ZoneRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@Transactional
public class CareerService extends AbstractService {

    private final CareerRepository careerRepository;
    private final ResumeRepository resumeRepository;

    protected CareerService(TagRepository tagRepository, ZoneRepository zoneRepository, CareerRepository careerRepository, ResumeRepository resumeRepository) {
        super(tagRepository, zoneRepository);
        this.careerRepository = careerRepository;
        this.resumeRepository = resumeRepository;
    }

    public Long createCareer(Account account, CreateCareerRequest request) {

        Resume resume = resumeRepository.findByAccountId(account.getId());
        if (resume == null) {
            throw new NotFoundException("이력서가 존재하지 않습니다.");
        }

        Career career = Career.createCareer(resume,
                request.getCompanyName(),
                request.getDuty(),
                LocalDate.parse(request.getStartDate(), DateTimeFormatter.ISO_DATE),
                LocalDate.parse(request.getEndDate(), DateTimeFormatter.ISO_DATE),
                request.isPresent(),
                getTags(request.getTags()),
                request.getDetail(),
                request.getUrl());
        return careerRepository.save(career).getId();
    }

    public void updateCareer(Long careerId, UpdateCareerRequest request) {

        Career career = careerRepository.findById(careerId)
                .orElseThrow(() -> new NotFoundException());

        career.setCompanyName(request.getCompanyName());
        career.setDuty(request.getDuty());
        career.setStartDate(LocalDate.parse(request.getStartDate(), DateTimeFormatter.ISO_DATE));
        career.setEndDate(LocalDate.parse(request.getEndDate(), DateTimeFormatter.ISO_DATE));
        career.setPresent(request.isPresent());
        career.setTags(getTags(request.getTags()));
        career.setDetail(request.getDetail());
        career.setUrl(request.getUrl());
    }


    public void deleteCareer(Long careerId) {

        Career career = careerRepository.findById(careerId)
                .orElseThrow(() -> new NotFoundException());
        Resume resume = career.getResume();

        career.setResume(null);
        resume.getCareers().remove(career);
        careerRepository.delete(career);
    }

}
