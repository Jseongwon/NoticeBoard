package com.noticeboard.user;

import com.noticeboard.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("User Service");
        System.out.println(username);
        Optional<User> _user = this.userRepository.findByname(username);
        if(!_user.isPresent()){
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다");
        }
        User user = _user.get();

        System.out.println(user.getName());
        System.out.println(user.getPassword());

        List<GrantedAuthority> authorities = new ArrayList<>();
        if(username.equals("admin")) {
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        }
        else {
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }

//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        UserDetails userDetails = (UserDetails)principal;
//
//        System.out.println(userDetails.getUsername());
//        System.out.println(userDetails.getPassword());

        return ApplicationUser.builder()
                .id(user.getId())
                .username(user.getName())
                .password(user.getPassword())
                .authorities(authorities)
                .build();
    }
}
