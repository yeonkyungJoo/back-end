package com.project.devidea.modules.content.resume.activity;

import com.project.devidea.infra.error.exception.ErrorCode;
import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.content.mentoring.exception.InvalidInputException;
import com.project.devidea.modules.content.mentoring.exception.NotFoundException;
import com.project.devidea.modules.content.resume.Resume;
import com.project.devidea.modules.content.resume.ResumeRepository;
import com.project.devidea.modules.content.resume.form.activity.ActivityRequest;
import com.project.devidea.modules.content.resume.form.activity.CreateActivityRequest;
import com.project.devidea.modules.content.resume.form.activity.UpdateActivityRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class ActivityService {

    private final ResumeRepository resumeRepository;
    private final ActivityRepository activityRepository;

    private void validateActivityRequest(ActivityRequest request) {

        LocalDate startDate = LocalDate.parse(request.getStartDate());
        LocalDate endDate = LocalDate.parse(request.getEndDate());
        LocalDate now = LocalDate.now();

        if (startDate.isAfter(now)) {
            throw new InvalidInputException();
        }

        if (endDate != null && (endDate.isAfter(now) || !endDate.isAfter(startDate))) {
            throw new InvalidInputException();
        }
    }

    public Long createActivity(Account account, CreateActivityRequest request) {

        validateActivityRequest(request);
        Resume resume = resumeRepository.findByAccountId(account.getId());
        if (resume == null) {
            throw new NotFoundException("이력서가 존재하지 않습니다.");
        }

        Activity activity = Activity.createActivity(
                resume,
                request.getActivityName(),
                LocalDate.parse(request.getStartDate()),
                LocalDate.parse(request.getEndDate()),
                request.getDescription(),
                request.getLink());
        return activityRepository.save(activity).getId();
    }

    public void updateActivity(Long activityId, UpdateActivityRequest request) {

        validateActivityRequest(request);
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new NotFoundException());

        activity.setActivityName(request.getActivityName());
        activity.setStartDate(LocalDate.parse(request.getStartDate()));
        activity.setEndDate(LocalDate.parse(request.getEndDate()));
        activity.setDescription(request.getDescription());
        activity.setLink(request.getLink());
    }


    public void deleteActivity(Long activityId) {

        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new NotFoundException());
        Resume resume = activity.getResume();

        activity.setResume(null);
        resume.getActivites().remove(activity);
        activityRepository.delete(activity);
    }
}
