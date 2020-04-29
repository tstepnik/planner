package com.tstepnik.planner.security.auth;

import com.tstepnik.planner.domain.CustomUserDetail;
import com.tstepnik.planner.domain.Role;
import com.tstepnik.planner.domain.User;
import com.tstepnik.planner.domain.UserRole;
import com.tstepnik.planner.repository.UserRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class MyUserDetailsService implements UserDetailsService {

    UserRepository userRepository;


    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public CustomUserDetail loadUserByUsername(String name) throws UsernameNotFoundException, DataAccessException {
        // returns the get(0) of the user list obtained from the db
        User domainUser = userRepository.findByLogin(name).get();


        Set<UserRole> roles = domainUser.getRoles();

        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        for(UserRole role: roles){
            authorities.add(new SimpleGrantedAuthority(role.getRole().name()));
        }

        CustomUserDetail customUserDetail=new CustomUserDetail();
        customUserDetail.setUser(domainUser);
        customUserDetail.setAuthorities(authorities);

        return customUserDetail;

    }
}
