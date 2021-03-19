package com.project.devidea.modules.content.resume;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CareerService {

    private final CareerRepository careerRepository;


    public Long save(Career career) {

        careerRepository.save(career);
        return career.getId();
    }
}
