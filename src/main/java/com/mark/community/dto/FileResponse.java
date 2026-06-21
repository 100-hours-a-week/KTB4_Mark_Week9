package com.mark.community.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FileResponse {
    private String fileName;
    private String filePath;

    public FileResponse(String fileName, String filePath){
        this.fileName = fileName;
        this.filePath = filePath;
    }

}
