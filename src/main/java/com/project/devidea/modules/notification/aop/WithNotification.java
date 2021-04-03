package com.project.devidea.modules.notification.aop;

import com.project.devidea.modules.notification.NotificationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface WithNotification {
    String to();
    NotificationType type();
    String title();
    String message();
}
