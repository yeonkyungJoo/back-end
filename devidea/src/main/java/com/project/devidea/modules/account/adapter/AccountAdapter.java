package com.project.devidea.modules.account.adapter;

import com.project.devidea.modules.account.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class AccountAdapter extends User {

    private Account account;


    public AccountAdapter(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

//    public AccountAdapter(Account account) {
//        super(account.getEmail(), account.getPassword(), account.getRoles())
//    }
}
