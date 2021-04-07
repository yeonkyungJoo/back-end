package com.project.devidea.modules.content.suggestion;

import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.content.mentoring.Mentee;
import com.project.devidea.modules.content.mentoring.MenteeRepository;
import com.project.devidea.modules.content.mentoring.Mentor;
import com.project.devidea.modules.content.mentoring.MentorRepository;
import com.project.devidea.modules.content.mentoring.exception.NotFoundException;
import com.project.devidea.modules.content.suggestion.form.SuggestionRequest;
import com.project.devidea.modules.notification.Notification;
import com.project.devidea.modules.notification.NotificationRepository;
import com.project.devidea.modules.notification.NotificationType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SuggestionService {

    static String NOTIFICATION_TITLE_NEW = "새로운 제안이 도착했습니다.";
    static String NOTIFICATION_TITLE_CANCEL = "제안이 취소되었습니다.";

    private final MentorRepository mentorRepository;
    private final MenteeRepository menteeRepository;
    private final SuggestionRepository suggestionRepository;
    private final NotificationRepository notificationRepository;

    public Long suggest(Account account, Long id, SuggestionRequest request) {

        Mentor findMentor = mentorRepository.findByAccountId(account.getId());
        Mentee findMentee = menteeRepository.findByAccountId(account.getId());
        Account to = null;
        if (findMentor != null && findMentee == null) {
            Mentee mentee = menteeRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("존재하지 않는 멘티입니다."));
            to = mentee.getAccount();
        } else if (findMentor == null && findMentee != null) {
            Mentor mentor = mentorRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("존재하지 않는 멘토입니다."));
            to = mentor.getAccount();
        } else {
            throw new NotFoundException();
        }

        Suggestion suggestion = Suggestion.createSuggestion(account, to, request.getSubject(), request.getMessage());
        // 알림 생성
        Notification notification = Notification.generateNotification(NOTIFICATION_TITLE_NEW,
                "[" + request.getSubject() + "]\n" + request.getMessage(), NotificationType.SUGGESTION, to);
        notificationRepository.save(notification);
        // TODO - 메일
        return suggestionRepository.save(suggestion).getId();
    }

    public void cancel(Account account, Long suggestionId) {

        Suggestion suggestion = suggestionRepository.findById(suggestionId)
                .orElseThrow(() -> new NotFoundException());

        // TODO - 예외 처리
        if (suggestion.getFrom() != account) {

        }
        // 알림 생성
        Notification notification = Notification.generateNotification(NOTIFICATION_TITLE_CANCEL,
                account.getName() + "님이 제안을 취소했습니다.", NotificationType.SUGGESTION, suggestion.getTo());
        notificationRepository.save(notification);
        // TODO - 메일
        suggestionRepository.delete(suggestion);
    }
}
