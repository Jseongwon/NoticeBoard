package com.noticeboard.usermeta;

import com.noticeboard.entity.User;
import com.noticeboard.entity.UserMeta;
import com.noticeboard.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserMetaService {

    private final UserMetaRepository userMetaRepository;

    public UserMeta getUserMeta(Long id) {
        Optional<UserMeta> optionalUser = this.userMetaRepository.findById(id);
        if(optionalUser.isPresent()) {
            return optionalUser.get();
        }
        else{
            throw new DataNotFoundException("user not found");
        }
    }

    public UserMeta findByUserId(User user) {
        Optional<UserMeta> optionalUser = this.userMetaRepository.findByuserId(user.getId());
        if(optionalUser.isPresent()) {
            return optionalUser.get();
        }
        else{
            throw new DataNotFoundException("user not found");
        }
    }
}
