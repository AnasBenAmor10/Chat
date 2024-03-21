package com.example.Chat.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

//Make sure that this class is  a configuration pour SpringBoot
@Configuration

//Activate a Configuration Web Security
@EnableWebSecurity

//Configure Security Role for HTTP request
public class SecurityConfiguration {
    private CustomUserDetailsService userDetailsService;

    @Autowired
    public SecurityConfiguration(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    //Security Filter Chain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                //This URL don't need to be authenticated give them a permission
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/home" ,"/api/room","/api/auth/**").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/**").permitAll()
                        //Any URL except that need to be authenticated
                        .anyRequest().authenticated()

                )
//                .formLogin((form) -> form
//                        // Page login URL
//                        .loginPage("/login")
//                        //Give a permission to everyone to view login form don't need to be authenticated
//                        .permitAll()
//                )
                .logout((logout) -> logout.permitAll());

        return http.build();
    }
    @Bean
    //Init a bean that hash a password
    public PasswordEncoder passwordEncoder() {
        //Return a hash password using BCrypt Algo
        return new BCryptPasswordEncoder();
    }

    @Bean
    // Init a bean (Service) that configure a Details of User Authentication
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user =
                User.builder()
                        .username("user")
                        .password(passwordEncoder.encode("password")) // Encode le mot de passe
                        .roles("USER")
                        .build();
        UserDetails admin =
                User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("password")) // Encode le mot de passe
                        .roles("ADMIN")
                        .build();

        return new InMemoryUserDetailsManager(user , admin); //Impl that Contain a User Detail to facilitate a Test
    }


    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }







}