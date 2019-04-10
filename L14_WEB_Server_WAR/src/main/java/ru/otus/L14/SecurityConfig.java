package ru.otus.L14;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth, PasswordEncoder passwordEncoder) throws Exception {
        auth.inMemoryAuthentication().withUser("admin2").password(passwordEncoder.encode("admin2")).roles("ADMIN");

    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/appLogin", "/login").permitAll()
                .and()
                .authorizeRequests().antMatchers("/**").access("hasRole('ROLE_ADMIN')")
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/appLogin").usernameParameter("app_username").passwordParameter("app_password")
                .defaultSuccessUrl("/user/list")
                .and()
                .logout().logoutUrl("/appLogout").logoutSuccessUrl("/login");
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}