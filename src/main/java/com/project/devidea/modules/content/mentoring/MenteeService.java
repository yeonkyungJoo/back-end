package com.project.devidea.modules.content.mentoring;

import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.content.mentoring.exception.AlreadyExistException;
import com.project.devidea.modules.content.mentoring.exception.NotFoundException;
import com.project.devidea.modules.content.mentoring.form.CreateMenteeRequest;
import com.project.devidea.modules.content.mentoring.form.UpdateMenteeRequest;
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
public class MenteeService extends AbstractService {

    private final MenteeRepository menteeRepository;

    protected MenteeService(TagRepository tagRepository, ZoneRepository zoneRepository, MenteeRepository menteeRepository) {
        super(tagRepository, zoneRepository);
        this.menteeRepository = menteeRepository;
    }

    public Long createMentee(Account account, CreateMenteeRequest request) {

        if(getMentee(account.getId()) != null) {
            throw new AlreadyExistException("이미 등록된 멘티입니다.");
        }

        Set<Zone> zones = getZones(request.getZones());
        Set<Tag> tags = getTags(request.getTags());

        Mentee mentee = Mentee.createMentee(account, request.getDescription(), zones, tags, request.isFree());
        return menteeRepository.save(mentee).getId();
    }

    private Mentee getMentee(Long accountId) {
        return menteeRepository.findByAccountId(accountId);
    }

    public void deleteMentee(Account account) {

        Mentee mentee = getMentee(account.getId());
        if(mentee == null) {
            throw new NotFoundException("존재하지 않는 멘티입니다.");
        }
        mentee.setAccount(null);
        menteeRepository.delete(mentee);
    }

    public void updateMentee(Account account, UpdateMenteeRequest request) {

        Mentee mentee = getMentee(account.getId());
        if(mentee == null) {
            throw new NotFoundException("존재하지 않는 멘티입니다.");
        }

        Set<Zone> zones = getZones(request.getZones());
        Set<Tag> tags = getTags(request.getTags());

        mentee.setDescription(request.getDescription());
        mentee.setZones(zones);
        mentee.setTags(tags);
        mentee.setOpen(request.isOpen());
        mentee.setFree(request.isFree());
    }

}
