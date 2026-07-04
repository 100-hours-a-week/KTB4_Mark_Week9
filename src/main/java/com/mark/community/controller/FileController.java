package com.mark.community.controller;

import com.mark.community.dto.FileResponse;
import com.mark.community.messages.ApiResponseMessage;
import com.mark.community.response.ApiResponse;
import com.mark.community.service.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<?> getFile(@PathVariable Long fileId) {
        FileResponse fileResponse = fileService.getFile(fileId);

        return ResponseEntity
                .status(ApiResponseMessage.SUCCESS_GET_FILE.getStatusCode())
                .body(new ApiResponse<>(ApiResponseMessage.SUCCESS_GET_FILE, fileResponse));
    }

    @GetMapping
    public ResponseEntity<?> getFiles(@RequestParam List<Long> fileIds) {
        List<FileResponse> fileResponses = fileService.getFiles(fileIds);

        return ResponseEntity
                .status(ApiResponseMessage.SUCCESS_GET_FILE.getStatusCode())
                .body(new ApiResponse<>(ApiResponseMessage.SUCCESS_GET_FILE, fileResponses));
    }
}
