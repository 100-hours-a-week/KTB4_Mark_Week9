package com.mark.community.entity;

import com.mark.community.entity.key.PostViewId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "post_view")
public class View {
    @EmbeddedId
    private PostViewId id;
    private Date viewTime;

    public View(Long userId, Long postId){
        this.id = new PostViewId(userId, postId);
        this.viewTime = new Date();
    }

    public void updateTime(){
        this.viewTime = new Date();
    }
}





