package com.project.devidea.modules.content.resume;

import com.project.devidea.modules.content.resume.form.UpdateActivityRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final ModelMapper modelMapper;

    public Long save(Activity activity) {
        return activityRepository.save(activity).getId();
    }

    public void updateActivity(UpdateActivityRequest request, Activity activity) {
        modelMapper.map(request, activity);
    }

    public void deleteActivity(Resume resume, Activity activity) {
        activity.setResume(null);
        resume.getActivites().remove(activity);
        activityRepository.delete(activity);
    }
}
