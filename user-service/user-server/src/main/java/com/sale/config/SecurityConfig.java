package com.sale.config;

import com.sale.filter.JwtAuthenticationFilter;
import com.sale.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.Filter;


@Configuration
//@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors(AbstractHttpConfigurer::disable).
                csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 设置授权规则
                .authorizeRequests(auth -> auth.
                        requestMatchers(new AntPathRequestMatcher("/auth/login")).anonymous().
                        requestMatchers(new AntPathRequestMatcher("/auth/register")).anonymous().
                        anyRequest().authenticated())
                // 添加JWT认证过滤器
                .addFilterBefore((Filter) jwtAuthenticationFilter, (Class<? extends Filter>) UsernamePasswordAuthenticationFilter.class)
                ;

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

//    @Bean
//    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.
//                // 禁用csrf
//                csrf(csrf -> csrf.disable())
//                // 设置Session为无效状态
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                // 设置授权规则
//                .authorizeRequests(auth -> auth.
//                        antMatchers("/auth/login").permitAll().
//                        anyRequest().authenticated())
//                // 添加JWT认证过滤器
//                .addFilterBefore((Filter) jwtAuthenticationFilter, (Class<? extends Filter>) UsernamePasswordAuthenticationFilter.class)
//                ;
//
//        // 构建并返回安全过滤链
//        return http.build();
//    }
}