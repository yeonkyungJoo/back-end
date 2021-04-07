package com.project.devidea.modules.content.resume.award;

import com.project.devidea.infra.error.exception.ErrorCode;
import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.content.mentoring.AbstractService;
import com.project.devidea.modules.content.mentoring.exception.InvalidInputException;
import com.project.devidea.modules.content.mentoring.exception.NotFoundException;
import com.project.devidea.modules.content.resume.Resume;
import com.project.devidea.modules.content.resume.ResumeRepository;
import com.project.devidea.modules.content.resume.form.award.AwardRequest;
import com.project.devidea.modules.content.resume.form.award.CreateAwardRequest;
import com.project.devidea.modules.content.resume.form.award.UpdateAwardRequest;
import com.project.devidea.modules.tagzone.tag.TagRepository;
import com.project.devidea.modules.tagzone.zone.ZoneRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
public class AwardService extends AbstractService {

    private final ResumeRepository resumeRepository;
    private final AwardRepository awardRepository;

    protected AwardService(TagRepository tagRepository, ZoneRepository zoneRepository, ResumeRepository resumeRepository, AwardRepository awardRepository) {
        super(tagRepository, zoneRepository);
        this.resumeRepository = resumeRepository;
        this.awardRepository = awardRepository;
    }

    private void validateAwardRequest(AwardRequest request) {

        LocalDate date = LocalDate.parse(request.getDate());

        if (date.isAfter(LocalDate.now())) {
            throw new InvalidInputException();
        }
    }

    public Long createAward(Account account, CreateAwardRequest request) {

        validateAwardRequest(request);
        Resume resume = resumeRepository.findByAccountId(account.getId());
        if (resume == null) {
            throw new NotFoundException("이력서가 존재하지 않습니다.");
        }

        Award award = Award.createAward(resume,
                request.getName(),
                LocalDate.parse(request.getDate()),
                request.getLink(),
                request.getDescription());
        return awardRepository.save(award).getId();
    }

    public void updateAward(Long awardId, UpdateAwardRequest request) {

        validateAwardRequest(request);
        Award award = awardRepository.findById(awardId)
                .orElseThrow(() -> new NotFoundException());

        award.setName(request.getName());
        award.setDate(LocalDate.parse(request.getDate()));
        award.setLink(request.getLink());
        award.setDescription(request.getDescription());
    }

    public void deleteAward(Long awardId) {

        Award award = awardRepository.findById(awardId)
                .orElseThrow(() -> new NotFoundException());
        Resume resume = award.getResume();

        award.setResume(null);
        resume.getAwards().remove(award);
        awardRepository.delete(award);
    }

}
