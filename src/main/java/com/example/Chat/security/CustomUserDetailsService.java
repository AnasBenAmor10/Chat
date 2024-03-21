package com.example.Chat.security;

import com.example.Chat.model.Role;
import com.example.Chat.model.UserEntity;
import com.example.Chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
//Use to get User information from Database
public class CustomUserDetailsService implements UserDetailsService {
    //Get access a data via instance UserRepository
    private UserRepository userRepository ;
    //Constructor
    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    //Load user via Username and transform data to UserDetails
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Find User his Username Else UserNotFound
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));
        //Return User Object implement UserDetails
        return new User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    //Convert Role User to a Collection GrantedAuthority
    private Collection<GrantedAuthority> mapRolesToAuthorities(List<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
