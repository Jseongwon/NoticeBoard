package com.noticeboard.usermeta;

import com.noticeboard.entity.UserMeta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMetaRepository extends JpaRepository<UserMeta, Long> {

}
