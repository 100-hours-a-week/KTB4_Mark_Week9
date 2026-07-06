package com.mark.community.entity;

import com.mark.community.enums.FileType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
public class UploadFile {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private String filePath;

    @Enumerated(EnumType.STRING)
    private FileType fileType;
    private Long fileSize;

    public UploadFile(String fileName, String filePath, FileType fileType, Long fileSize) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
        this.fileSize = fileSize;
    }

    public void setId(Long id){
        this.id = id;
    }
}
