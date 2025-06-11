package com.devendra.SocietySync.security;

import com.devendra.SocietySync.model.User;
import com.devendra.SocietySync.model.Staff;
import com.devendra.SocietySync.repository.UserRepository;
import com.devendra.SocietySync.repository.StaffRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Try loading as User
        User user = userRepository.findByEmail(email).orElse(null);
        if (user != null) {
            return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
            );
        }

        // Try loading as Staff
        Staff staff = staffRepository.findByEmail(email).orElse(null);
        if (staff != null) {
            return new org.springframework.security.core.userdetails.User(
                staff.getEmail(),
                staff.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_STAFF"))
            );
        }

        // Not found in either
        throw new UsernameNotFoundException("User or Staff not found with email: " + email);
    }
}
