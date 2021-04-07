package com.project.devidea.modules.content.mentoring;

import com.project.devidea.infra.error.exception.ErrorCode;
import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.content.mentoring.exception.AlreadyExistException;
import com.project.devidea.modules.content.mentoring.exception.InvalidInputException;
import com.project.devidea.modules.content.mentoring.exception.NotFoundException;
import com.project.devidea.modules.content.mentoring.form.CreateMentorRequest;
import com.project.devidea.modules.content.mentoring.form.MentorRequest;
import com.project.devidea.modules.content.mentoring.form.UpdateMentorRequest;
import com.project.devidea.modules.content.resume.Resume;
import com.project.devidea.modules.content.resume.ResumeRepository;
import com.project.devidea.modules.tagzone.tag.Tag;
import com.project.devidea.modules.tagzone.tag.TagRepository;
import com.project.devidea.modules.tagzone.zone.Zone;
import com.project.devidea.modules.tagzone.zone.ZoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
public class MentorService extends AbstractService {

    private final MentorRepository mentorRepository;
    private final ResumeRepository resumeRepository;

    protected MentorService(TagRepository tagRepository, ZoneRepository zoneRepository, MentorRepository mentorRepository, ResumeRepository resumeRepository) {
        super(tagRepository, zoneRepository);
        this.mentorRepository = mentorRepository;
        this.resumeRepository = resumeRepository;
    }

    private Mentor getMentor(Long accountId) {
        return mentorRepository.findByAccountId(accountId);
    }

    private void validateMentorRequest(MentorRequest request) {
        if ((request.isFree() && request.getCost() > 0) || request.getCost() < 0) {
            throw new InvalidInputException();
        }
    }

    public Long createMentor(Account account, CreateMentorRequest request) {

        validateMentorRequest(request);

        if (getMentor(account.getId()) != null) {
            throw new AlreadyExistException("이미 등록된 멘토입니다.");
        }

        Resume resume = resumeRepository.findByAccountId(account.getId());
        if (resume == null) {
            throw new NotFoundException("이력서가 존재하지 않습니다.");
        }

        Set<Zone> zones = getZones(request.getZones());
        Set<Tag> tags = getTags(request.getTags());
        Mentor mentor = Mentor.createMentor(account, resume, zones, tags, request.isFree(), request.getCost());
        return mentorRepository.save(mentor).getId();
    }

    public void updateMentor(Account account, UpdateMentorRequest request) {

        validateMentorRequest(request);

        Mentor mentor = getMentor(account.getId());
        if (mentor == null) {
            throw new NotFoundException("존재하지 않는 멘토입니다.");
        }
        Set<Zone> zones = getZones(request.getZones());
        Set<Tag> tags = getTags(request.getTags());

        mentor.setZones(zones);
        mentor.setTags(tags);

        mentor.setOpen(request.isOpen());
        mentor.setFree(request.isFree());
        mentor.setCost(request.getCost());
    }

    public void deleteMentor(Account account) {

        Mentor mentor = getMentor(account.getId());
        if (mentor == null) {
            throw new NotFoundException("존재하지 않는 멘토입니다.");
        }

        mentor.getTags().clear();
        mentor.getZones().clear();

        mentor.setAccount(null);
        mentor.setResume(null);
        mentorRepository.delete(mentor);
    }

}
