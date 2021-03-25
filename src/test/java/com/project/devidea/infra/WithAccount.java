package com.project.devidea.infra;

import com.project.devidea.modules.content.study.Study_Role;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithAccountFactory.class)
public @interface WithAccount {
    String NickName();
    String Role()default "Study_Role.회원";
}
