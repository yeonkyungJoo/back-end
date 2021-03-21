package com.project.devidea.modules.content.mentoring;

import com.project.devidea.modules.content.mentoring.form.UpdateMentorRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MentorService {

    private final MentorRepository mentorRepository;
    private final ModelMapper modelMapper;

    public Long createMentor(Mentor mentor) {
        mentor.publish();
        mentorRepository.save(mentor);
        return mentor.getId();
    }

    public void updateMentor(UpdateMentorRequest request, Mentor mentor) {
        modelMapper.map(request, mentor);
    }

    public void deleteMentor(Mentor mentor) {
        mentorRepository.delete(mentor);
    }

}
