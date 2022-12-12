package com.noticeboard.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity(name = "UserMeta")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserMeta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    @OneToOne
    private Profile profile;

    @OneToMany(mappedBy = "userMeta", cascade = CascadeType.REMOVE)
    private List<Post> postList;

    @OneToMany(mappedBy = "userMeta", cascade = CascadeType.REMOVE)
    private List<Comment> commentList;

    @Builder
    public UserMeta(User user, Profile profile) {
        this.user = user;
        this.profile = profile;
    }
}
