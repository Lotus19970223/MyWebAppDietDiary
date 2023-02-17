package com.dietdiary;

// 参考：https://qiita.com/t-yama-3/items/a538d47b8f0a27639d23

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    //以下を参考に、ログイン失敗→成功時のエラーを防ぐ
    //https://stackoverflow.com/questions/61029340/spring-security-redirects-to-page-with-status-code-999
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/favicon.ico", "/resources/**", "/error");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("yama3")
            .password(passwordEncoder().encode("123456"))
            .roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .anyRequest()
            .authenticated();
        http.formLogin()
        	//ログイン画面のURL
            .loginPage("/login")
            //.failureUrl("/login")// 認証失敗時のURL
            //認証後にリダイレクトされるページ
            .defaultSuccessUrl("/sampleMyPageThisMonth")
            .permitAll();
        http.logout()
        .permitAll();
    }
}