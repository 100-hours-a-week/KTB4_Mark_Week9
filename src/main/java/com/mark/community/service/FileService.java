package com.mark.community.service;

import com.mark.community.entity.UploadFile;
import com.mark.community.enums.FileType;
import com.mark.community.messages.ErrorMessage;
import com.mark.community.repository.UploadFileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
public class FileService {
    private final String UPLOAD_DIR = "uploads/";

    private final UploadFileRepository uploadFileRepository;

    public FileService(UploadFileRepository uploadFileRepository){
        this.uploadFileRepository = uploadFileRepository;
    }

    public UploadFile upload(MultipartFile multipartFile) {
        String originalName = multipartFile.getOriginalFilename();
        String extension = originalName.substring(originalName.lastIndexOf('.'));
        FileType fileType = FileType.checkFileType(multipartFile.getContentType());
        Long fileSize = multipartFile.getSize();
        String filePath = UPLOAD_DIR + UUID.randomUUID() + extension;

        if(!saveFile(multipartFile, filePath)) return null;

        UploadFile uploadFile = new UploadFile(originalName, filePath, fileType, fileSize);

        return uploadFileRepository.save(uploadFile);

    }

    private boolean saveFile(MultipartFile file, String filePath){
        try{
            file.transferTo(new File(filePath).getAbsoluteFile());
        } catch (IOException e){
            log.error(ErrorMessage.FAIL_UPLOAD.getMessage());
            return false;
        }
        return true;
    }
}