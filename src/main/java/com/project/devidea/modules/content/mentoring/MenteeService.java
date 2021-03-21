package com.project.devidea.modules.content.mentoring;

import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.content.mentoring.form.UpdateMenteeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MenteeService {

    private final MenteeRepository menteeRepository;

    public Long createMentee(Mentee mentee, Account account) {
        mentee.setAccount(account);
        mentee.publish();
        menteeRepository.save(mentee);
        return mentee.getId();
    }

    public void updateMentee(UpdateMenteeRequest request, Mentee mentee) {
        // modelMapper.map(request, mentee);
        mentee.setDescription(request.getDescription());
        mentee.setZones(request.getZones());
        mentee.setTags(request.getTags());
        mentee.setOpen(request.isOpen());
        mentee.setFree(request.isFree());
    }

    public void deleteMentee(Mentee mentee) {
        menteeRepository.delete(mentee);
    }
}
