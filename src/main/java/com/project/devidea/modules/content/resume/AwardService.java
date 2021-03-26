package com.project.devidea.modules.content.resume;

import com.project.devidea.modules.content.resume.form.UpdateAwardRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AwardService {

    private final AwardRepository awardRepository;
    private final ModelMapper modelMapper;

    public Long save(Award award) {
        return awardRepository.save(award).getId();
    }

    public void updateAward(UpdateAwardRequest request, Award award) {
        modelMapper.map(request, award);
    }

    public void deleteAward(Resume resume, Award award) {
        award.setResume(null);
        resume.getAwards().remove(award);
    }
}
