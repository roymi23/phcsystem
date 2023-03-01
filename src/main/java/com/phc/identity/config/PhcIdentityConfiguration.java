package com.phc.identity.config;

import com.phc.identity.security.DbUserDetailsService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.CrossOrigin;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class PhcIdentityConfiguration {

    @Bean
    UserDetailsService userDetailsService() {
//        UserDetails admin = User.withUsername("Roy")
//                .password(encoder.encode("urtheadmin")).roles("ADMIN").build();
//
//        UserDetails user1 = User.withUsername("Joel")
//                .password(encoder.encode("uruser1")).roles("USER").build();
//
//        return new InMemoryUserDetailsManager(admin, user1);

        return new DbUserDetailsService();
    }

//    @Bean
//    UserDetailsService userDetailsService() {
//
//        return new DbUserDetailsService();
//    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @Bean
    @ConditionalOnProperty(name="form.login", havingValue="true")
    SecurityFilterChain securityFilterChainFormLogin(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity.csrf().disable().
                authorizeHttpRequests()
                .requestMatchers("/phc/identity/login", "/phc/identity/register").permitAll()
                .requestMatchers(antMatcher("/h2-console/**")).permitAll()
                .requestMatchers(antMatcher("/swagger-ui/**")).permitAll()
                .and()
                .authorizeHttpRequests().
                requestMatchers("/phc/identity/**")
                .authenticated()
                .and().formLogin()
                .and().build();
    }

    @Bean
    @ConditionalOnProperty(name="form.login", havingValue="false")
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity.csrf().disable().
                authorizeHttpRequests()
                .requestMatchers("/phc/identity/login", "/phc/identity/register").permitAll()
                .requestMatchers(antMatcher("/h2-console/**")).permitAll()
                .requestMatchers(antMatcher("/v3/api-docs/**")).permitAll()
                .requestMatchers(antMatcher("/actuator/**")).permitAll()
                .and()
                .authorizeHttpRequests().
                requestMatchers("/phc/identity/**")
                .authenticated()
                .and().httpBasic()
                .and().build();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        return daoAuthenticationProvider;
    }

}
