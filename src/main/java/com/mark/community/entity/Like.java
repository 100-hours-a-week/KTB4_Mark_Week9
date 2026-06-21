package com.mark.community.entity;


import com.mark.community.entity.key.PostLikeId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "post_like")
public class Like {
    @EmbeddedId
    private PostLikeId id;

    public Like(Long userId, Long postId){
        this.id = new PostLikeId(userId, postId);
    }




}
