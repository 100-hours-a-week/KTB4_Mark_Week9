package com.mark.community.repository;

import com.mark.community.entity.UploadFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface FileRepository {
    Optional<UploadFile> findById (String fileId);
    UploadFile save(MultipartFile multipartFile);

}
