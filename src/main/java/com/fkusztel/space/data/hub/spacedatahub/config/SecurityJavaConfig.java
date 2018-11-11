package com.fkusztel.space.data.hub.spacedatahub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Filip.Kusztelak
 */
@Configuration
@EnableWebSecurity
public class SecurityJavaConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin").password(encoder().encode("adminPass")).roles("ADMIN")
                .and()
                .withUser("user").password(encoder().encode("userPass")).roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .exceptionHandling()
                .and()
                .authorizeRequests()
                .antMatchers("/mission/read","/product/lower",
                        "/product/greater", "/product/between", "/product/order").authenticated()
                .antMatchers("/mission/create","/mission/update",
                        "/mission/delete","/product/create", "/product/delete").hasRole("ADMIN")
                .and()
                .httpBasic();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
