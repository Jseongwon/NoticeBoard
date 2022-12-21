package com.noticeboard.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Set;

@Getter
@Entity(name = "Comment")
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author", referencedColumnName = "id")
    private UserMeta author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToMany
    Set<UserMeta> voter;

    public Comment(String content, UserMeta author, Post post) {
        this.content = content;
        this.author = author;
        this.post = post;
    }

    public void update(String content) {
        this.content = content;
    }
}
