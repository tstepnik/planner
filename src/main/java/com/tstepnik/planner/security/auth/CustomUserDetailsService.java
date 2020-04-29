package com.tstepnik.planner.security.auth;

import com.tstepnik.planner.domain.User;
import com.tstepnik.planner.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      User user = userRepository.findByLogin(username)
              .orElseThrow(() -> new UsernameNotFoundException("Bad credentials"));
      return toUserDetails(user);
    }

    private UserDetails toUserDetails (User user){
        return new CustomUserDetails(user.getLogin(),user.getPassword(),getAuthorities(user),user);
    }

    private List<GrantedAuthority> getAuthorities(User user){
        return user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getRole().
                toString())).collect(Collectors.toList());
    }
}
