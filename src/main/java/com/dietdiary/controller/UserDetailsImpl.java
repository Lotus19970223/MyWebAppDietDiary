//参考：https://qiita.com/t-yama-3/items/a538d47b8f0a27639d23
//5-3-2. UserDetailsImpl クラスのコードの記述 の方法で作成

package com.dietdiary.controller;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    private Collection<GrantedAuthority> authorities;


	public UserDetailsImpl(String username, String password, Collection<GrantedAuthority> authorities) {
		super();
		this.username = username;
		this.password = password;
		this.authorities = authorities;
	}

	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public String getUsername() {
        return username;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
         return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }

}
