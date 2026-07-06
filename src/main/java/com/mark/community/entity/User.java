package com.mark.community.entity;


import com.mark.community.enums.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String nickname;
    private boolean deleted;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToOne
    @JoinColumn(name = "file_id", unique = true)
    private UploadFile profileFile;

    public User(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    public User(String email, String password, String nickname, UploadFile file) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.profileFile = file;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    public void setFile(UploadFile file) {
        this.profileFile = file;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setDeleted(boolean deleted){
        this.deleted = deleted;
    }

    public void setRole(UserRole userRole){
        this.role = userRole;
    }
}
