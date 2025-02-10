package com.typerf1.typerf1.config;

import com.typerf1.typerf1.model.ParticipantLoginData;
import com.typerf1.typerf1.repository.ParticipantLoginDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private ParticipantLoginDataRepository participantLoginDataRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<ParticipantLoginData> foundUsers = participantLoginDataRepository.findAllByUsername(username);
        if(!foundUsers.isEmpty()){
            ParticipantLoginData participantLoginData = foundUsers.getFirst();

            List<GrantedAuthority> authorities = new ArrayList<>();

            return new User(participantLoginData.getUsername(),
                    participantLoginData.getPassword(), authorities);
        }
        return null;
    }
}
