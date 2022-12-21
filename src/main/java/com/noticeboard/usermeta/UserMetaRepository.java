package com.noticeboard.usermeta;

import com.noticeboard.entity.UserMeta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserMetaRepository extends JpaRepository<UserMeta, Long> {
    Optional<UserMeta> findByuserId(Long userId);
}
