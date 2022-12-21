package com.noticeboard.user;

import com.noticeboard.entity.Profile;
import com.noticeboard.entity.User;
import com.noticeboard.entity.UserMeta;
import com.noticeboard.exception.DataNotFoundException;
import com.noticeboard.profile.ProfileRepository;
import com.noticeboard.usermeta.UserMetaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final UserMetaRepository userMetaRepository;
    private final PasswordEncoder passwordEncoder;

    public User create(String name, String emailAddress, String password, String salt) {
        User user = User.builder()
                .name(name)
                .emailAddress(emailAddress)
                .password(this.passwordEncoder.encode(password))
//                .password(password)
                .salt(salt)
                .build();
        this.userRepository.save(user);

        Profile profile = Profile.builder().build();
        this.profileRepository.save(profile);

        UserMeta userMeta = UserMeta.builder()
                .user(user)
                .profile(profile)
                .build();
        this.userMetaRepository.save(userMeta);

        return user;
    }

    public User findByName(String name) {
        Optional<User> optionalUser = this.userRepository.findByname(name);
        if(!optionalUser.isPresent()){
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다");
        }
        User user = optionalUser.get();
        return user;
    }

    public User getUser(String name) {
        Optional<User> optionalUser = this.userRepository.findByname(name);
        if(optionalUser.isPresent()) {
            return optionalUser.get();
        }
        else{
            throw new DataNotFoundException("user not found");
        }
    }
}
