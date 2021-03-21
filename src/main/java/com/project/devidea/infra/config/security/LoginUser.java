package com.project.devidea.infra.config.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.devidea.modules.account.Account;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
@Getter
public class LoginUser implements UserDetails, Serializable {

    private static final long serialVersionUID = 5926468583005150707L;
    @JsonIgnore
    private Account account;
    //need default constructor for JSON Parsing
    public LoginUser(Account account) {
//        super(account.getNickName(), account.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
        this.account = account;
    }
    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        Arrays.asList(account.getRoles().split(", ")).forEach(r -> authorities.add(new SimpleGrantedAuthority(r)));
        return authorities;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return account.getPassword();
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return account.getEmail();
    }


    public String getNickName() { return account.getNickname(); }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
