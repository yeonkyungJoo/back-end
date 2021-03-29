//package com.project.devidea.modules.notification.aop;
//
//
//import com.project.devidea.modules.account.Account;
//import com.project.devidea.modules.account.repository.AccountRepository;
//import com.project.devidea.modules.notification.Notification;
//import com.project.devidea.modules.notification.NotificationRepository;
//import lombok.RequiredArgsConstructor;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.*;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.validation.ValidationException;
//import java.lang.reflect.Method;
//import java.time.LocalDateTime;
//
//@Component
//@Aspect
//@RequiredArgsConstructor
//@Transactional
//public class NotificationAop {
//    NotificationRepository notificationRepository;
//    @Around("@annotation(WithNotification)")
//    public void 알림보내기(ProceedingJoinPoint pjp) {
//        MethodSignature signature = (MethodSignature) pjp.getSignature();
//        Method method = signature.getMethod();
//
//        WithNotification withNotification = method.getAnnotation(WithNotification.class);
//        Notification notification=new Notification()
//                .builder()
//                .account(Account.generateAccountById(Long.parseLong(withNotification.to())))
//                .notificationType(withNotification.type())
//                .checked(false)
//                .createdDateTime(LocalDateTime.now())
//                .title(withNotification.title())
//                .message(withNotification.message())
//                .build();
//        notificationRepository.saveAndFlush(notification);
//    }
//
//}
