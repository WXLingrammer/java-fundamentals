package com.dxc.MyDigitalHub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.dxc.MyDigitalHub.services.UserDetailsServiceImpl;
 
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
 
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }
     
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
     
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
         
        return authProvider;
    }
 
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.cors().and().csrf().disable().authorizeRequests()
    	.antMatchers(HttpMethod.GET, "/getUsers/{role}").permitAll()
    	.antMatchers(HttpMethod.GET, "/getUser/{id}").permitAll()
    	.antMatchers(HttpMethod.GET, "/getUsername/{username}").permitAll()
    	.antMatchers(HttpMethod.GET, "/getName/{name}").permitAll()
    	.antMatchers(HttpMethod.POST, "/addUser").permitAll()
    	.antMatchers(HttpMethod.PUT, "/updateUser/{id}").permitAll()
    	.antMatchers(HttpMethod.DELETE, "/deleteUser/{id}").permitAll()
    	.antMatchers(HttpMethod.GET, "/getPosts").permitAll()
    	.antMatchers(HttpMethod.GET, "/getPost/{id}").permitAll()
    	.antMatchers(HttpMethod.POST, "/addPost").permitAll()
    	.antMatchers(HttpMethod.DELETE, "/deletePost/{id}").permitAll()
    	.antMatchers(HttpMethod.PUT, "/updatePost/{id}").permitAll()
    	.antMatchers(HttpMethod.GET, "/getUserPassword/{password}").permitAll()
    	.antMatchers(HttpMethod.POST, "/upload").permitAll()
    	.antMatchers(HttpMethod.GET, "/files").permitAll()
    	.antMatchers(HttpMethod.GET, "/files/{filename:.+}").permitAll()
		.anyRequest().authenticated().and().httpBasic();
    }
}
