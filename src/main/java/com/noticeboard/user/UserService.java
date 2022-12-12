package com.noticeboard.user;

import com.noticeboard.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User create(String name, String emailAddress, String password, String salt) {
        User user = User.builder()
                .name(name)
                .emailAddress(emailAddress)
                .password(this.passwordEncoder.encode(password))
                .salt(salt)
                .build();
        this.userRepository.save(user);
        return user;
    }
}
