package com.project.devidea.modules.content.mentoring;

import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.content.mentoring.form.CreateMenteeRequest;
import com.project.devidea.modules.content.mentoring.form.UpdateMenteeRequest;
import com.project.devidea.modules.tagzone.tag.Tag;
import com.project.devidea.modules.tagzone.tag.TagRepository;
import com.project.devidea.modules.tagzone.zone.Zone;
import com.project.devidea.modules.tagzone.zone.ZoneRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MenteeService {

    private final MenteeRepository menteeRepository;
    private final TagRepository tagRepository;
    private final ZoneRepository zoneRepository;
    private final ModelMapper modelMapper;

    public Long createMentee(Account account, CreateMenteeRequest request) {

        // TODO - 예외 처리
        if(getMentee(account.getId()) != null) {
        }

        Set<Zone> zones = request.getZones().stream().map(zone -> {
            String[] locations = zone.split("/");
            return zoneRepository.findByCityAndProvince(locations[0], locations[1]);
        }).collect(Collectors.toSet());

        Set<Tag> tags = request.getTags().stream().map(tag -> {
            return tagRepository.findByFirstName(tag);
        }).collect(Collectors.toSet());

        Mentee mentee = Mentee.createMentee(account, request.getDescription(), zones, tags, request.isFree());
        return menteeRepository.save(mentee).getId();
    }

    private Mentee getMentee(Long accountId) {
        return menteeRepository.findByAccountId(accountId);
    }

/*    public void updateMentee(UpdateMenteeRequest request, Mentee mentee) {
        // modelMapper.map(request, mentee);
        mentee.setDescription(request.getDescription());
        mentee.setZones(request.getZones());
        mentee.setTags(request.getTags());
        mentee.setOpen(request.isOpen());
        mentee.setFree(request.isFree());
    }*/

    public void deleteMentee(Mentee mentee) {
        mentee.setAccount(null);
        menteeRepository.delete(mentee);
    }

    public void updateMentee(Account account, UpdateMenteeRequest request) {

        Mentee mentee = getMentee(account.getId());
        // TODO - 예외처리
        if(mentee == null) {
            
        }

        modelMapper.map(request, mentee);
    }
}
