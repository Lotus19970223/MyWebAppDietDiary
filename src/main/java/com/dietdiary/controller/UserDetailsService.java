package com.dietdiary.controller;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDetailsService {
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;  // ユーザー名からユーザー情報を取得
}