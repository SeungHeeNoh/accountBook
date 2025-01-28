package com.sweethome.accountbook.config;


import com.sweethome.accountbook.config.security.UserLoginFailureHandler;
import com.sweethome.accountbook.config.security.UserLoginSuccessHandler;
import com.sweethome.accountbook.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
public class WebSecurityConfig {

    private final UserLoginSuccessHandler userLoginSuccessHandler;
    private final UserLoginFailureHandler userLoginFailureHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(
                        auth -> auth.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                .requestMatchers("/", "/login").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/user").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler(userLoginSuccessHandler)
                        .failureHandler(userLoginFailureHandler)
                        .permitAll()
                )
                .logout(logout -> logout.logoutSuccessUrl("/"))
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserService userService) {
        return userId -> userService.searchUser(userId);
    }


}
